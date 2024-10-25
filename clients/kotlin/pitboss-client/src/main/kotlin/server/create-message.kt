package dev.makeall.pitboss.server

import com.google.gson.Gson

fun createMessage(gameId: String, playerId: String, event: String): String {
  val message = Message("game:$gameId", event, emptyMap(), 1)
  val gson = Gson()
  val jsonMessage = gson.toJson(message)
  println("Created message: $jsonMessage")
  return jsonMessage
}
