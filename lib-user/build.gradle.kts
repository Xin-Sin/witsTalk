dependencies {
    implementation(project(mapOf("path" to ":lib-common")))
    implementation("commons-codec:commons-codec:1.16.0")
}

sourceSets {
    main{
        resources {
            srcDirs("src/main/java")
        }
    }
}