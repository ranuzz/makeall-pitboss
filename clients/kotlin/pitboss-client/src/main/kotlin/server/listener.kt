package dev.makeall.pitboss.server

import dev.makeall.pitboss.utils.debug
import kotlinx.coroutines.*
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

// Coroutine-based WebSocket Listener
class PitbossWebSocketListener(
        private val scope: CoroutineScope,
        private val onOpenCallback: suspend (WebSocket) -> Unit
) : WebSocketListener() {

  override fun onOpen(webSocket: WebSocket, response: Response) {
    debug("onOpen: Coroutine about to launch")
    val job =
            scope.launch {
              debug("onOpen: Coroutine started")
              onOpenCallback(webSocket)
              debug("onOpen: Coroutine completed")
            }
    debug("onOpen: Coroutine launched with job: ${job}")
    debug("onOpen: Coroutine state: ${job.isActive}")
  }

  override fun onMessage(webSocket: WebSocket, text: String) {
    println("Received message: $text")
  }

  override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
    debug("Closing WebSocket: $code / $reason")
    webSocket.close(1000, null)
    debug("WebSocket closed")
  }

  override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
    debug("Error: ${t.message}")
  }
}
