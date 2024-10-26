package dev.makeall.pitboss.server

import com.google.gson.Gson

fun serverMessage(message: Any) {
  println("[Server] $message")
}

fun userPrompt() {
  println("")
  print("> ")
}

fun messageParser(message: String) {
  val gson = Gson()
  val gameMessage = gson.fromJson(message, Message::class.java)
  when (gameMessage.event) {
    "new_round" -> {
      serverMessage(gameMessage.payload["prompt"] ?: "No prompt available")
      userPrompt()
    }
    "game_started" -> {
      serverMessage("Game Started!!")
      userPrompt()
    }
    "player_joined" -> {
      serverMessage("New player joined ${gameMessage.payload["player_id"]}")
      userPrompt()
    }
    "phx_reply" -> {
      serverMessage("ACK")
      userPrompt()
    }
    else -> println("Unknown event ${gameMessage.event}")
  }
}
