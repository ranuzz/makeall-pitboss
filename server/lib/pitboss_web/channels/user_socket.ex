defmodule PitbossWeb.UserSocket do
  require Logger
  use Phoenix.Socket

  channel "game:*", PitbossWeb.GameChannel

  def connect(params, socket, _connect_info) do
    Logger.debug("connected")
    Logger.debug(params)
    {:ok, socket}
  end

  def id(_socket), do: nil
end
