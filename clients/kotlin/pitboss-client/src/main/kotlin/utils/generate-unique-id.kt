package dev.makeall.pitboss.utils

import java.util.UUID

fun generateUniqueId(): String {
  return UUID.randomUUID().toString()
}
