# QueryInfo

### Languages

- [English](../README.MD)
- Chinese Simplified

---

## 关于

这是一个允许其他客户端通过HTTP协议获取正在运行的Minecraft客户端信息的模组。

## 文档

[Client API (仅简体中文)](apis/ClientAPI_ZHCN.MD)

> TODO: 英文版

## TODO

### 优先
- [ ] 替换错误页面为JSON（如404）
- [x] 允许执行操作（不仅仅是信息获取）
- [ ] 添加服务器端支持
### 其他
- [ ] Websocket 支持
- [ ] Server-Sent-Events 支持
- [ ] 身份验证支持
- [ ] ...

## Mixin

如果要修改此模组并添加自定义的路由，请Mixin到
`xyz.yldk.mcmod.queryinfo.client.WebServerManager.setupRoutes()`
的底部。

### 推荐的API路由规范：
- /api/client/* 用于客户端
- /api/server/* 用于服务器

## APIs

- [x] /
- [x] /api/client/info
- [x] /api/client/entities
- [x] /api/client/show_msg
- [x] /api/client/send_msg
- [x] /api/client/send_command
- [ ] /api/client/recv_msgs (SSE)
- [ ] /api/server/info
