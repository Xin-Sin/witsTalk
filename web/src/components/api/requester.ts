import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {Md5} from "ts-md5/dist/esm/md5";

const instance = axios.create({
    baseURL: '/'
});
const responseInterceptors = function (response: AxiosResponse): AxiosResponse {
    let token = response.headers.token;
    if (token) {
        window.sessionStorage.setItem("token", token);
    }
    return response;
}
instance.interceptors.response.use(responseInterceptors,)
const requestInterceptors = function (request: AxiosRequestConfig): AxiosRequestConfig {
    let token = window.sessionStorage.getItem("token");
    if (request.headers) {
        request.headers["Access-Token"] = token;
    } else {
        request.headers = {"Access-Token": token}
    }
    return request;
}
instance.interceptors.request.use(requestInterceptors);
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
        // params: {"min_length": 5, "max_length": 6}
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

/**
 * 修改用户密码接口
 * @param username
 * @param password
 */
export function changeUserPassword(username: string, password: string) {
    return instance({
        url: "/user/api/changepassword",
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            "username": username,
            "password": Md5.hashStr(password)
        }
    })
}

export function changeUserHeadImg(username: string, base64: string) {
    return instance({
        url: "/user/api/setHeadPortrait",
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            "username": username,
            "password": base64
        }
    })
}

export function changeUsername(username: string) {
    return instance({
        url: "/user/api/changeUsername",
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            "username": username
        }
    })
}

/**
 * 获取文件
 * @param idMin 最小id
 * @param pageSize 页面大小
 */
export function getFiles(idMin: number,pageSize: number) {
    return instance({
        url: "/file/api/getAllFileNames",
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        params: {
            "min_id": idMin,
            'pageSize': pageSize
        }
    })
}
export function getShowFile(md5: string,name:string,token:string) {
    return instance({
        url: "file/api/downloadFile?md5=" + md5 + "&filename=" + name + "&token=" + token,
        headers: {
            'Content-Type': 'application/json',
        },
    });
}