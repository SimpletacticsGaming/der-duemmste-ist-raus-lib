group = "de.simpletactics"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
	java
	`maven-publish`

	id("org.springframework.boot")
	id("com.gorylenko.gradle-git-properties")
	id("com.github.ben-manes.versions")
	id("org.openapi.generator")
}
apply(plugin = "io.spring.dependency-management")

java {

	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}

	// sourceCompatibility = JavaVersion.VERSION_17
	// targetCompatibility = JavaVersion.VERSION_17


	withSourcesJar()
	withJavadocJar()
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-mysql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.wrapper {
	val versionGradle: String by project
	gradleVersion = versionGradle
}

tasks.bootJar {
	mainClass.set("de.simpletactics.der-duemmste-ist-raus-lib.Application")
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
