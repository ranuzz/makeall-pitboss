defmodule Pitboss.Repo do
  use Ecto.Repo,
    otp_app: :pitboss,
    adapter: Ecto.Adapters.Postgres
end
