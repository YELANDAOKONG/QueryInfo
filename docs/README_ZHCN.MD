# QueryInfo

### Languages

- English
- [Chinese Simplified](docs/README_ZHCN.MD)

---

## 关于

> TODO...

## 文档

> TODO...

## TODO

### 优先
- [ ] 替换错误页面为JSON（如404）
- [ ] 允许执行操作（不仅仅是信息获取）
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
- [ ] /api/server/info
