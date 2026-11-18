# mall-swarm

基于 Spring Cloud Alibaba 的微服务电商系统，采用 Spring Cloud 2025 & Alibaba、Spring Boot 3.5、Sa-Token、MyBatis、Elasticsearch 等核心技术，配套前端项目 [mall-app-web](https://github.com/Melusine-ichnose/mall-app-web)。

## 项目简介

`mall-swarm` 是一套电商业务微服务实践项目，集成了注册中心、配置中心、监控中心、网关、统一认证等系统能力。

## 组织结构

``` lua
mall-swarm
├── mall-common -- 工具类及通用代码模块
├── mall-mbg -- MyBatisGenerator生成的数据库操作代码模块
├── mall-auth -- 统一认证中心
├── mall-gateway -- 基于 Spring Cloud Gateway 的微服务 API 网关
├── mall-monitor -- 基于 Spring Boot Admin 的微服务监控中心
├── mall-admin -- 后台管理系统服务
├── mall-search -- 基于 Elasticsearch 的商品搜索服务
├── mall-portal -- 移动端商城服务
├── mall-demo -- 微服务远程调用测试服务
└── config -- 配置中心存储的配置
```

## 技术选型

### 后端技术

| 技术                 | 说明                 | 官网                                                 |
| -------------------- | -------------------- | ---------------------------------------------------- |
| Spring Cloud         | 微服务框架           | https://spring.io/projects/spring-cloud              |
| Spring Cloud Alibaba | 微服务框架           | https://github.com/alibaba/spring-cloud-alibaba      |
| Spring Boot          | 容器+MVC框架         | https://spring.io/projects/spring-boot               |
| Sa-Token             | 认证和授权框架       | https://github.com/dromara/Sa-Token                  |
| MyBatis              | ORM框架              | http://www.mybatis.org/mybatis-3/zh/index.html       |
| MyBatisGenerator     | 数据层代码生成       | http://www.mybatis.org/generator/index.html          |
| PageHelper           | MyBatis物理分页插件  | http://git.oschina.net/free/Mybatis_PageHelper        |
| Knife4j              | 文档生产工具         | https://github.com/xiaoymin/swagger-bootstrap-ui     |
| Elasticsearch        | 搜索引擎             | https://github.com/elastic/elasticsearch             |
| RabbitMq             | 消息队列             | https://www.rabbitmq.com/                            |
| Redis                | 分布式缓存           | https://redis.io/                                    |
| MongoDb              | NoSql数据库          | https://www.mongodb.com/                             |
| Docker               | 应用容器引擎         | https://www.docker.com/                              |
| Druid                | 数据库连接池         | https://github.com/alibaba/druid                     |
| Lombok               | 简化对象封装工具     | https://github.com/rzwitserloot/lombok               |

### 前端技术

移动端商城前端基于 `uni-app + Vue3`，见 [mall-app-web](https://github.com/Melusine-ichnose/mall-app-web)。

## 本地环境

| 组件   | 版本   | 说明                         |
| ------ | ------ | ---------------------------- |
| JDK    | 21     | Spring Boot 3.5 需要 JDK 17+ |
| MySQL  | 8.x    | 业务数据库，库名 `mall`      |
| Redis  | 7.x    | 缓存 / Session               |
| Nacos  | 2.x    | 注册中心 + 配置中心，8848    |

## 启动步骤

1. 执行 `document/sql/mall.sql` 初始化数据库；
2. 启动 MySQL、Redis，确认 `mall` 库可连接；
3. 启动 Nacos（单机模式，端口 8848），并将 `config/` 目录下的配置导入 Nacos 配置中心；
4. 根目录执行 `mvn clean install -DskipTests` 编译；
5. 按需启动各模块，`mall-admin` 依赖 `mall-auth`、`mall-gateway`、`mall-portal` 需要注册到 Nacos：

   ```bash
   java -jar mall-admin/target/mall-admin-1.0-SNAPSHOT.jar
   java -jar mall-portal/target/mall-portal-1.0-SNAPSHOT.jar
   ```

## 接口地址

| 服务        | 地址                                  |
| ----------- | ------------------------------------- |
| 后台管理    | http://localhost:8080                 |
| 移动端商城  | http://localhost:8085                 |
| Nacos 控制台 | http://localhost:8848/nacos           |
| 接口文档    | http://localhost:8080/swagger-ui/     |

## 许可证

[Apache License 2.0](LICENSE)

Copyright (c) 2026 Melusine-ichnose
