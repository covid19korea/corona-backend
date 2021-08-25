import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val springFoxVersion: String by project
val junitVersion: String by project
val jacksonXmlVersion: String by project

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "com.corona"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // WEB
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // CACHING
    implementation("org.springframework.boot:spring-boot-starter-cache:2.5.3")
    // WEBCLIENT
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    // TEST
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // JACKSON
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonXmlVersion")
    // SWAGGER
    implementation("io.springfox:springfox-swagger2:$springFoxVersion")
    implementation("io.springfox:springfox-swagger-ui:$springFoxVersion")
    // ETC
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
