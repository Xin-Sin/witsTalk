import {Md5} from "ts-md5/dist/esm/md5";
import instance from "./requester";

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

export function registerUser(username: string, password: string) {
    return instance({
        url: "/user/api/adduser",
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            "username": username,
            "password": Md5.hashStr(password),
        }
    })
}
export function getWeather() {
    return instance({
        url: "/user/api/getWeather",
        method: "get",
    })
}
export function setUserExclusiveColor(color:string,username:string) {
    return instance({
        url: "/user/api/setUserExclusiveColor",
        method: "get",
        headers:{
            color:color,
            username:username
        }
    })
}