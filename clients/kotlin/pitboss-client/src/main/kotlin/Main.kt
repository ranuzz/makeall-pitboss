package dev.makeall.pitboss

import dev.makeall.pitboss.server.createMessage
import dev.makeall.pitboss.server.getWebSocketClient
import dev.makeall.pitboss.test.testHandler
import java.util.UUID

fun generateUniqueId(): String {
  return UUID.randomUUID().toString()
}

suspend fun main(args: Array<String>) {
  println("Running pitboss client")

  var host = false
  var gameId: String = generateUniqueId()
  var testId: String = "creategame"
  val playerId = generateUniqueId()

  if (args.isEmpty()) {
    println("usage: --host --join id --test testId")
    return
  }

  var i = 0
  while (i < args.size) {
    when (args[i]) {
      "--host" -> {
        host = true
        i++ // Move to the next argument
      }
      "--join" -> {
        if (i + 1 < args.size) {
          gameId = args[i + 1]
          i += 2 // Move to the next argument pair
        } else {
          println("Error: --join requires a game ID")
          return
        }
      }
      "--test" -> {
        if (i + 1 < args.size) {
          testId = args[i + 1]
          i += 2 // Move to the next argument pair
        } else {
          println("Error: --api requires a test ID")
          return
        }
      }
      else -> {
        println("Unknown argument: ${args[i]}")
        return
      }
    }
  }

  val webSocket = getWebSocketClient()

  if (host) {
    println("You are hosting the game")
    webSocket.send(createMessage(gameId, playerId, "phx_join"))
  } else if (!gameId.isNullOrEmpty()) {
    println("Joining Game ID: $gameId")
    webSocket.send(createMessage(gameId, playerId, "heartbeat"))
  } else if (!testId.isNullOrEmpty()) {
    println("Running test ID: $testId")
    testHandler(testId)
  }
}
