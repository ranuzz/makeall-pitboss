package dev.makeall.pitboss.server

import kotlinx.coroutines.*
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

// Coroutine-based WebSocket Listener
class PitboassWebSocketListener(private val onOpenCallback: suspend (WebSocket) -> Unit) :
        WebSocketListener() {

  override fun onOpen(webSocket: WebSocket, response: Response) {
    // Resume the coroutine when the connection is open
    GlobalScope.launch { onOpenCallback(webSocket) }
  }

  override fun onMessage(webSocket: WebSocket, text: String) {
    println("Received message: $text")
  }

  override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    println("Closing WebSocket: $code / $reason")
    webSocket.close(1000, null)
  }

  override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    println("Error: ${t.message}")
  }
}
