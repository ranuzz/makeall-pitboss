defmodule PitbossWeb.UserSocket do
  require Logger
  use Phoenix.Socket

  channel "game:*", PitbossWeb.GameChannel

  def connect(_params, socket, _connect_info) do
    Logger.debug("joining")
    {:ok, socket}
  end

  def id(_socket), do: nil
end
