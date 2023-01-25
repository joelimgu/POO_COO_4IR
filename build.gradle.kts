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
val umlDoclet: Configuration by configurations.creating

application {
    //mainClass.set("org.example.Main")
    mainClass.set("org.example.Main")
}


tasks.jar {
    manifest {
//        attributes["Class-Path"] = configurations.compile.collect { it.getName() }.join(' ');
        attributes["Main-Class"] = "org.example.Main"
//        attributes(mapOf(
//            "Clavardage-BigSur" to project.name,
//            "0" to project.version
//        ),
////            'Class-Path': configurations.compile.collect { it.getName() }.join(' '),
//        'Main-Class': 'org.example.Main')
    }
}

java {
    withSourcesJar()
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
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    umlDoclet("nl.talsmasoftware:umldoclet:2.0.15")
}

configurations {
    umlDoclet
}

tasks.javadoc {
    source = sourceSets.main.get().allJava
    val docletOptions = options as StandardJavadocDocletOptions
    docletOptions.docletpath = umlDoclet.files.toList()
    docletOptions.doclet = "nl.talsmasoftware.umldoclet.UMLDoclet"
//    docletOptions.addStringOption("additionalParamName", "additionalParamValue")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to "org.example.Main")
        }
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        sourcesMain.resources.forEach { println("Add from resources: ${it.name}") }
        from(sourcesMain.output)
    }
}

