package dev.makeall.pitboss.server

import okhttp3.WebSocket

fun handleUserInput(input: String, webSocket: WebSocket, gameId: String, playerId: String) {
  when (input.trim()) {
    "exit" -> {
      println("Exiting...")
      webSocket.close(1000, "User requested exit")
      return
    }
    "status" -> {
      webSocket.send(createMessage(gameId, playerId, "status"))
    }
    "start_game" -> {
      webSocket.send(createMessage(gameId, playerId, "start_game"))
    }
    "submit_answer" -> {
      print("answer: ")
      val answer = readLine()
      if (answer != null) {
        webSocket.send(submitAnswer(gameId, playerId, answer))
      }
    }
    else -> {
      println("Unknown command: $input")
    }
  }
}
