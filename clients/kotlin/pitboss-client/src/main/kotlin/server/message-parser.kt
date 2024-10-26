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
      if (gameMessage.payload["status"] == "error") {
        serverMessage("ERROR")
        userPrompt()
      } else {
        serverMessage("PASSED")
        userPrompt()
      }
    }
    "game_over" -> {
      serverMessage("Game Over")
      println(gameMessage.payload["scores"])
      userPrompt()
    }
    "phx_error" -> {
      serverMessage("Error Occured")
      println(gameMessage.payload)
      userPrompt()
    }
    else -> println("Unknown event ${gameMessage.event}")
  }
}
