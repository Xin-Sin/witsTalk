plugins {
    id("java")
}

group = "top.xinsin"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.testng:testng:7.7.0")
    implementation(project(mapOf("path" to ":lib-common")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(":lib-user"))
}

tasks.test {
    useJUnitPlatform()
}