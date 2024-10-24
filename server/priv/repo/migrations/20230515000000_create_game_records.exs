defmodule Pitboss.Repo.Migrations.CreateGameRecords do
  use Ecto.Migration

  def change do
    create table(:game_records) do
      add :game_id, :string, null: false
      add :players, {:array, :string}, null: false
      add :scores, :map, null: false
      add :rounds, :integer, null: false
      add :ended_at, :naive_datetime, null: false

      timestamps()
    end

    create index(:game_records, [:game_id])
  end
end
