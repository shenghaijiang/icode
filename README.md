# ICode代码生成平台
## 演示站点
- http://139.196.125.197/icode
- 用户名:admin 密码:123456
- 清不要删除菜单,数据定时清理
<!--more-->
## 实现功能
- 项目代码自动生成
- 提供一套前后端的代码生成模板(Java后端，Vue前端)

## 后端部署
- 创建数据库git-icode
- 导入 icode.sql 文件
- idea导入项目
- 修改 application.yml 对应的数据库连接,用户名,密码
- 运行项目|java -jar icode-api.jar --eureka.client.serviceUrl.defaultZone=http://{eureke地址[如果不使用spring cloud方式运行,忽略]}:8761/eureka

## 前端部署
- 确保本地安装node.js v4+
- $ cd my-project
- $ npm install babel-plugin-syntax-jsx babel-plugin-transform-vue-jsx babel-helper-vue-jsx-merge-props babel-preset-es2015 --save-dev
- $ npm --registry https://registry.npm.taobao.org install
- $ npm run dev
- $ 修改src/api/api.js中let base=实际后端java接口地址

## 技术选型
- 模板引擎：FreeMarker
- 核心框架：Spring cloud Dalston.SR3 ; Spring boot 1.5.9 
- 安全框架：Shiro
- 持久层框架：MyBatis
- 数据库：mysql
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.6、Log4j
- 页面交互：Vue2.x

## 项目截图
- 项目界面
![image](https://shenghaijiang-git.oss-cn-shanghai.aliyuncs.com/icode/1.png)
- 生成前端代码结构
![image](https://shenghaijiang-git.oss-cn-shanghai.aliyuncs.com/icode/2.png)
- 生成后端代码结构
![image](https://shenghaijiang-git.oss-cn-shanghai.aliyuncs.com/icode/3.png)

## 后期规划
- 授权查询数据库,大并发下存在性能问题,后期授权整合缓存系统

## 交流、反馈
- 网站：http://www.xtits.cn
- 官方QQ群：**372848506**

![image](http://shenghaijiang-git.oss-cn-shanghai.aliyuncs.com/common/372848506.png)
