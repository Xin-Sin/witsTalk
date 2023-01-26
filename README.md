<div align="center"><img alt="Logo" height="128" src="web/src/assets/logo.png" width="128"/></div>

<h2 align="center">🌟Project Name: WitsTalk</h2>
<h5 align="center">A project that can speak on the web.</h5>
<h5 align="center">🚧 Do not use the WitsTalk as the main force while it is still under development</h5>

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

<div>
    <a href="./README_zh.md">[中文 Readme]</a>
</div>

## ✨ What is the mobile WitsTalk project？
- Mobile `WitsTalk` is an open source project made by our project team on December 11, 2021/12/11. The idea of the project is to develop this project after finding some shortcomings in the group voice.
- In the voice chat group members can adjust any group members' mobile `Output volume` and their own mobile `Input volume`.
- Administrators have the highest privileges to switch on and off the group member's `microphone` or to adjust the group member's `input volume`. (... tentatively for these functions)
- The original purpose of the project is to give Minecraft players a more comfortable voice environment and a better communication environment.

## 💡️ How to use WitsTalk？
- ~~As the project is still under development, the use method is not provided temporarily, only the development method is provided~~
- The complete "Use Document" and "Release" will be provided after the opening`

## ✏️ How do I submit code to WitsTalk?
- 1.Fork`WitsTalk`
- 2.Maintenance code~
- 3.Please follow the following submission format:
- `🚧 Fix`,`➕ Feat`,`🔨 Refactor`,`📝 Docs`,`✨ Style`,`🍱 Perf`,`🔧 Test`,`⚡️ Chore`,`🐛 Bug`
- 4.The modified corresponding branch submitted to the main warehouse

## ✅ How do I send Issues?
- Please follow the following submission format:
- `🐛 Bug`,`✨ Style`,`🎨 Proposai`.

## 👥 Developers of the project
- `[UI design、Web Developer] Mo_Yi`
- `[Server Developer、Web Developer] Xin-Sin`
- `[Server Developer、Web Developer] Wzp-2008`
- [Dongyifengs 的 GitHub](https://github.com/Dongyifengs)
- [Xin-Sin 的 GitHub](https://github.com/xin-sin)
- [Wzp-2008 的 GitHub](https://github.com/Wzp-2008)

## ⚖️ Open source agreement
- This project is for the public, so we will open source, please follow the relevant open source agreement [Apache License 2.0](https://github.com/XinSin-top/witsTalk/blob/main/LICENSE) 的规则.

## 🍀 Acknowledgement
<div align="center"><img alt="Logo" height="256" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png?_gl=1*avq98w*_ga*NjQ5OTM0MzUxLjE2NDY1NTIyMzQ.*_ga_V0XZL7QHEB*MTY0Njk2NjY2Mi4zLjAuMTY0Njk2NjY2Mi4w" width="256"/></div>


## 🧑‍💻How to develop?
- 1.Use `git clone https://github.com/xin-sin/witsTalk.git` Download our project
- 2.We recommend that you use `idea` for development, which will save you a lot of time in configuring the environment
- 3.Create a `user` table in the database


``` mysql
  CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User table id',
  `username` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'User table user name',
  `password` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'User table password',
  `auth` enum('admin','user') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT 'User table user permissions',
  `online` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'User table whether the user is online',
  `last_login` datetime NULL DEFAULT NULL COMMENT 'User table user's last online time',
  `base64` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'User table user avatar',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 4.Create a `message` table in the database
``` mysql
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Message table id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Message Table Message Content',
  `sender` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Message table sender',
  `recall` tinyint(1) UNSIGNED ZEROFILL NOT NULL DEFAULT 0 COMMENT 'Whether the message table is recalled',
  `sendtime` datetime NOT NULL COMMENT 'Message table message sending time',
  `type` enum('text','img') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'text' COMMENT 'Message Table Message Type',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sender`(`sender`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender`) REFERENCES `witstalk`.`user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 5.Create a `file` table in the database
``` mysql
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'File table id',
  `size` double NOT NULL COMMENT 'File Table File Size',
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'File table file name',
  `md5` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'File table file md5',
  `uploadTime` datetime NOT NULL COMMENT 'File table file upload time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;
```
- 7.Adjust database for each module `url`
- 8.Use `maven` to download backend project dependencies
- 9.Check the `mysql` database version and change the 'JDBC' dependent version in `pom.xml`
- 10.Use `npm install` to download front-end project dependencies
- 11.(Optional). Configure `nginx` reverse proxy and port number
- 12.Start the front-end project `npm run dev`, start nginx, and start the back-end project: (Do you still use me to teach? - _ -):