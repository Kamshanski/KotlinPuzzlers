plugins {
	kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	val coroutines = "1.9.0-RC"

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")

	testImplementation(kotlin("test"))
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines")
}

tasks.test {
	useJUnitPlatform()
}
kotlin {
	jvmToolchain(21)
}