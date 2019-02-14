<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd" >
<generatorConfiguration>

    <context id="context" targetRuntime="MyBatis3">
        <property name="autoDelimitKeywords" value="true" />
        <property name="beginningDelimiter" value="`"></property>
        <property name="endingDelimiter" value="`"></property>

        <plugin type="org.mybatis.generator.plugins.CaseInsensitiveLikePlugin"></plugin>
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"></plugin>
        <plugin type="cn.xtits.xtf.mybatis.ExampleQueryPlugin" />

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
        <javaModelGenerator targetPackage="${model.packageName}.entity" targetProject="../${model.code}-domain/src/main/java">
            <property name="enableSubPackages" value="false"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>

        <!-- !!!! Mapper XML Configurations !!!! -->
        <sqlMapGenerator targetPackage="mapper" targetProject="../${model.code}-core/src/main/resources">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>

        <!-- !!!! Mapper Interface Configurations !!!! -->
        <javaClientGenerator targetPackage="${model.packageName}.mapper.base" targetProject="../${model.code}-core/src/main/java" type="XMLMAPPER">
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>
        <!-- !!!! Table Configurations !!!! -->
<#list model.tables as c>
        <table tableName="${c.tableName}" domainObjectName="${c.code}" delimitIdentifiers="true" enableCountByExample="true"
               enableDeleteByExample="false" enableSelectByExample="true"
               enableUpdateByExample="true">
            <property name="useActualColumnNames" value="true"></property>
            <generatedKey column="Id" sqlStatement="JDBC"/>
        </table>
</#list>
    </context>
</generatorConfiguration>