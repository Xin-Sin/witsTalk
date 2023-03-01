# this file show all interface

## user module
### user login
- url:`/user/api/login`
- method:`post`
- requestParameter:

| 参数名称     | 是否必须 | 注释  |
|----------|------|-----|
| username | √    | 用户名 |
| password | √    | 密码  |

- example:

  ~~~json
  {
      "username":"admin",
      "password":"21232f297a57a5a743894a0e4a801fc3"
  }
  ~~~

- responseParameter:

| 参数名称     | 注释   |
|----------|------|
| canLogin | 是否成功 |
| status   | 状态码  |

- example:

  ~~~json
  {
      "canLogin":true,
      "status":200,
      "//": "登陆成功后会将token写在请求头中，以后每次请求需要携带token进行验证"
  
  }
  ~~~
- response headers

| 参数名称  | 注释    |
|-------|-------|
 | token | token |
- example:
  ~~~json
    {
    "Access-Token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjYiLCJleHAiOjE2NzYwOTk0NzYsImFjY291bnQiOiIxMTk5NDcyNDMiLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.3IUC63Y01sBalf87LfPttt6YDSqJpiZBh4vVb8nuE94lrbFTXLV3AyQA1vNqRZVZ-R2cDM79SpvSAnGVwdWuzw"
  }
  ~~~

### register user
- url:`/user/api/adduser`
- method:`post`
- requestParameter:

| 参数名称     | 是否必须 | 注释       |
|----------|------|----------|
| username | √    | 用户名      |
| password | √    | 密码       |
| auth     | √    | 权限       |
| base64   | √    | 头像base64 |
- example:

  ~~~json
  {
      "username":"example",
      "password":"21232f297a57a5a743894a0e4a801fc3",
      "auth": "user",
      "base64": "base64是去除 data:image/png;base64,"
  }
  ~~~

- responseParameter:

| 参数名称   | 注释  |
|--------|-----|
| status | 状态码 |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~
`注: 以下接口访问都需要使用token访问`
- request headers
  
| 参数名称  | 注释                                                                                                                                                                                                                          |
|-------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| token | eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjYiLCJleHAiOjE2NzYwOTk0NzYsImFjY291bnQiOiIxMTk5NDcyNDMiLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.3IUC63Y01sBalf87LfPttt6YDSqJpiZBh4vVb8nuE94lrbFTXLV3AyQA1vNqRZVZ-R2cDM79SpvSAnGVwdWuzw |

- example:
  ~~~json
    {
      "token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjYiLCJleHAiOjE2NzYwOTk0NzYsImFjY291bnQiOiIxMTk5NDcyNDMiLCJ1c2VybmFtZSI6InpoYW5nc2FuIn0.3IUC63Y01sBalf87LfPttt6YDSqJpiZBh4vVb8nuE94lrbFTXLV3AyQA1vNqRZVZ-R2cDM79SpvSAnGVwdWuzw"
    }
  ~~~

### modify password
- url:`/user/api/changepassword`
- method:`post`
- requestParameter:

| 参数名称     | 是否必须 | 注释  |
|----------|------|-----|
| username | √    | 用户名 |
| password | √    | 密码  |
- example:

  ~~~json
  {
      "username":"user",
      "password":"21232f297a57a5a743894a0e4a801fc3"
  }
  ~~~

- responseParameter:

| 参数名称   | 注释  |
|--------|-----|
| status | 状态码 |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~
  
### modify portrait
- url:`/user/api/setHeadPortrait`
- method:`post`
- requestParameter:

| 参数名称     | 是否必须 | 注释     |
|----------|------|--------|
| username | √    | 用户名    |
| base64   | √    | base64 |
- example:

  ~~~json
  {
      "username":"user",
      "base64": "base64是去除 data:image/png;base64,"
  }
  ~~~

- responseParameter:

| 参数名称   | 注释  |
|--------|-----|
| status | 状态码 |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~
  
### get porttait
- url:`/user/api/getUserHeadPortrait`
- method:`get`
- requestParameter:

| 参数名称     | 是否必须 | 注释  |
|----------|------|-----|
| username | √    | 用户名 |
- example:

  ~~~json
  {
      "username":"user"
  }
  ~~~

- responseParameter:

| 参数名称   | 注释     |
|--------|--------|
| data   | base64 |  
| status | 状态码    |

- example:

  ~~~json
  {
      "data": "base64是去除 data:image/png;base64,",
      "status":200
  }
  ~~~
  
### get online user
- url:`/user/api/getOnlineUser`
- method:`get`

- responseParameter:

| 参数名称   | 注释   |
|--------|------|
| data   | 在线用户 |  
| status | 状态码  |

- example:

  ~~~json
  {
      "data": "countUser",
      "status":200
  }
  ~~~

## fileTransfer module

### file upload
- url:`/file/api/fileUpload`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释  |
|------|------|-----|
| file | √    | 文件  |
- example:

  ~~~json
  {
      "file":"briny"
  }
  ~~~

- responseParameter:

| 参数名称   | 注释    |
|--------|-------|
| data   | 文件md5 |  
| status | 状态码   |

- example:

  ~~~json
  {
      "data": "md5",
      "status":200
  }
  ~~~

### get file name
- url:`/file/api/getName`
- method:`get`
- requestParameter:

| 参数名称 | 是否必须 | 注释     |
|------|------|--------|
| md5  | √    | 文件MD5值 |

- responseParameter:

| 参数名称   | 注释   |
|--------|------|
| data   | 文件名字 |  
| status | 状态码  |

- example:

  ~~~json
  {
      "data": "file name",
      "status":200
  }
  ~~~

### download file
- url:`/file/api/downloadFile`
- method:`get`
- requestParameter:

| 参数名称     | 是否必须 | 注释     |
|----------|------|--------|
| md5      | √    | 文件MD5值 |
| filename | √    | 文件名字   |
| token    | √    | token  |

- responseParameter:

| 参数名称           | 注释          |
|----------------|-------------|
| ResponseEntity | file stream |  

### get all file names
- url:`/file/api/getAllFileNames`
- method:`post`
  
- responseParameter:

| 参数名称   | 注释     |
|--------|--------|
| data   | 全部文件名字 |  
| status | 状态码    |

- example:

  ~~~json
  {
      "data": "all file names",
      "status":200
  }
  ~~~
  
`以下为websocket接口`

## chat module

- address
  `ws://address:8005/chat`
    - login `在用户第一次连接时进行登陆验签
            ,使用token作为参数进行验证`
    - 