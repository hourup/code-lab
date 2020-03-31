# TDD In SpringBoot

TDD 的开发节奏是: **红灯 - 绿灯 - 重构**

在 SpringBoot 开发中,以传统的 MVC 三层架构来看,我们需要 在 Controller, Service, Repository 层分别运用 TDD

需要注意的是, 在对每一层进行 TDD 后, 还需要做一次 集成测试(Integration Testing).

## 1.Controller Testing
> Mock 掉所有 Service 层依赖

## 2.Service Testing
> Mock 掉所有 数据库层依赖
>
## 3.Repository Testing (可选)
> 数据库层可以不添加单元测试

## 4.Integration Testing
> 集成测试: 在 Controller 层 Mock 掉 数据库层