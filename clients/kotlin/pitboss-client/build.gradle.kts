plugins {
    kotlin("jvm") version "2.0.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "dev.makeall.pitboss"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks {
    // Ensure Kotlin sources are compiled before building the fat jar
    shadowJar {
        archiveFileName.set("pitboss-client.jar") // Name of the fat jar
        manifest {
            attributes["Main-Class"] = "dev.makeall.pitboss.MainKt" // Change to your main class path
        }
    }
}