dependencies {
    implementation("org.testng:testng:7.7.0")
    implementation(project(mapOf("path" to ":lib-common")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation(project(mapOf("path" to ":lib-user")))
    implementation(project(mapOf("path" to ":lib-voice")))
    implementation(project(mapOf("path" to ":lib-fileTransfer")))
    implementation(project(mapOf("path" to ":lib-chat")))
}

tasks.test {
    useJUnitPlatform()
}

tasks.bootJar {
    val env = "prod"
    val current = "xinsin"
    doFirst {
        val file = File("${buildDir}/resources/main/application.properties")
        var allText = ""
        file.forEachLine { line ->
            allText += if (line.endsWith(current) && line.replace(current, env) != "") {
                line.replace(current, env)
            }else{
                line
            }
        }
        file.writeText(allText)
    }
    dependsOn("compileJava", "processResources", "classes")
}