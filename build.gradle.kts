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
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21

	withSourcesJar()
	//withJavadocJar()
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "21"
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

val nexusSnapshotUrl: String by project
val nexusUrl: String by project
val nexusUser: String by project
val nexusPassword: String by project

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "de.simpletactics"
			artifactId = "der-duemmste-ist-raus-lib"
			version = version
			from(components["java"])
		}
	}
	repositories {
		maven {
			name = "nexus"
			url = if (version.toString().contains("SNAPSHOT", true)) {
				uri(nexusSnapshotUrl)
			} else {
				uri(nexusUrl)
			}
			credentials {
				username = nexusUser
				password = nexusPassword
			}
		}
	}
}
