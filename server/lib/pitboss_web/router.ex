defmodule PitbossWeb.Router do
  use PitbossWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/api", PitbossWeb do
    pipe_through :api
  end
end
