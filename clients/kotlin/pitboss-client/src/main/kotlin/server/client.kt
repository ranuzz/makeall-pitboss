package dev.makeall.pitboss.server

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

suspend fun getWebSocketClient(): WebSocket {
  val client = OkHttpClient()
  // Coroutine function to open a WebSocket connection and send a message
  suspend fun connectAndAttachListener(): WebSocket = suspendCoroutine { continuation ->
    val request = Request.Builder().url(WEBSOCKET_BASE).build()

    // Coroutine callback to handle sending a message once connected
    val listener = PitboassWebSocketListener { webSocket ->
      continuation.resume(webSocket)
      println("websocket connected callback...")
    }

    client.newWebSocket(request, listener)
  }

  return connectAndAttachListener()
}
