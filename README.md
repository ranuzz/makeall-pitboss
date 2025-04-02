# Pitboss: Server for Turn Based Multiplayer Games

Multiplayer games are always fun. Even if you're not a gamer, joining a lobby and playing with your friends is never boring. But making a multiplayer game? Not so much.

As easy as it is to pick up a game engine and code a single-player action-adventure, adding multiplayer into the mix is not straightforward at all. There are game engines with built-in multiplayer networking support, as well as services and SaaS tools to manage game servers, but they all seem either too restrictive or a hassle to set up.

At least, that's what I told myself to justify building my own multiplayer game server. So here I am, exploring how to make one.

In terms of basic requirements, all I need is a remote server that can exchange game state data across users in a shared game session. Latency will definitely play a huge role, but I think I can work around it if I focus only on round-based or turn-based games.

I'm choosing Elixir for this project—partly because I need an excuse to work with it.


#### Latency

For a real-time game latency matters a lot.
If I add a constraint that the server is only suitable for
round or turn based games then I can possibly get away with slower response times.

#### Capacity planning

Any good stack should be able to vertically or horizontally scale.
For my design I plan to use in-memory server state for game sessions.
This makes horizontal scaling not so straightforward. But for my use case
I can just add this as a constraint and assume there will be no horizontal scaling.
The vertically scaled server should be able to handle couple thousand sessions at the
same time

#### Must have Features

- Host Game (predefined game templates)
- Share Game via link
- Join Game via link or code
- In-Game player chat

#### Good to have Features

- Custom Game Templates
- Game History
- Leaderboard

#### Data Structures

- Game templates
  <br />
  read only game definition that defines game rules, play conditions, win-lose scenarios
  <br />
- Game instance
  <br />
  In memory instance of a game template that keeps track of the game session
  <br />
- Game state
  <br />
  The game state at any given time. This should be same across all players and game
  servers. On concluding the game this may optionaly persist in the db
  <br />

#### Clients

- JavaScript client for the web
- Kotlin client for android and CLI

### TODO

Dev setup (sample client and local server)
Stress testing (RPS, maximum games)
Production deployment
