import Axios from "./axios"  // 导入配置好的axios文件
// 封装axios请求函数，并用export导出

//获取所有文件名接口
export function getAllFileNames(){
  return Axios({
    url: "/file/api/getAllFileNames",
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded' //设置请求头请求格式form
    },
  });
}
// 验证码接口
export function getverificationcode(datas) {
  return Axios({
    url: "/user/api/vc",
    method: "get",
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded' //设置请求头请求格式form
    },
    data: datas
  })
}
//登陆验证接口
export function Login(datas){
  return Axios({
    url: "/user/api/login",
    method: "post",
    headers:{
      "Content-Type" : 'application/json'
    },
    data: datas
  });
}
//获取每日一言
export function getHitokoto(){
  return Axios({
    url : "https://v1.hitokoto.cn/",
    method: "get",
    headers:{
      "Content-Type" : 'application/json'
    },
  });
}
//获取所有聊天信息
export function getAllMessage() {
  return Axios({
    url: "/user/api/message/getAll",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  });
}
//获取聊天信息总行数
export function getMessageCount(){
  return Axios({
    url: "/user/api/message/count",
    headers:{
      'Content-Type': 'application/json',
    }
  });
}
//发送信息
export function sendMessage(message,sender){
  return Axios({
    url: "/user/api/message/send",
    method: "post",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
    data:{"content":message,"sender":sender,"type":"text"},
  });
}
//发送用户在线情况
export function getAllUserOnline(){
  return Axios({
    url: "/user/api/getOnlineUser",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  });
}
export function getHeadImg(username){
  return Axios({
    url: "/user/api/getUserHeadPortrait/" + username,
    headers:{
      'Content-Type': 'application/json', //设置请求头请求格式form
    }
  })
}
