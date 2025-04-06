---
title: 个人项目
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 个人项目

Base URLs:

# Authentication

# 测试接口

## GET test

GET /1/dubbo

> 返回示例

> 200 Response

```json
"string"
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|string|

# 处理支付回调的逻辑(bank-api)

## POST wxNotify

POST /payNotify/wxNotify

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|param|query|string| 是 |none|

> 返回示例

> 200 Response

```json
"string"
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|string|

# 消息配置接口(im)

## POST 获取 IM 配置信息的接口。

POST /live/api/im/getImConfig

<p>
此接口通过调用 IM 服务层的方法，获取 IM 系统的配置信息，如 Token 和服务器地址等。
</p>

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 银行相关接口（bank）

## POST 获取银行产品列表的接口。

POST /live/api/bank/products

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 支付产品接口，处理支付请求。

POST /live/api/bank/payProduct

<p>支付过程包括：</p>
<ol>
    <li>调用第三方支付接口（如支付宝、微信支付），生成支付中状态的订单。</li>
    <li>生成一个特定支付页二维码（用户输入账户密码进行支付），支付完成后第三方平台完成支付。</li>
    <li>第三方平台发送回调请求至业务方，业务方根据回调数据处理相应的业务。</li>
</ol>
<p>此接口支持接收不同平台的回调数据，并且可以根据业务标识（如业务 code）来决定回调到哪个业务服务。</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|productId|query|integer| 否 |产品id|
|paySource|query|integer| 否 |支付来源 (直播间，个人中心，聊天页面，第三方宣传页面，广告弹窗引导)|
|payChannel|query|integer| 否 |支付渠道|

#### 详细说明

**paySource**: 支付来源 (直播间，个人中心，聊天页面，第三方宣传页面，广告弹窗引导)
//@see org.qiyu.live.bank.constants.PaySourceEnum

**payChannel**: 支付渠道
//@see org.qiyu.live.bank.constants.//PayChannelEnum

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 礼物相关接口(gift)

## POST 获取礼物列表

POST /live/api/gift/listGift

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 发送礼物方法

POST /live/api/gift/send

具体实现在后边的章节会深入讲解

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|giftId|query|integer| 否 |礼物ID，标识礼物的唯一编号|
|roomId|query|integer| 否 |房间ID，标识礼物发送的房间|
|senderUserId|query|integer(int64)| 否 |发送礼物的用户ID|
|receiverId|query|integer(int64)| 否 |接收礼物的用户ID|
|type|query|integer| 否 |礼物类型，标识礼物的种类或分类|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 用户相关的 API 接口

## GET 根据用户ID获取用户信息

GET /live/api/userId

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|integer| 否 |用户ID|

> 返回示例

> 200 Response

```json
{
  "userId": 0,
  "nickName": "",
  "trueName": "",
  "avatar": "",
  "sex": 0,
  "workCity": 0,
  "bornCity": 0,
  "bornDate": "",
  "createTime": "",
  "updateTime": ""
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[UserDTO](#schemauserdto)|

## GET 更新用户信息

GET /live/api/update

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userid|query|integer| 否 |用户ID|

> 返回示例

> 200 Response

```json
true
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|boolean|

## GET 插入一条新的用户信息

GET /live/api/insertOne

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userid|query|integer| 否 |用户ID|

> 返回示例

> 200 Response

