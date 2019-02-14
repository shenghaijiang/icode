server:
  port: 0
logging:
  level: debug
# MyBatis
mybatis:
    mapperLocations: classpath:mapper/*.xml
    configLocation: classpath:mybatis-config.xml
    type-aliases-package: ${model.packageName}.entity
spring:
  #设置文件大小
  http:
    multipart:
      max-file-size: 20Mb
      max-request-size: 100Mb
  #取消模板文件缓存
  thymeleaf:
    cache: false
  application:
    name: ${model.code}-api-service
  profiles:
    active: test
eureka:
  instance:
    prefer-ip-address: true
    instance-id: ${r'${spring.cloud.client.ipAddress}'}:${r'${server.port}'}
    hostname: ${r'${spring.cloud.client.ipAddress}'}
    # 服务失效时间
    lease-expiration-duration-in-seconds: 90
    # 服务续约任务的调用时间间隔
    lease-renewal-interval-in-seconds: 30