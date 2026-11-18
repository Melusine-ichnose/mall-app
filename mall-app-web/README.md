# mall-app-web

电商系统移动端前端，基于 `uni-app + Vue3` 实现，后端对接微服务项目 [mall-swarm](https://github.com/Melusine-ichnose/mall-swarm)。

## 项目介绍

本项目包含首页门户、商品搜索、商品展示、品牌专区、购物车、订单流程、支付、会员中心等功能模块。首页已改造为蓝白卡片风格的博客式布局。

## 技术选型

| 技术       | 说明             | 官网                           |
| ---------- | ---------------- | ------------------------------ |
| Vue 3      | 核心前端框架     | https://vuejs.org              |
| Pinia      | 全局状态管理框架 | https://pinia.vuejs.org        |
| TypeScript | 类型系统         | https://www.typescriptlang.org |
| uni-app    | 移动端前端框架   | https://uniapp.dcloud.io       |
| Vite       | 构建工具         | https://vitejs.dev             |
| Sass       | CSS 预处理器     | https://sass-lang.com          |
| ESLint     | 代码质量检查     | https://eslint.org             |
| Prettier   | 代码格式化       | https://prettier.io            |

## 项目结构

```lua
src -- 源码目录
├── apis -- API 请求方法（按业务模块划分）
├── components -- 通用组件封装
├── composables -- 组合式函数（自定义 hooks）
├── pages -- 前端页面
│   ├── address -- 地址管理页
│   ├── brand -- 商品品牌页
│   ├── cart -- 购物车页
│   ├── category -- 商品分类页
│   ├── coupon -- 优惠券页
│   ├── index -- 首页
│   ├── money -- 支付页
│   ├── notice -- 通知页
│   ├── order -- 订单页
│   ├── product -- 商品页
│   ├── public -- 登录页
│   ├── set -- 设置页
│   └── user -- 会员页
├── static -- 图片等静态资源
├── stores -- Pinia 状态管理
├── styles -- 全局样式
├── types -- TypeScript 类型定义
└── utils -- 工具类
```

## 本地运行

本项目使用 `VSCode` 开发，下载地址：https://code.visualstudio.com/

1. 前后端分离项目，本地调试需要先启动后端服务，参考 [mall-swarm](https://github.com/Melusine-ichnose/mall-swarm)；
2. 前端接口全部由 `mall-portal` 模块提供（默认端口 8085），请确保该模块已启动；
3. 开发环境接口地址在 `.env.development` 中通过 `VITE_API_BASE_URL` 配置，默认走 Vite 代理 `/api` 转发到 `http://127.0.0.1:8085`，无需额外处理跨域；
4. 安装依赖：

   ```bash
   npm install
   ```

5. H5 端运行：

   ```bash
   npm run dev:h5
   ```

   启动后访问 http://localhost:5173 ，建议将浏览器切换为手机模式查看；
6. 微信小程序端运行：`npm run dev:mp-weixin`，然后在微信开发者工具中导入 `dist/dev/mp-weixin`。

## 许可证

[Apache License 2.0](LICENSE.txt)

Copyright (c) 2026 Melusine-ichnose
