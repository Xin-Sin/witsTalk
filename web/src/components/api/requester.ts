import axios, {AxiosResponse} from "axios";
import {Md5} from "ts-md5/dist/esm/md5";

const instance = axios.create({
    baseURL: 'http://127.0.0.1:8080'
});
const responseInterceptors = function (response: AxiosResponse): AxiosResponse {
    let token = response.headers.token;
    if (token) {
        window.sessionStorage.setItem("token", token);
    }
    return response;
}
instance.interceptors.response.use(responseInterceptors,)

/**
 * 获取所有文件
 */
export function getAllFileNames() {
    return instance({
        url: "/file/api/getAllFileNames",
        method: 'post',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    });
}

/**
 * 登录接口
 * @param username 用户名
 * @param password 密码
 */
export function login(username: string, password: string) {
    return instance({
        url: "/user/api/login",
        method: "post",
        headers: {
            "Content-Type": 'application/json'
        },
        data: {
            "username": username,
            "password": Md5.hashStr(password)
        }
    });
}

/**
 * 获取每日一言
 */
export async function getHitokoto() {
    return instance({
        url: "https://v1.hitokoto.cn/",
        method: "post",
        headers: {
            "Content-Type": 'application/json'
        },
        params: {"min_length": 5, "max_length": 6}
    });
}

/**
 * 获取用户在线状态
 */
export function getAllUserOnline() {
    return instance({
        url: "/user/api/getOnlineUser",
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

/**
 * 获取头像接口
 * @param username 用户名
 */
export function getHeadImg(username: string) {
    return instance({
        url: "/user/api/getUserHeadPortrait/" + username,
        headers: {
            'Content-Type': 'application/json',
        }
    })
}