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
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  });
}

export function getMessageCount(){
  return Axios({
    url: "api/message/count",
    headers:{
      'Content-Type': 'application/json',
    }
  });
}

export function getSomeMessage(min,max){
  return Axios({
    url: "api/message/get?min=" + min + "&max=" + max,
    headers:{
      'Content-Type': 'application/json'
    }
  });
}

export function sendMessage(message,sender){
  return Axios({
    url: "api/message/send",
    method: "post",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
    data:{"content":message,"sender":sender,"type":"text"},
  });
}

export function getAllUserOnline(){
  return Axios({
    url: "api/message/getOnlineUser",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
  });
}


export async function getUserHeading(username){
  return Axios({
    url: "api/getHeadPortrait",
    method: "post",
    headers: {
      'Content-Type': 'application/json', //设置请求头请求格式form
    },
    data:{"username":username},
  });
}
