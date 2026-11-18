# mall-app

电商系统全栈实践项目，前后端同仓管理。

| 目录 | 说明 | 技术栈 |
| ---- | ---- | ------ |
| `mall-app-web` | 移动端商城前端（H5 / 小程序） | uni-app + Vue3 + TypeScript + Pinia + Vite |
| `mall-swarm` | 后端微服务 | Spring Cloud 2025 & Alibaba + Spring Boot 3.5 + Sa-Token + MyBatis + Nacos |

## 快速开始

### 后端

1. 准备 JDK 21、MySQL 8、Redis 7、Nacos 2（8848）；
2. 执行 `mall-swarm/document/sql/mall.sql` 初始化数据库；
3. 将 `mall-swarm/config/` 下的配置导入 Nacos 配置中心；
4. 编译并启动：

   ```bash
   cd mall-swarm
   mvn clean install -DskipTests
   java -jar mall-admin/target/mall-admin-1.0-SNAPSHOT.jar
   java -jar mall-portal/target/mall-portal-1.0-SNAPSHOT.jar
   ```

### 前端

```bash
cd mall-app-web
npm install
npm run dev:h5
```

启动后访问 http://localhost:5173 ，开发环境通过 Vite 代理 `/api` 转发到 `http://127.0.0.1:8085`（mall-portal）。

## 目录结构

```text
mall-app
├── mall-app-web -- 移动端商城前端
└── mall-swarm -- 后端微服务（mall-common / mall-gateway / mall-auth / mall-admin / mall-portal / mall-search / mall-monitor 等）
```

## 许可证

[Apache License 2.0](mall-app-web/LICENSE.txt)

Copyright (c) 2026 Melusine-ichnose
