# 该文件将列出所有接口以及使用

## user模块
### 用户登录
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
      "password":"21232f297a57a5a743894a0e4a801fc3",
      "//":"password是前端MD5加密后"
  }
  ~~~

- responseParameter:
  
  | 参数名称 | 注释     |
  | -------- | -------- |
  | canLogin | 是否成功 |
  | status   | 状态码   |

- example:

  ~~~json
  {
      "canLogin":true,
      "status":200,
      "//": "登陆成功后会将token写在请求头中，以后每次请求需要携带token进行验证"
  
  }
  ~~~

### 添加用户
- url:`/user/api/adduser`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| username | √        | 用户名 |
| password | √        | 密码   |
| auth     | √        | 权限   |
| base64   | √        | 头像base64|
- example:

  ~~~json
  {
      "username":"user",
      "password":"21232f297a57a5a743894a0e4a801fc3",
      "auth": "user",
      "base64": "去头的base64",
      "//":"password是前端MD5加密后",
      "/*": "base64是去除{data:image/png;base64,}"
  }
  ~~~

- responseParameter:

  | 参数名称 | 注释     |
    | -------- | -------- |
  | status   | 状态码   |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~

### 修改密码
- url:`/user/api/changepassword`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| username | √        | 用户名 |
| password | √        | 密码   |
- example:

  ~~~json
  {
      "username":"user",
      "password":"21232f297a57a5a743894a0e4a801fc3",
      "//":"password是前端MD5加密后"
  }
  ~~~

- responseParameter:

  | 参数名称 | 注释     |
  | -------- | -------- |
  | status   | 状态码   |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~
  
### 修改头像
- url:`/user/api/setHeadPortrait`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| username | √        | 用户名 |
| base64 | √        | base64   |
- example:

  ~~~json
  {
      "username":"user",
      "base64": "去头的base64"
  }
  ~~~

- responseParameter:

  | 参数名称 | 注释     |
    | -------- | -------- |
  | status   | 状态码   |

- example:

  ~~~json
  {
      "status":200
  }
  ~~~
  
### 获取头像
- url:`/user/api/getUserHeadPortrait`
- method:`get`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| username | √        | 用户名 |
- example:

  ~~~json
  {
      "username":"user"
  }
  ~~~

- responseParameter:

  | 参数名称 | 注释     |
  | -------- | -------- |
  | data  | base64   |  
  | status   | 状态码   |

- example:

  ~~~json
  {
      "data": "去头的base64",
      "status":200
  }
  ~~~
  
### 获取在线人数
- url:`/user/api/getOnlineUser`
- method:`get`

- responseParameter:

  | 参数名称 | 注释     |
    | -------- | -------- |
  | data  | 在线用户   |  
  | status   | 状态码   |

- example:

  ~~~json
  {
      "data": "countUser",
      "status":200
  }
  ~~~

## fileTransfer模块

### 文件上传
- url:`/file/api/fileUpload`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| file | √        | 文件 |
- example:

  ~~~json
  {
      "file":"file"
  }
  ~~~

- responseParameter:

  | 参数名称 | 注释     |
  | -------- | -------- |
  | data  | 文件md5   |  
  | status   | 状态码   |

- example:

  ~~~json
  {
      "data": "md5",
      "status":200
  }
  ~~~

### 获取文件名字
- url:`/file/api/getName`
- method:`get`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| md5 | √        | 文件MD5值|

- responseParameter:

  | 参数名称 | 注释     |
  | -------- | -------- |
  | data  | 文件名字   |  
  | status   | 状态码   |

- example:

  ~~~json
  {
      "data": "文件名字",
      "status":200
  }
  ~~~

### 下载文件
- url:`/file/api/downloadFile`
- method:`get`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| md5 | √        | 文件MD5值|
| filename | √        | 文件名字|
| token | √        | token|

- responseParameter:

  | 参数名称 | 注释     |
  | -------- | -------- |
  |ResponseEntity|文件流|  

### 获取全部文件名字
- url:`/file/api/getAllFileNames`
- method:`post`
  
- responseParameter:

  | 参数名称 | 注释     |
    | -------- | -------- |
  | data  | 全部文件名字   |  
  | status   | 状态码   |

- example:

  ~~~json
  {
      "data": "全部文件名字",
      "status":200
  }
  ~~~