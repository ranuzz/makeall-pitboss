package dev.makeall.pitboss

import dev.makeall.pitboss.server.inline
import dev.makeall.pitboss.test.testHandler

suspend fun main(args: Array<String>) {
    println("Running pitboss client")

    var host = false
    var gameId: String? = null
    var testId: String? = null

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
            "--api" -> {
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

    if (host) {
        println("You are hosting the game")
        inline()
    } else if (!gameId.isNullOrEmpty()) {
        println("Joining Game ID: $gameId")
    } else if (!testId.isNullOrEmpty()) {
        println("Running test ID: $testId")
        testHandler(testId)
    }
}