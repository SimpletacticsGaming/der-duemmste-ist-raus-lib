import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "de.simpletactics"
version = "0.0.1-SNAPSHOT"

tasks.wrapper {
	gradleVersion = "8.1.1"
	// You can either download the binary-only version of Gradle (BIN) or
	// the full version (with sources and documentation) of Gradle (ALL)
	distributionType = Wrapper.DistributionType.ALL
}

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

	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}

	// java.sourceCompatibility = JavaVersion.VERSION_17
	// java.targetCompatibility = JavaVersion.VERSION_17

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

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
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
	mainClass.set("de.simpletactics.Application")
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
			artifactId = "library"
			version = "0.1"

			from(components["java"])
		}
	}
}


