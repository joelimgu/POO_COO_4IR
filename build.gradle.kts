plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("org.example.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("com.google.code.gson:gson:2.10")
    implementation("io.github.cdimascio:dotenv-java:2.2.4")
    implementation("org.jetbrains:annotations-java5:16.0.2")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

