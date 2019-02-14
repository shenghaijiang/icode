<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>

    <!-- !!!! Driver Class Path !!!! -->
    <classPathEntry location="D:/maven/repository/mysql/mysql-connector-java/5.1.41/mysql-connector-java-5.1.41.jar"/>

    <context id="context" targetRuntime="MyBatis3">
        <property name="autoDelimitKeywords" value="true" />
        <property name="beginningDelimiter" value="`"></property>
        <property name="endingDelimiter" value="`"></property>

        <plugin type="org.mybatis.generator.plugins.CaseInsensitiveLikePlugin"></plugin>
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin>

        <commentGenerator>
            <property name="suppressAllComments" value="false"/>
            <property name="suppressDate" value="true"/>
        </commentGenerator>

        <!-- !!!! Database Configurations !!!! -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://${model.dbHost}/${model.dbName}" userId="${model.dbUser}"
                        password="${model.dbPassword}"/>

        <!-- <jdbcConnection
                 driverClass="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                 connectionURL="jdbc:sqlserver://192.168.2.103:1433;Database=ipmes"
                 userId="sa"
                 password="IL4SsadfjASDFi@da34d"/>-->

        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- !!!! Model Configurations !!!! -->
        <javaModelGenerator targetPackage="${model.packageName}.entity" targetProject="generator">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!-- !!!! Mapper XML Configurations !!!! -->
        <sqlMapGenerator targetPackage="${model.packageName}.service" targetProject="generator">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>

        <!-- !!!! Mapper Interface Configurations !!!! -->
        <javaClientGenerator targetPackage="${model.packageName}.mapper" targetProject="generator" type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>

<#list model.tables as c>
        <!-- !!!! Table Configurations !!!! -->
        <table tableName="${c.tableName}" domainObjectName="${c.tableComment}" delimitIdentifiers="true" enableCountByExample="true"
               enableDeleteByExample="false" enableSelectByExample="true"
               enableUpdateByExample="true">
            <property name="useActualColumnNames" value="true"></property>
            <generatedKey column="Id" sqlStatement="JDBC"/>
        </table>
</#list>
    </context>
</generatorConfiguration>