# Client API

---

## GET /

```json
{
    "code": 200,
    "msg": "OK",
    "time": 1739840647668,
    "uuid": "e5138e51-e9b0-47d3-910c-a96f546c9c2e",
    "data": {
        "queryinfo": true,
        "server": "QueryInfo/1.2.0",
        "version": {
            "name": "QueryInfo",
            "version": "1.2.0"
        }
    }
}
```

通过检查`data.queryinfo`来识别此服务是否由QueryInfo提供。


## GET /api/client/info

```json
{
  "code": 200,
  "msg": "OK",
  "time": 1739841262641,
  "uuid": "ed30a42f-c9ce-46e5-9fe2-f89916e5d8b4",
  "data": {
    "fps": 59,
    "isInGame": false,
    "isPaused": false,
    "isDemo": false,
    "isFinishedLoading": true,
    "isRealmsEnabled": true,
    "isInSingleplayer": false,
    "isIntegratedServerRunning": false,
    "isConnectedToLocalServer": false,
    "isMultiplayerEnabled": true,
    "player": {},
    "world": {}
  }
}
```

如果玩家未进入游戏，则`data.player`和`data.world`为空，如上所示。

```json
{
    "code": 200,
    "msg": "OK",
    "time": 1739841319190,
    "uuid": "6e33bb2c-1396-4783-b3ae-67516a57cc4d",
    "data": {
        "fps": 118,
        "isInGame": true,
        "isPaused": false,
        "isDemo": false,
        "isFinishedLoading": true,
        "isRealmsEnabled": true,
        "isInSingleplayer": true,
        "isIntegratedServerRunning": true,
        "isConnectedToLocalServer": true,
        "isMultiplayerEnabled": true,
        "player": {
            "teamColorValue": 16777215,
            "luck": 0.0,
            "displayName": "Player846",
            "headYaw": -6.7499104,
            "blockInteractionRange": 5.0,
            "blockX": -8,
            "air": 300,
            "blockY": -59,
            "movementSpeed": 0.1,
            "uuid": "8a5928f7-beb7-37f5-80fc-dd4e68365b90",
            "blockZ": 20,
            "score": 0,
            "seepTimer": 0,
            "pos": [
                -7.173931330081825,
                -58.35980987524104,
                20.595682363852834
            ],
            "absorptionAmount": 0.0,
            "armorVisibility": 0.0,
            "health": 20.0,
            "bodyYaw": -6.7499104,
            "attackCooldownProgressPerTick": 5.0,
            "defaultPortalCooldown": 10,
            "hunger": {
                "prevFoodLevel": 20,
                "saturationLevel": 5.0,
                "exhaustion": 0.0,
                "foodLevel": 20
            },
            "damageTiltYaw": 0.0,
            "armor": 0,
            "enchantmentTableSeed": 0,
            "name": "Player846",
            "chunkPosX": -1,
            "chunkPosZ": 1
        },
        "world": {
            "moonSize": 1.0,
            "time": 3233,
            "type": "minecraft:overworld",
            "moonPhase": 0,
            "dimension": {
                "hasCeiling": false,
                "natural": true,
                "logicalHeight": 384,
                "bedWorks": true,
                "hasRaids": true,
                "ambientLight": 0.0,
                "fixedTime": "OptionalLong.empty",
                "minY": -64,
                "hasFixedTime": false,
                "respawnAnchorWorks": false,
                "monsterSpawnBlockLightLimit": 0,
                "coordinateScale": 1.0,
                "hasSkyLight": true,
                "ultrawarm": false,
                "height": 384,
                "piglinSafe": false
            },
            "loadedChunkCount": 609
        }
    }
}
```

`data.player`和`data.world`包含玩家和世界的信息。

~~有些数据字段是直接从对象中获取的，我也没有研究具体是什么意思（~~

## GET /api/client/entities

```json
{
  "code": 200,
  "msg": "OK",
  "time": 1739841508230,
  "uuid": "9609aab4-3535-4c9b-9a00-44a7a87a76fd",
  "data": {
    "entities": [
      {
        "name": "Player846",
        "type": "entity.minecraft.player",
        "uuid": "8a5928f7-beb7-37f5-80fc-dd4e68365b90",
        "x": -7.17393133008183,
        "y": -58.359809875241,
        "z": 20.5956823638528,
        "pitch": 29.4000263214111,
        "yaw": -6.74991035461426,
        "blockX": -8,
        "blockY": -59,
        "blockZ": 20,
        "age": 3874
      },
      {
        "name": "鸡",
        "type": "entity.minecraft.chicken",
        "uuid": "52d26150-0698-40c9-90ac-8035a42bd884",
        "x": 3.22607421875,
        "y": -60,
        "z": 65.165771484375,
        "pitch": 0,
        "yaw": -39.375,
        "blockX": 3,
        "blockY": -60,
        "blockZ": 65,
        "age": 3874
      },
      {
        "name": "史莱姆",
        "type": "entity.minecraft.slime",
        "uuid": "6294fa66-813c-4e91-8dc7-64659457cfd2",
        "x": -17.4167774108712,
        "y": -60,
        "z": 19.6519651817505,
        "pitch": 0,
        "yaw": 77.34375,
        "blockX": -18,
        "blockY": -60,
        "blockZ": 19,
        "age": 3874
      },
      {
        "name": "牛",
        "type": "entity.minecraft.cow",
        "uuid": "acd9ead9-fbea-4a46-ac15-8abaa5fcafb4",
        "x": 26.2322337263461,
        "y": -60,
        "z": 10.8376686536039,
        "pitch": 0,
        "yaw": -142.03125,
        "blockX": 26,
        "blockY": -60,
        "blockZ": 10,
        "age": 3874
      }
      // ...
    ],
    "isInGame": true
  }
}
```

如上所示，返回客户端所有实体的信息。

## GET /api/client/show_msg

| 参数名     | 参数类型    | 参数说明        | 备注                           |
|---------|---------|-------------|------------------------------|
| msg     | string  | 需要显示给客户端的消息 | 两个连续的"&"（即"&&"）会被替换为分节符（"§"） | 
| overlay | boolean |             | 默认为false                     |

> 注：**请注意为&字符进行URL编码（%26）**

**请求成功示例**

```json
{
    "code": 200,
    "msg": "OK",
    "time": 1740586152734,
    "uuid": "f64ff8ea-b14f-402e-bd03-e681c3e02845"
}
```

**缺少参数示例**

```json
{
    "code": 400,
    "msg": "Bad Request: \u0027msg\u0027 parameter is required",
    "time": 1740586309706, 
    "uuid": "d152a799-cd6f-4f67-8c68-89bd8814e5e8"
}
```



---

