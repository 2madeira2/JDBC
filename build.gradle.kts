plugins {
    java
}

group = "ru.madeira"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.flywaydb:flyway-core:8.0.3")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("org.jetbrains:annotations:22.0.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}