<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>${model.code}</artifactId>
        <groupId>${model.packageName?replace("."+model.code, "")}</groupId>
        <version>1.0.0</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>${model.code}-core</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    <name>${model.code}-core</name>
    <dependencies>
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
        </dependency>
        <dependency>
            <groupId>${model.packageName?replace("."+model.code, "")}</groupId>
            <artifactId>${model.code}-domain</artifactId>
        </dependency>
        <dependency>
            <groupId>${model.packageName?replace("."+model.code, "")}</groupId>
            <artifactId>${model.code}-sapi</artifactId>
        </dependency>

    </dependencies>
    <build>
        <finalName>${model.code}-core</finalName>
    </build>

</project>