defmodule PitbossWeb.GameChannel do
  use Phoenix.Channel
  require Logger
  alias Pitboss.Game

  def join("game:" <> game_id, _params, socket) do
    case Game.start_link(game_id) do
      {:ok, _pid} ->
        {:ok, assign(socket, :game_id, game_id)}

      {:error, {:already_started, _pid}} ->
        {:ok, assign(socket, :game_id, game_id)}

      _ ->
        {:error, %{reason: "server error"}}
    end
  end

  def handle_in("join_game", %{"player_id" => player_id}, socket) do
    game_id = socket.assigns.game_id

    case Game.join(game_id, player_id) do
      :ok ->
        broadcast!(socket, "player_joined", %{player_id: player_id})
        {:reply, :ok, socket}

      {:error, reason} ->
        {:reply, {:error, %{reason: reason}}, socket}
    end
  end

  def handle_in("start_game", _params, socket) do
    game_id = socket.assigns.game_id

    case Game.start_game(game_id) do
      :ok ->
        broadcast!(socket, "game_started", %{})
        {:reply, :ok, socket}

      {:error, reason} ->
        {:reply, {:error, %{reason: reason}}, socket}
    end
  end

  def handle_in("submit_answer", %{"player_id" => player_id, "answer" => answer}, socket) do
    game_id = socket.assigns.game_id

    case Game.submit_answer(game_id, player_id, answer) do
      :ok ->
        {:reply, :ok, socket}

      {:error, reason} ->
        {:reply, {:error, %{reason: reason}}, socket}
    end
  end

  def handle_in("hearbeat", _params, socket) do
    Logger.debug("heartbeat received")
    {:reply, :ok, socket}
  end

  def handle_info({:new_round, prompt}, socket) do
    push(socket, "new_round", %{prompt: prompt})
    {:noreply, socket}
  end

  def handle_info({:game_over, scores}, socket) do
    push(socket, "game_over", %{scores: scores})
    {:stop, :normal, socket}
  end
end
