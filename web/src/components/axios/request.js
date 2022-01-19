import Axios from "./axios"  // 导入配置好的axios文件
// 封装axios请求函数，并用export导出
export function getverificationcode(datas) {
  return Axios({
    url: "api/vc",
    method: "get",
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded' //设置请求头请求格式form
    },
    data: datas
  })
}
export function Login(datas){
  return Axios({
    url: "/api/login",
    method: "post",
    headers:{
      "Content-Type" : 'application/json'
    },
    data: datas
  });
}

export function getOneMessage(){
  return Axios({
    url : "https://v1.hitokoto.cn/",
    method: "get",
    headers:{
      "Content-Type" : 'application/json'
    },
  });
}

export function getAllMessage() {
  return Axios({
    url: "api/message/getAll",
    method: "get",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  })
}

export function sendMessage(message,sender){
  return Axios({
    url: "api/message/send",
    method: "post",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
    data:{"content":message,"sender":sender},
  })
}

export function getAllUserOnline(){
  return Axios({
    url: "api/message/getOnlineUser",
    method: "get",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  })
}