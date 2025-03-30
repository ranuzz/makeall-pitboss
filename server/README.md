# Pitboss

To start your Phoenix server:

- Run `mix setup` to install and setup dependencies
- Start Phoenix endpoint with `mix phx.server` or inside IEx with `iex -S mix phx.server`

Now you can visit [`localhost:4000`](http://localhost:4000) from your browser.

Ready to run in production? Please [check our deployment guides](https://hexdocs.pm/phoenix/deployment.html).

## Learn more

- Official website: https://www.phoenixframework.org/
- Guides: https://hexdocs.pm/phoenix/overview.html
- Docs: https://hexdocs.pm/phoenix
- Forum: https://elixirforum.com/c/phoenix-forum
- Source: https://github.com/phoenixframework/phoenix

## Generated Using

```
mix phx.new server --app pitboss --module Pitboss --database postgres --no-assets --no-dashboard --no-esbuild --no-gettext --no-html --no-live --no-mailer --no-tailwind --binary-id
```

## Project Structure

```
server/
├── lib/
│ ├── pitboss/
│ │ ├── application.ex
│ │ ├── game.ex
│ │ ├── game_record.ex
│ │ ├── game_state.ex
│ │ └── game_supervisor.ex
│ ├── pitboss_web/
│ │ ├── channels/
│ │ │ ├── game_channel.ex
│ │ │ └── user_socket.ex
│ │ ├── endpoint.ex
│ │ └── router.ex
│ └── pitboss.ex
├── priv/
│ └── repo/
│ └── migrations/
│ └── 20230515000000_create_game_records.exs
├── config/
│ ├── config.exs
│ ├── dev.exs
│ ├── prod.exs
│ └── test.exs
└── mix.exs
```

## Running

- run postgres server
- populate the environment variables

```
export PITBOSS_POSTGRES_DATABASE=
export PITBOSS_POSTGRES_PASSWORD=
export PITBOSS_POSTGRES_PORT=
export PITBOSS_SESSION_SALT=
export PITBOSS_SECRET_KEY_BASE=
```

```
source ../settings/.env.sh
mix phx.server
```
