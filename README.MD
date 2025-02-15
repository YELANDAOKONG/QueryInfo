# QueryInfo

### Languages

- English
- [Chinese Simplified](docs/README_ZHCN.MD)

---

## About

This is a mod that allows
other clients to retrieve information from a running Minecraft client via HTTP protocol.

## Document

> TODO...

## TODO

### Preferred
- [ ] Replace error pages with JSON (e.g., 404)
- [ ] Allow execution of operations (not limited to information retrieval)
- [ ] Add server side support
### Others
- [ ] Websocket support
- [ ] Server-Sent-Events support
- [ ] Authorization support
- [ ] ...

## Mixin

If you need to modify this mod and add custom routes, mixin into the bottom of  
`xyz.yldk.mcmod.queryinfo.client.WebServerManager.setupRoutes()`.

### Recommended API route specifications:
- /api/client/* for client-side
- /api/server/* for server-side

## APIs

- [x] /
- [x] /api/client/info
- [ ] /api/server/info
