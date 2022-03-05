## [WitsTalk]智慧语音
**本项目名称为:** `WitsTalk`
- `WitsTalk`是我们项目团队在2021/12/11制作的开源项目,项目的构思是在群语音的时候发现了一些缺点,才开发此项目.
- 实现在语音聊天中群员可以调节任何群员的`输出音量`以及自己的`输入音量`
- 管理员拥有最高权限,可以开关群员的`麦克风`,也可以调节群员的`输入音量` ...暂定是这些功能
- 项目的初衷是给minecraft玩家一个更舒服的语音环境,更好的交流环境

**如何开发** `开发方法`
- 1.使用`git clone https://github.com/XinSin-top/witsTalk.git` 下载我们的项目
- 2.我们建议您使用`idea`来进行开发,那样将会为您省去很多配置环境时间
- 4.在数据库中创建`user`表
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
- 5.在数据库中创建`message`表
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
- 6.在数据库中创建`file`表
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
- 7.在数据库中创建`v_message_base64_info`视图
``` mysql
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_message_base64_info` AS select `message`.`id` AS `id`,`message`.`content` AS `content`,`message`.`sender` AS `sender`,`message`.`recall` AS `recall`,`message`.`sendtime` AS `sendtime`,`message`.`type` AS `type`,`user`.`base64` AS `base64` from (`message` join `user` on((`message`.`sender` = `user`.`username`)));
```
- 8.调整每一个模块的数据库`url`
- 9.使用`maven`来下载后端项目依赖
- 10.检查`mysql`数据库版本,并更改`pom.xml`中`JDBC`依赖版本
- 11.使用`npm install`下载前端项目依赖
- 12.(可选).配置`nginx`反向代理,和端口号
- 13.启动前端项目`npm run dev`,启动nginx,启动后端项目:(还用我教吗?-_-):

**如何使用** `使用方法`
- ~~由于该项目还在开发中,暂不提供使用方式,只提供开发方法~~
- 一开完毕将提供完整的`使用文档`和`release`

**提交代码** `如何提交`
- 1.fork`witstalk`
- 2.维护代码~~(由于接口文档还没有完善,需要大家自己去理解每个接口的意义,也可以通过发`issues`来询问,我们在尽力完善接口文档,ps:在等一位好心人来帮忙完善http.md)~~
- 3.请遵守提交格式`fix`,`feat`,`refactor`,`docs`,`style`,`perf`,`test`,`chore`
- 4.提交到`主仓库`的`master`
- 5.请耐心等待!!!!!!!!

**发送issues** `如何发送`
- ~~暂时还没有模板,随便发~~


**该项目成员** `成员列表`
- `[UI设计、前端]Mo_Yi`  `[后端、前端]xinxin`  `[后端、前端]wzp`

- 本智慧语音项目是面向大众的，所以我们将会把这个项目进行开源处理，具体请遵守开源 [Apache License 2.0](https://github.com/XinSin-top/witsTalk/blob/main/LICENSE) 的规则.

- [Dongyifengs 的 GitHub](https://github.com/Dongyifengs)
- [XinSin-top 的 GitHub](https://github.com/XinSin-top)
- [Wzp-2008 的 GitHub](https://github.com/Wzp-2008)