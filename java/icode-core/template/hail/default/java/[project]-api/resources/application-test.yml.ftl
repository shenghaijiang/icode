server:
  port: 8018
  context-path: /${model.code}-api
spring:
  datasource:
    url: jdbc:mysql://${model.dbHost}/${model.dbName}?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: ${model.dbUser}
    password: '${model.dbPassword}'
    driver-class-name: com.mysql.jdbc.Driver
  redis:
    host: 192.168.2.111
    port: 6379
    database: 0
    pool:
      max-active: 8
      max-wait: -1
      max-idle: 500
      min-idle: 0
    timeout: 0
eureka:
  client:
    serviceUrl:
      defaultZone: http://192.168.2.103:8761/eureka/
#xtp:
APP_TOKEN:  CJzdWIiOiJ7XCJ1c2VySDkwMTUeyJhbGciOiJI
TTL_MILLIS:  864000000