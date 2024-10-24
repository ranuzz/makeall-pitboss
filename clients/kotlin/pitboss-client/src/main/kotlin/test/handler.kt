package dev.makeall.pitboss.test

fun testHandler(testId: String) {
    when (testId.lowercase()) {
        "creategame" -> {
            println("Running createGame...")
        }
    }
}
