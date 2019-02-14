spring:
  datasource:
    url: jdbc:mysql://${model.dbHost}/${model.dbName}?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: ${model.dbUser}
    password: '${model.dbPassword}'
    driver-class-name: com.mysql.jdbc.Driver
  redis:
    host: 10.10.3.12
    port: 6379
    database: 1
    pool:
      max-active: 8
      max-wait: -1
      max-idle: 500
      min-idle: 0
APP_TOKEN: CJzdWIiOiJ7XCJ1c2VySDkwMTUeyJhbGciOiJI
TTL_MILLIS: 864000000