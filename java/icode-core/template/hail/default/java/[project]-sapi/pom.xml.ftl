<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>${model.packageName?replace("."+model.code, "")}</groupId>
    <artifactId>${model.code}-sapi</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>${model.code}-sapi</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <artifactId>${model.code}</artifactId>
        <groupId>${model.packageName?replace("."+model.code, "")}</groupId>
        <version>1.0.0</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

</project>
