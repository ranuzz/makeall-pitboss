defmodule Pitboss.Game do
  use GenServer
  alias Pitboss.{Repo, GameState, GameRecord}

  def start_link(game_id) do
    GenServer.start_link(__MODULE__, game_id, name: via_tuple(game_id))
  end

  def init(game_id) do
    {:ok, %{game_id: game_id, state: %GameState{}}}
  end

  def via_tuple(game_id) do
    {:via, Registry, {Pitboss.GameRegistry, game_id}}
  end

  def join(game_id, player_id) do
    GenServer.call(via_tuple(game_id), {:join, player_id})
  end

  def start_game(game_id) do
    GenServer.call(via_tuple(game_id), :start_game)
  end

  def submit_answer(game_id, player_id, answer) do
    GenServer.call(via_tuple(game_id), {:submit_answer, player_id, answer})
  end

  def handle_call({:join, player_id}, _from, %{state: state} = data) do
    if length(state.players) < 8 do
      new_state = %{state | players: [player_id | state.players]}
      {:reply, :ok, %{data | state: new_state}}
    else
      {:reply, {:error, :game_full}, data}
    end
  end

  def handle_call(:start_game, _from, data) do
    if length(data.state.players) >= 2 do
      new_state = %{data.state | status: :in_progress, current_round: 1}
      new_prompt = generate_prompt()

      PitbossWeb.Endpoint.broadcast("game:#{data.game_id}", "new_round", %{
        prompt: new_prompt
      })

      {:reply, :ok, %{data | state: new_state}}
    else
      {:reply, {:error, :not_enough_players}, data}
    end
  end

  def handle_call({:submit_answer, player_id, answer}, _from, data) do
    new_state = update_game_state(data.state, player_id, answer)

    if all_players_answered?(new_state) do
      scores = calculate_scores(new_state)
      new_state = advance_round(new_state, scores)

      if game_ended?(new_state) do
        PitbossWeb.Endpoint.broadcast("game:#{data.game_id}", "game_over", %{scores: scores})

        save_game_state(data.game_id, new_state)
        Process.send_after(self(), :terminate, 5000)
        {:reply, :ok, %{data | state: new_state}}
      else
        new_prompt = generate_prompt()

        PitbossWeb.Endpoint.broadcast("game:#{data.game_id}", "new_round", %{
          prompt: new_prompt
        })

        {:reply, :ok, %{data | state: new_state}}
      end
    else
      {:reply, :ok, %{data | state: new_state}}
    end
  end

  def handle_info(:terminate, state) do
    {:stop, :normal, state}
  end

  defp update_game_state(state, player_id, answer) do
    %{state | answers: Map.put(state.answers, player_id, answer)}
  end

  defp all_players_answered?(state) do
    length(state.players) == map_size(state.answers)
  end

  defp calculate_scores(state) do
    state.players
    |> Enum.map(fn player ->
      score = String.length(Map.get(state.answers, player, ""))
      {player, score}
    end)
    |> Map.new()
  end

  defp advance_round(state, scores) do
    %{
      state
      | current_round: state.current_round + 1,
        scores: Map.merge(state.scores, scores),
        answers: %{}
    }
  end

  defp game_ended?(state) do
    state.current_round > 10
  end

  defp generate_prompt do
    "What's your favorite color?"
  end

  defp save_game_state(game_id, state) do
    game_record = %GameRecord{
      game_id: game_id,
      players: state.players,
      scores: state.scores,
      rounds: state.current_round,
      ended_at: NaiveDateTime.utc_now()
    }

    Repo.insert(game_record)
  end
end
