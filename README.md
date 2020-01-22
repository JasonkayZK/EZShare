## EZShare

![https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square](https://img.shields.io/badge/license-Apache%202.0-blue.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/springboot-2.2.4-yellow.svg?longCache=true&style=flat-square](https://img.shields.io/badge/springboot-2.2.4-yellow.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/shiro-1.4.2-orange.svg?longCache=true&style=flat-square](https://img.shields.io/badge/shiro-1.4.2-orange.svg?longCache=true&style=flat-square)
![https://img.shields.io/badge/vue-2.9.6-brightgreen.svg?longCache=true&style=flat-square](https://img.shields.io/badge/vue-2.9.6-brightgreen.svg?longCache=true&style=flat-square) 

项目灵感来自于[FEBS-Vue](https://github.com/wuyouzhuguli/FEBS-Vue)

EZShare是一款简洁高效的前后端分离的网页端应用, 如同他的名字EZ一样, 旨在互联网上建立"简单", "自由"的内容分享平台

项目使用Spring Boot，Shiro等技术构建

前端使用Vue全家桶，组件库采用[Ant-Design-Vue](https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn/)

### 项目文档

项目开发中, 文档暂无…

### 功能模块

```
├─文件管理
│  ├─文件分类
│  ├─文件上传
│  ├─文件列表
│  ├─文件上传/下载历史
│  └─文件管理
├─用户管理
│  ├─用户注册
│  ├─用户注销
│  ├─用户信息修改
├─角色管理
├─菜单管理
├─字典管理
│─资源搜索
│─需求留言板
├─系统监控
│  ├─在线用户
│  ├─系统日志
│  ├─Redis监控
│  ├─请求追踪
│  └─系统信息
│     ├─JVM信息
│     ├─服务器信息
│     └─Tomcat信息
│─任务调度
│  ├─定时任务
│  └─调度日志
│─网络资源
│  ├─天气查询
│  ├─影视资讯
│  │  ├─即将上映
│  │  └─正在热映
│  └─每日一文
└─其他模块
   └─导入导出
```

### 技术选型

#### 前端

- [Vue 2.9.6](https://cn.vuejs.org/),[Vuex](https://vuex.vuejs.org/zh/),[Vue Router](https://router.vuejs.org/zh/)
- [Axios](https://github.com/axios/axios)
- [vue-apexcharts](https://apexcharts.com/vue-chart-demos/line-charts/)
- [ant-design-vue](https://vuecomponent.github.io/ant-design-vue/docs/vue/introduce-cn/)
- [webpack](https://www.webpackjs.com/),[yarn](https://yarnpkg.com/zh-Hans/)

#### 后端

- [Spring Boot 2.2.4](http://spring.io/projects/spring-boot/)
- [Mybatis-Plus](https://mp.baomidou.com/guide/)
- [MySQL 8.0.18](https://dev.mysql.com/downloads/mysql/5.7.html#downloads),[Hikari](https://brettwooldridge.github.io/HikariCP/),[Redis](https://redis.io/)
- [Shiro](http://shiro.apache.org/),[JWT](https://jwt.io/)

### 项目特色

1. 支持多种文件分享
2. 支持各种格式文件导出
3. 前端页面布局多样化，主题多样化
4. 支持多数据源，代码生成
5. 根据不同用户权限动态构建路由
6. RESTFul风格接口
7. 前后端请求参数校验
8. 自定义Vue权限指令来控制DOM元素渲染与否:

| 指令               | 含义                                           | 示例                                                         |
| ------------------ | ---------------------------------------------- | ------------------------------------------------------------ |
| v-hasPermission    | 当用户拥有列出的权限的时候，渲染该元素         | `<template v-hasPermission="'user:add','user:update'"><span>hello</span></template>` |
| v-hasAnyPermission | 当用户拥有列出的任意一项权限的时候，渲染该元素 | `<template v-hasAnyPermission="'user:add','user:update'"><span>hello</span></template>` |
| v-hasRole          | 当用户拥有列出的角色的时候，渲染该元素         | `<template v-hasRole="'admin','register'"><span>hello</span></template>` |
| v-hasAnyRole       | 当用户拥有列出的任意一个角色的时候，渲染该元素 | `<template v-hasAnyRole="'admin','register'"><span>hello</span></template>` |

