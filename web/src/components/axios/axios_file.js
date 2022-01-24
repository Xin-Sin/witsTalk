import axios from 'axios'

// axios.defaults.baseURL = 'http://wzpmc.cn:8003';

const Axios_instance = axios.create({
  baseURL:'http://127.0.0.1:8004',
  timeout: 60000, 		      // 请求超时时间1分钟
  responseType: "json",
  withCredentials: false    // 是否允许带cookie这些
})

//请求拦截器
Axios_instance.interceptors.request.use(function (config){
  config.headers["Access-Token"] = window.localStorage.getItem("token");
  return config;
}),function (error){
  return Promise.reject(error);
}
export default Axios_instance;
