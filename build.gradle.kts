plugins {
    id("java")
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
}

tasks.create("num") {
    println(project.findProperty("springbootVersion"))
}

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = project.findProperty("group")!!
    version = project.findProperty("version")!!

    java {
        sourceCompatibility = JavaVersion.VERSION_17
    }

    repositories {
        mavenLocal()
        maven("https://maven.aliyun.com/repository/public")
        mavenCentral()
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")

        implementation("com.mybatis-flex:mybatis-flex-spring-boot-starter:1.6.5")
        implementation("com.mysql:mysql-connector-j:8.1.0")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("cn.hutool:hutool-all:5.8.22")
        implementation("com.zaxxer:HikariCP:5.0.1")
        implementation("io.jsonwebtoken:jjwt-api:0.11.5")

        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

        compileOnly("org.projectlombok:lombok")

        annotationProcessor("com.mybatis-flex:mybatis-flex-processor:1.6.5")
        annotationProcessor("org.projectlombok:lombok")
    }
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.2")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Test> {
    useJUnitPlatform()
}