dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}