```json
true
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|boolean|

## GET 批量查询多个用户信息

GET /live/api/batchQuery

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userIDList|query|array[integer]| 是 |用户ID列表|

> 返回示例

> 200 Response

```json
{
  "0": {
    "userId": 0,
    "nickName": "",
    "trueName": "",
    "avatar": "",
    "sex": 0,
    "workCity": 0,
    "bornCity": 0,
    "bornDate": "",
    "createTime": "",
    "updateTime": ""
  }
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[MapUserDTO](#schemamapuserdto)|

# 首页相关的 接口(HomePage）

## POST 初始化首页信息

POST /live/api/home/initPage

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 购物相关接口（shopInfo）

## POST 获取直播间商品信息

POST /live/api/shop/listSkuInfo

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 获取商品详情

POST /live/api/shop/detail

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|skuId|query|integer(int64)| 否 |none|
|anchorId|query|integer(int64)| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 将商品加入购物车

POST /live/api/shop/addCar

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|skuId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 从购物车中移除商品

POST /live/api/shop/removeFromCar

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|skuId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 获取购物车信息

POST /live/api/shop/getCarInfo

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|skuId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 清空购物车

POST /live/api/shop/clearCar

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|skuId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 预下单操作

POST /live/api/shop/prepareOrder

进行库存锁定操作，防止商品被其他用户抢购。

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 获取主播的库存预锁定信息

POST /live/api/shop/prepareStock

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|anchorId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 立即支付操作

POST /live/api/shop/payNow

完成订单支付，扣减库存。

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|userId|query|integer(int64)| 否 |none|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 用户登录相关接口（userLogin）

## POST 发送登录验证码

POST /live/api/userLogin/sendLoginCode

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|phone|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 用户登录操作

POST /live/api/userLogin/login

验证验证码是否有效，完成初始化注册或老用户登录。

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|phone|query|string| 否 |none|
|code|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 直播间业务相关接口（LivingRoom）

## POST 获取直播间列表接口。

POST /live/api/living/list

<p>
普通用户通过此接口获取直播间列表，支持分页和类型过滤。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |房间类型（如：1-娱乐、2-教育、3-游戏等）|
|page|query|integer| 否 |当前页码（从1开始）|
|pageSize|query|integer| 否 |每页条数|
|roomId|query|integer| 否 |none|
|redPacketConfigCode|query|string| 否 |红包唯一标识|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 开启直播间接口。

POST /live/api/living/startingLiving

<p>
此接口用于开启一个新的直播间。通过类型标记来决定开启的直播间类型。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |前端返回的标记//开启的直播间类型|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 主播间PK对决接口。

POST /live/api/living/onlinePk

<p>
主播可以发起PK对决，通过此接口进行PK请求。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roomId|query|integer| 否 |房间ID，标识发起或参与PK的房间|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 关闭直播间接口。

POST /live/api/living/closeLiving

<p>
此接口用于关闭指定的直播间。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roomId|query|integer| 否 |房间号|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 获取主播相关配置信息接口（仅限主播）。

POST /live/api/living/anchorConfig

@ roomId 主播房间号

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|roomId|query|integer| 否 |none|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 准备红包接口。

POST /live/api/living/prepareRedPacket

<p>
主播通过此接口准备红包数据，以便发送红包。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |房间类型（如：1-娱乐、2-教育、3-游戏等）|
|page|query|integer| 否 |当前页码（从1开始）|
|pageSize|query|integer| 否 |每页条数|
|roomId|query|integer| 否 |none|
|redPacketConfigCode|query|string| 否 |红包唯一标识|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 发放红包接口。

POST /live/api/living/startRedPacket

<p>
主播通过此接口开始发放红包，广播到直播间用户。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |房间类型（如：1-娱乐、2-教育、3-游戏等）|
|page|query|integer| 否 |当前页码（从1开始）|
|pageSize|query|integer| 否 |每页条数|
|roomId|query|integer| 否 |none|
|redPacketConfigCode|query|string| 否 |红包唯一标识|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

## POST 获取红包接口。

POST /live/api/living/getRedPacket

<p>
用户通过此接口获取红包。
</p>

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|type|query|integer| 否 |房间类型（如：1-娱乐、2-教育、3-游戏等）|
|page|query|integer| 否 |当前页码（从1开始）|
|pageSize|query|integer| 否 |每页条数|
|roomId|query|integer| 否 |none|
|redPacketConfigCode|query|string| 否 |红包唯一标识|

> 返回示例

> 200 Response

```json
{
  "code": 0,
  "msg": "",
  "data": {}
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|[WebResponseVO](#schemawebresponsevo)|

# 数据模型

<h2 id="tocS_0">0</h2>

<a id="schema0"></a>
<a id="schema_0"></a>
<a id="tocS0"></a>
<a id="tocs0"></a>

```json
{
  "userId": 0,
  "nickName": "string",
  "trueName": "string",
  "avatar": "string",
  "sex": 0,
  "workCity": 0,
  "bornCity": 0,
  "bornDate": "string",
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||none|
|nickName|string|false|none||none|
|trueName|string|false|none||none|
|avatar|string|false|none||none|
|sex|integer|false|none||none|
|workCity|integer|false|none||none|
|bornCity|integer|false|none||none|
|bornDate|string|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|

<h2 id="tocS_Pet">Pet</h2>

<a id="schemapet"></a>
<a id="schema_Pet"></a>
<a id="tocSpet"></a>
<a id="tocspet"></a>

```json
{
  "id": 1,
  "category": {
    "id": 1,
    "name": "string"
  },
  "name": "doggie",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 1,
      "name": "string"
    }
  ],
  "status": "available"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|true|none||宠物ID编号|
|category|[Category](#schemacategory)|true|none||分组|
|name|string|true|none||名称|
|photoUrls|[string]|true|none||照片URL|
|tags|[[Tag](#schematag)]|true|none||标签|
|status|string|true|none||宠物销售状态|

#### 枚举值

|属性|值|
|---|---|
|status|available|
|status|pending|
|status|sold|

<h2 id="tocS_Category">Category</h2>

<a id="schemacategory"></a>
<a id="schema_Category"></a>
<a id="tocScategory"></a>
<a id="tocscategory"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||分组ID编号|
|name|string|false|none||分组名称|

<h2 id="tocS_Tag">Tag</h2>

<a id="schematag"></a>
<a id="schema_Tag"></a>
<a id="tocStag"></a>
<a id="tocstag"></a>

```json
{
  "id": 1,
  "name": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|id|integer(int64)|false|none||标签ID编号|
|name|string|false|none||标签名称|

<h2 id="tocS_WebResponseVO">WebResponseVO</h2>

<a id="schemawebresponsevo"></a>
<a id="schema_WebResponseVO"></a>
<a id="tocSwebresponsevo"></a>
<a id="tocswebresponsevo"></a>

```json
{
  "code": 0,
  "msg": "string",
  "data": {}
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|code|integer|false|none||状态码|
|msg|string|false|none||相关描述|
|data|object|false|none||数据|

<h2 id="tocS_UserDTO">UserDTO</h2>

<a id="schemauserdto"></a>
<a id="schema_UserDTO"></a>
<a id="tocSuserdto"></a>
<a id="tocsuserdto"></a>

```json
{
  "userId": 0,
  "nickName": "string",
  "trueName": "string",
  "avatar": "string",
  "sex": 0,
  "workCity": 0,
  "bornCity": 0,
  "bornDate": "string",
  "createTime": "string",
  "updateTime": "string"
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|userId|integer(int64)|false|none||none|
|nickName|string|false|none||none|
|trueName|string|false|none||none|
|avatar|string|false|none||none|
|sex|integer|false|none||none|
|workCity|integer|false|none||none|
|bornCity|integer|false|none||none|
|bornDate|string|false|none||none|
|createTime|string|false|none||none|
|updateTime|string|false|none||none|

<h2 id="tocS_MapUserDTO">MapUserDTO</h2>

<a id="schemamapuserdto"></a>
<a id="schema_MapUserDTO"></a>
<a id="tocSmapuserdto"></a>
<a id="tocsmapuserdto"></a>

```json
{
  "0": {
    "userId": 0,
    "nickName": "string",
    "trueName": "string",
    "avatar": "string",
    "sex": 0,
    "workCity": 0,
    "bornCity": 0,
    "bornDate": "string",
    "createTime": "string",
    "updateTime": "string"
  }
}

```

### 属性

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|0|[0](#schema0)|false|none||none|

