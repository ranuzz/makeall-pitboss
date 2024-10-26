package dev.makeall.pitboss.server

import com.google.gson.Gson
import dev.makeall.pitboss.utils.debug
import dev.makeall.pitboss.utils.messageRef

fun createMessage(gameId: String, playerId: String, event: String): String {
  return createMessageInternal(gameId, mapOf("player_id" to playerId), event)
}

fun submitAnswer(gameId: String, playerId: String, answer: String): String {
  return createMessageInternal(
          gameId,
          mapOf("player_id" to playerId, "answer" to answer),
          "submit_answer"
  )
}

fun createMessageInternal(gameId: String, payload: Map<String, Any>, event: String): String {
  val message = Message("game:$gameId", event, payload, messageRef)
  messageRef = messageRef + 1
  val gson = Gson()
  val jsonMessage = gson.toJson(message)
  debug("Created message: $jsonMessage")
  return jsonMessage
}
