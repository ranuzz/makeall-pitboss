package dev.makeall.pitboss.server

// Define the message data class
data class Message(
        val topic: String,
        val event: String,
        val payload: Map<String, Any>,
        val ref: Int
)
