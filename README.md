<div align="center"><img alt="Logo" height="128" src="web/src/assets/logo.png" width="128"/></div>

<h2 align="center">🌟项目名称: WitsTalk</h2>
<h5 align="center">一个能在网页语音的项目.</h5>
<h5 align="center">🚧 WitsTalk还在开发状态下,请勿当作主力使用.</h5>

<div align="center">
    <img alt="GitHub" src="https://img.shields.io/github/license/xin-sin/WitsTalk?style=for-the-badge">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/xin-sin/WitsTalk/Experimental-UI?style=for-the-badge">
</div>

<div align="center">
    <img src="https://img.shields.io/badge/Node-%3E%3Dv14.18.1-green">
    <img src="https://img.shields.io/badge/Vue-v3.0-blue">
    <img src="https://img.shields.io/badge/yarn-v1.22.19-yellow">
    <img src="https://img.shields.io/badge/Java-%3E%3Dv1.8-orange">
    <img src="https://img.shields.io/badge/SPRING%20BOOT-v2.7-green">
    <img src="https://img.shields.io/badge/Nety-v4.1.86.Final-lightgrey">

</div>

## ✨ WitsTalk是什么项目？
- `WitsTalk`是我们项目团队在2021/12/11制作的开源项目,项目的构思是在群语音的时候发现了一些缺点,才开发此项目.
- 实现在语音聊天中群员可以调节任何群员的`输出音量`以及自己的`输入音量`.
- 管理员拥有最高权限,可以开关群员的`麦克风`,也可以调节群员的`输入音量`. (...暂定是这些功能)
- 项目的初衷是给Minecraft玩家一个更舒服的语音环境,更好的交流环境.

## 💡️ WitsTalk该如何使用？
- ~~由于该项目还在开发中,暂不提供使用方式,只提供开发方法~~
- 一开完毕将提供完整的`使用文档`和`release`

## ✏️ 如何向WitsTalk提交代码?
- 1.Fork`WitsTalk`
- 2.维护代码~
- 3.请遵守以下提交格式:
- `🚧 Fix`,`➕ Feat`,`🔨 Refactor`,`📝 Docs`,`✨ Style`,`🍱 Perf`,`🔧 Test`,`⚡️ Chore`,`🐛 Bug`
- 4.提交到`主仓库`的修改的`相应分支`.

## ✅ 如何发送Issues?
- 请遵守以下提交格式:
- `🐛 Bug`,`✨ Style`,`🎨 Proposai`.

## 👥 本项目开发人员
- `[UI设计、前端]Mo_Yi`  `[后端、前端]xinxin`  `[后端、前端]wzp`
- [Dongyifengs 的 GitHub](https://github.com/Dongyifengs)
- [XinSin-top 的 GitHub](https://github.com/xin-sin)
- [Wzp-2008 的 GitHub](https://github.com/Wzp-2008)

## ⚖️ 开源协议
- 本项目是面向大众的，所以我们会进行开源,请遵循相关开源协议 [Apache License 2.0](https://github.com/XinSin-top/witsTalk/blob/main/LICENSE) 的规则.
- 众人拾柴火焰高，开源需要依靠大家的努力，请自觉遵守开源协议，弘扬开源精神，共建开源社区！

## 🍀 鸣谢
<div align="center"><img alt="Logo" height="256" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png?_gl=1*avq98w*_ga*NjQ5OTM0MzUxLjE2NDY1NTIyMzQ.*_ga_V0XZL7QHEB*MTY0Njk2NjY2Mi4zLjAuMTY0Njk2NjY2Mi4w" width="256"/></div>


## 🧑‍💻如何开发?
- 1.使用`git clone https://github.com/xin-sin/witsTalk.git` 下载我们的项目
- 2.我们建议您使用`idea`来进行开发,那样将会为您省去很多配置环境时间
- 3.在数据库中创建`user`表

``` mysql
  CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户表用户名',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户表密码',
  `auth` enum('admin','user') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '用户表用户权限',
  `online` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户表用户是否在线',
  `last_login` datetime NULL DEFAULT NULL COMMENT '用户表用户最后上线时间',
  `base64` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户表用户头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 4.在数据库中创建`message`表
``` mysql
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息表id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息表消息内容',
  `sender` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息表发送者',
  `recall` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT '消息表是否撤回',
  `sendtime` datetime NOT NULL COMMENT '消息表消息发送时间',
  `type` enum('text','img') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'text' COMMENT '消息表消息类型',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sender`(`sender`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `witstalk`.`user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 5.在数据库中创建`file`表
``` mysql
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件表id',
  `size` double NOT NULL COMMENT '文件表文件大小',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件表文件名',
  `md5` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件表文件md5',
  `uploadTime` datetime NOT NULL COMMENT '文件表文件上传时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 7.调整每一个模块的数据库`url`
- 8.使用`maven`来下载后端项目依赖
- 9.检查`mysql`数据库版本,并更改`pom.xml`中`JDBC`依赖版本
- 10.使用`npm install`下载前端项目依赖
- 11.(可选).配置`nginx`反向代理,和端口号
- 12.启动前端项目`npm run dev`,启动nginx,启动后端项目:(还用我教吗?-_-):






