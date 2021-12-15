# 该文件将列出所有接口以及使用

## 用户登录
- url:`/api/login`
- method:`post`
- requestParameter:

| 参数名称 | 是否必须 | 注释   |
| -------- | -------- | ------ |
| username | √        | 用户名 |
| password | √        | 密码   |

- example:

  ~~~json
  {
      "username":"admin",
      "password":"21232f297a57a5a743894a0e4a801fc3" //md5加密后
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
      "status":200
      // 登陆成功后会将token写在请求头中，以后每次请求需要携带token进行验证
  
  }
  ~~~

  