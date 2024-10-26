package dev.makeall.pitboss.server

import com.google.gson.Gson

fun createMessage(gameId: String, playerId: String, event: String): String {
  val message = Message("game:$gameId", event, mapOf("player_id" to playerId), 1)
  val gson = Gson()
  val jsonMessage = gson.toJson(message)
  println("Created message: $jsonMessage")
  return jsonMessage
}
