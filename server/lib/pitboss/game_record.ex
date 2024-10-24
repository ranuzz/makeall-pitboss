defmodule Pitboss.GameRecord do
  use Ecto.Schema
  import Ecto.Changeset

  schema "game_records" do
    field :game_id, :string
    field :players, {:array, :string}
    field :scores, :map
    field :rounds, :integer
    field :ended_at, :naive_datetime

    timestamps()
  end

  def changeset(game_record, attrs) do
    game_record
    |> cast(attrs, [:game_id, :players, :scores, :rounds, :ended_at])
    |> validate_required([:game_id, :players, :scores, :rounds, :ended_at])
  end
end
