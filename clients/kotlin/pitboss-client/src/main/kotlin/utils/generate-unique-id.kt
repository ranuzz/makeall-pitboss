package dev.makeall.pitboss.utils

import java.util.UUID
import kotlin.random.Random

fun generateUniqueId(): String {
  return UUID.randomUUID().toString()
}

fun generateRandomWord(length: Int): String {
  val chars = ('A'..'Z').toList()
  return (1..length).map { chars[Random.nextInt(chars.size)] }.joinToString("")
}

fun generatePlayerId(): String {
  return generateRandomWord(5)
}

fun generateGameId(): String {
  val first = generateRandomWord(4)
  val second = generateRandomWord(4)
  return "$first-$second"
}
