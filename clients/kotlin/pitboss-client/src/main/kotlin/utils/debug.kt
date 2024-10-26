package dev.makeall.pitboss.utils

fun debug(message: String) {
  if (globalConfig.verbose) {
    println(message)
  }
}
