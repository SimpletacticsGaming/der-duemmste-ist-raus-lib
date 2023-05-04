import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "de.simpletactics"
version = "0.0.1"

plugins {
	java
	`maven-publish`

	// Kotlin
	kotlin("jvm")
	kotlin("plugin.spring")
	kotlin("plugin.noarg")

	id("org.springframework.boot")
	id("com.gorylenko.gradle-git-properties")
	id("com.github.ben-manes.versions")
	id("org.openapi.generator")
}
apply(plugin = "io.spring.dependency-management")

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11

	withSourcesJar()
	withJavadocJar()
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	// implementation("org.flywaydb:flyway-core")
	// implementation("org.flywaydb:flyway-mysql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.getByName<Jar>("jar") {
 	enabled = false
 }

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.wrapper {
	val versionGradle: String by project
	gradleVersion = versionGradle
}

tasks.bootJar {
	enabled = false
	mainClass.set("de.simpletactics.Application")
}

tasks.jar {
	enabled = true
	archiveClassifier.set("")
	manifest.attributes["Main-Class"] = "de.simpletactics.Application"
}

tasks.register("bootRunLocal") {
	group = "application"
	description = "Runs the Spring Boot application with the local profile"
	doFirst {
		tasks.bootRun.configure {
			systemProperty("spring.profiles.active", "local,secrets")
		}
	}
	finalizedBy("bootRun")
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "de.simpletactics"
			artifactId = "der-duemmste-ist-raus-lib"
			version = version
			from(components["java"])
		}
	}
}
