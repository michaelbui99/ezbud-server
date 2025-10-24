plugins {
	java
	id("org.springframework.boot") version "3.5.7"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jooq.jooq-codegen-gradle") version "3.20.8"
}

group = "dk.michaelbui"
version = "0.0.1-SNAPSHOT"
description = "EzBud Server"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")
	implementation(libs.springdoc.openapi.starter.webmvc.ui)
	implementation(libs.jooq)

	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	jooqCodegen("org.postgresql:postgresql:42.7.3")
}

val DB_USER = System.getenv("EZBUD_DB_USERNNAME") ?: "postgres"
val DB_PASSWORD = System.getenv("EZBUD_DB_PASSWORD") ?: "postgres"
val DB_HOST = System.getenv("EZBUD_DB_HOST") ?: "localhost"
val DB_PORT = System.getenv("EZBUD_DB_PORT") ?: "5432"
val DB_DB = System.getenv("EZ_BUD_DB_DB") ?: "ezbud"

val dbUrl = "jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_DB";

jooq{
	configuration {
		jdbc{
			driver = "org.postgresql.Driver"
			url = dbUrl
			user = DB_USER
			password = DB_PASSWORD
		}

		generator {
			database {
				name = "org.jooq.meta.postgres.PostgresDatabase"
				inputSchema= "public"
			}

			target {
				directory = "src/main/java"
				packageName = "dk.michaelbui.ezbud_server.generated"
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

