rootProject.name = "der-duemmste-ist-raus-lib"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val gitPropertiesPluginVersion: String by settings
    val versionCheckPluginVersion: String by settings
    val openapiPluginVersion: String by settings
    plugins {
        // Kotlin
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion

        // Spring
        id("org.springframework.boot") version springBootVersion

        // Build
        id("com.gorylenko.gradle-git-properties") version gitPropertiesPluginVersion

        // Analytics
        id("com.github.ben-manes.versions") version versionCheckPluginVersion

        // Code generation
        id("org.openapi.generator") version openapiPluginVersion
    }
}
