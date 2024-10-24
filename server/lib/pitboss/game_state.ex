defmodule Pitboss.GameState do
  defstruct status: :waiting,
            players: [],
            current_round: 0,
            scores: %{},
            answers: %{}
end
