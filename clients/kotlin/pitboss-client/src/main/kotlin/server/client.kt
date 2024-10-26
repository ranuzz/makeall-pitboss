package dev.makeall.pitboss.server

import dev.makeall.pitboss.utils.debug
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

suspend fun getWebSocketClient(scope: CoroutineScope, client: OkHttpClient): WebSocket {
  // Coroutine function to open a WebSocket connection and send a message
  suspend fun connectAndAttachListener(): WebSocket = suspendCoroutine { continuation ->
    val request = Request.Builder().url(WEBSOCKET_BASE).build()

    // Coroutine callback to handle sending a message once connected
    val listener =
            PitbossWebSocketListener(scope) { webSocket ->
              continuation.resume(webSocket)
              debug("websocket connected callback...")
            }

    debug("connectAndAttachListener: returning")
    client.newWebSocket(request, listener)
  }

  debug("getWebSocketClient: returning")
  return connectAndAttachListener()
}
