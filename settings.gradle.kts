rootProject.name = "der-duemmste-ist-raus-lib"

pluginManagement {
    val springBootVersion: String by settings
    val gitPropertiesPluginVersion: String by settings
    val versionCheckPluginVersion: String by settings
    val openapiPluginVersion: String by settings
    plugins {
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
