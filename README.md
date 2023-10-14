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
- [Dongyifengs's GitHub](https://github.com/Dongyifengs)
- [Xin-Sin's GitHub](https://github.com/xin-sin)
- [Wzp-2008's GitHub](https://github.com/Wzp-2008)

## ⚖️ Open source agreement
- This project is for the public, so we will open source, please follow the relevant open source agreement [Apache License 2.0](https://github.com/XinSin-top/witsTalk/blob/main/LICENSE)'s rules.

## 🍀 Acknowledgement
<div style="width: 256px;height: 256px;text-align: center">
<img src="https://resource.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" alt="JetBrains Logo (Main) logo.">
</div>

## 🧑‍💻How to develop?
- 1.Use `git clone https://github.com/xin-sin/witsTalk.git` Download our project
- 2.We recommend that you use `idea` for development, which will save you a lot of time in configuring the environment
- 3.Create a `user` table in the database


```mysql
CREATE TABLE `user`
(
    `id`             int                                                           NOT NULL AUTO_INCREMENT,
    `username`       varchar(25) COLLATE utf8mb4_general_ci                        NOT NULL,
    `password`       varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `auth`           enum ('admin','user') COLLATE utf8mb4_general_ci              NOT NULL DEFAULT 'user',
    `online`         bit(1)                                                        NOT NULL DEFAULT b'0',
    `last_login`     datetime                                                               DEFAULT NULL,
    `base64`         longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    `exclusiveColor` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci            DEFAULT '000000',
    `ban`            int                                                                    DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `id` (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
```
- 4.Create a `message` table in the database

```mysql
CREATE TABLE `message`
(
    `id`       int                                            NOT NULL AUTO_INCREMENT,
    `content`  longtext COLLATE utf8mb4_general_ci            NOT NULL,
    `sender`   varchar(25) COLLATE utf8mb4_general_ci         NOT NULL,
    `recall`   bit(1)                                         NOT NULL DEFAULT b'0',
    `sendtime` datetime                                       NOT NULL,
    `type`     enum ('text','img') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'text',
    PRIMARY KEY (`id`),
    KEY `id` (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 35
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
```
- 5.Create a `file` table in the database

```mysql
CREATE TABLE `file`
(
    `id`         int                                     NOT NULL AUTO_INCREMENT,
    `size`       double                                  NOT NULL,
    `name`       varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
    `md5`        varchar(32) COLLATE utf8mb4_general_ci  NOT NULL,
    `uploadTime` datetime                                NOT NULL,
    `uploaderId` int                                     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `id` (`id`) USING BTREE,
    KEY `uploaderId` (`uploaderId`),
    CONSTRAINT `uploaderId` FOREIGN KEY (`uploaderId`) REFERENCES old_user (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 30
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
```

- 6.Create a `route` table in the database

```mysql
CREATE TABLE `route`
(
    `id`         int                                     NOT NULL AUTO_INCREMENT,
    `path`       varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `name`       varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
    `parentId`   int                                     DEFAULT NULL,
    `component`  varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `title`      varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `auth`       varchar(5) COLLATE utf8mb4_general_ci   NOT NULL,
    `status`     int                                     DEFAULT '0',
    `remark`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `createId`   int                                     DEFAULT NULL,
    `createTime` datetime                                DEFAULT NULL,
    `updateId`   int                                     DEFAULT NULL,
    `updateTime` datetime                                DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
```

- 7.Adjust database for each module `url`
- 8.Use `maven` to download backend project dependencies
- 9.Check the `mysql` database version and change the 'JDBC' dependent version in `pom.xml`
- 10.Use `npm install` to download front-end project dependencies
- 11.(Optional). Configure `nginx` reverse proxy and port number
- 12.Start the front-end project `npm run dev`, start nginx, and start the back-end project: (Do you still use me to
  teach? - _ -):
