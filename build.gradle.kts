plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

javafx {
    version = "17.0.2"
    modules("javafx.controls", "javafx.fxml")

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
    implementation("org.controlsfx:controlsfx:11.1.1")
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("com.google.code.gson:gson:2.10")
    implementation("io.github.cdimascio:dotenv-java:2.2.4")
    implementation("org.jetbrains:annotations-java5:16.0.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}



