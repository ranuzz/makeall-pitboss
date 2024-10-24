package dev.makeall.pitboss.server

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response
import com.google.gson.Gson
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// Define the message data class
data class Message(
    val topic: String,
    val event: String,
    val payload: Map<String, Any>,
    val ref: Int
)

// Coroutine-based WebSocket Listener
class MyWebSocketListener(
    private val onOpenCallback: suspend (WebSocket) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        // Resume the coroutine when the connection is open
        GlobalScope.launch {
            onOpenCallback(webSocket)
        }
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

// Main function to send a WebSocket message using coroutines
fun inline() = runBlocking {
    val client = OkHttpClient()

    // Coroutine function to open a WebSocket connection and send a message
    suspend fun connectAndSendMessage(): WebSocket = suspendCoroutine { continuation ->
        val request = Request.Builder()
            .url("ws://localhost:4000/socket/websocket")  // Replace with your WebSocket server URL
            .build()

        // Coroutine callback to handle sending a message once connected
        val listener = MyWebSocketListener { webSocket ->
            continuation.resume(webSocket)

            // Once WebSocket is open, send a message
            val message = Message(
                topic = "game:game_id_123",
                event = "phx_join",
                payload = emptyMap(),
                ref = 1
            )

            val gson = Gson()
            val jsonMessage = gson.toJson(message)

            // Send the JSON message asynchronously
            webSocket.send(jsonMessage)
            println("Sent message: $jsonMessage")
        }

        client.newWebSocket(request, listener)
    }

    // Launch the coroutine to connect and send the WebSocket message
    connectAndSendMessage()

    // Optionally, close the client dispatcher after all WebSocket messages have been sent
    client.dispatcher.executorService.shutdown()
}
