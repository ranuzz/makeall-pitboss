package dev.makeall.pitboss

import dev.makeall.pitboss.server.createMessage
import dev.makeall.pitboss.server.getWebSocketClient
import dev.makeall.pitboss.server.handleUserInput
import dev.makeall.pitboss.test.testHandler
import dev.makeall.pitboss.utils.debug
import dev.makeall.pitboss.utils.generateUniqueId
import dev.makeall.pitboss.utils.globalConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
  debug("Running pitboss client")

  var host = false
  var gameId: String = generateUniqueId()
  var testId: String = "creategame"
  val playerId = generateUniqueId()

  if (args.isEmpty()) {
    debug("usage: --host --join id --test testId")
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
          debug("Error: --join requires a game ID")
          return
        }
      }
      "--test" -> {
        if (i + 1 < args.size) {
          testId = args[i + 1]
          i += 2 // Move to the next argument pair
        } else {
          debug("Error: --api requires a test ID")
          return
        }
      }
      "--verbose" -> {
        globalConfig.verbose = true
        i++
      }
      else -> {
        debug("Unknown argument: ${args[i]}")
        return
      }
    }
  }

  val job = Job()
  val scope = CoroutineScope(Dispatchers.Default + job)

  runBlocking {
    debug("runBlocking: Starting")
    val webSocket = getWebSocketClient(scope)
    debug("runBlocking: WebSocket client obtained")

    // Sleep for 5 seconds
    debug("runBlocking: Sleeping for 5 seconds..")
    Thread.sleep(5000)
    debug("runBlocking: Woke up from sleep")

    if (host) {
      println("Game Id: $gameId")
      debug("runBlocking: You are hosting the game")
      webSocket.send(createMessage(gameId, playerId, "phx_join"))
      debug("runBlocking: Sent phx_join message")
      webSocket.send(createMessage(gameId, playerId, "join_game"))
      debug("runBlocking: Sent join_game message")
    } else if (!gameId.isNullOrEmpty()) {
      debug("runBlocking: Joining Game ID: $gameId")
      webSocket.send(createMessage(gameId, playerId, "phx_join"))
      debug("runBlocking: Sent phx_join message")
      webSocket.send(createMessage(gameId, playerId, "join_game"))
      debug("runBlocking: Sent join_game message")
    } else if (!testId.isNullOrEmpty()) {
      debug("runBlocking: Running test ID: $testId")
      testHandler(testId)
      debug("runBlocking: Test handler completed")
    }

    // Waiting loop for user input
    debug("Enter 'exit' to quit or 'status' to request status.")
    while (true) {
      print("> ")
      val input = readLine()
      if (input != null) {
        handleUserInput(input, webSocket, gameId, playerId)
        if (input.trim() == "exit") break
      }
    }

    debug("runBlocking: Cancelling the coroutine")
    job.cancel()
    debug("runBlocking: Coroutine cancelled")
  }

  debug("all done with code")
}
