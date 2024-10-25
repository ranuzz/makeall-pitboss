package dev.makeall.pitboss.server

import okhttp3.OkHttpClient

fun shutdown(client: OkHttpClient) = {
    // close the client dispatcher after all WebSocket messages have been sent
    client.dispatcher.executorService.shutdown()
}
