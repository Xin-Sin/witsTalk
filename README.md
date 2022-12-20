## [WitsTalk]智慧语音
**本项目名称为:** `WitsTalk`
- `WitsTalk`是我们项目团队在2021/12/11制作的开源项目,项目的构思是在群语音的时候发现了一些缺点,才开发此项目.
- 实现在语音聊天中群员可以调节任何群员的`输出音量`以及自己的`输入音量`
- 管理员拥有最高权限,可以开关群员的`麦克风`,也可以调节群员的`输入音量`
- 新分支暂加功能
  - 消息回复功能
  - 可发送表情信息
  - 文件分享功能
    - 对文件上传或者下载
    - 对简单文件进行在线预览,如(yml,xml,json,txt,log,mp3,mav,ogg,flv,mp4等格式)
- 暂不提供频道功能,会在这个项目开发完毕之后根据用户反馈,在考虑是否要进行二开(会对整个项目进行大重构,包括后端和数据加密进行处理)
- 项目的初衷是给minecraft玩家一个更舒服的语音环境,更好的交流环境,更友好的文件分享

**如何开发** `开发方法`
- 1.使用`git clone https://github.com/XinSin-top/witsTalk.git` 下载我们的项目
- 2.我们建议您使用`idea`来进行开发,那样将会为您省去很多配置环境时间
- 3.由于该分支正在积极开发(推进度),有很多不确定因素,所以我们暂不提供DDL语句
- 4.调整每一个模块的数据库`url`
- 5.使用`maven`来下载后端项目依赖
- 6.检查`mysql`数据库版本,并更改`pom.xml`中`JDBC`依赖版本
- 7.使用`npm install`下载前端项目依赖
- 8.(可选).配置`nginx`反向代理,和端口号
- 9.启动前端项目`npm run dev`,启动nginx,启动后端项目

**如何使用** `使用方法`
- 一开完毕将提供完整的`使用文档`和`release`

**提交代码** `提交方法`
- 1.fork`witstalk`
- 2.维护代码~~(由于接口文档还没有完善,需要大家自己去理解每个接口的意义,也可以通过发`issues`来询问,我们在尽力完善接口文档,ps:在等一位好心人来帮忙完善http.md)~~
- 3.请遵守提交格式`fix`,`feat`,`refactor`,`docs`,`style`,`perf`,`test`,`chore`
- 4.提交到`主仓库`的`master`
- 5.请耐心等待!!!!!!!!

**发送issues** `发送方法`
- ~~暂时还没有模板,随便发~~


**该项目成员** `成员列表`
- `[UI设计、前端]Mo_Yi`  `[后端、前端]xinxin`  `[后端、前端]wzp`

- 本智慧语音项目是面向大众的，所以我们将会把这个项目进行开源处理，具体请遵守开源 [Apache License 2.0](https://github.com/XinSin-top/witsTalk/blob/main/LICENSE) 的规则.

- [Dongyifengs 的 GitHub](https://github.com/Dongyifengs)
- [XinSin-top 的 GitHub](https://github.com/XinSin-top)
- [Wzp-2008 的 GitHub](https://github.com/Wzp-2008)

**鸣谢** `鸣谢`
- [jetbrains](https://www.jetbrains.com)
![jetbrains](https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png?_gl=1*avq98w*_ga*NjQ5OTM0MzUxLjE2NDY1NTIyMzQ.*_ga_V0XZL7QHEB*MTY0Njk2NjY2Mi4zLjAuMTY0Njk2NjY2Mi4w)
