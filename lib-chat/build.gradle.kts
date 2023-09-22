dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    implementation(project(mapOf("path" to ":lib-common")))
    implementation(project(mapOf("path" to ":lib-user")))
}