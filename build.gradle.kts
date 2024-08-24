plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.ming"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

extra["mapstructVersion"] = "1.5.5.Final"
extra["lombokMapstructBindingVersion"] = "0.2.0"
extra["jjwtVersion"] = "0.12.5"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// Mapstruct
	implementation ("org.mapstruct:mapstruct:${property("mapstructVersion")}")
	annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")
	// Lombok Mapstruct Binding
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${property("lombokMapstructBindingVersion")}")
	// jjwt
	implementation("io.jsonwebtoken:jjwt:${property("jjwtVersion")}")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
