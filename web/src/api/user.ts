import {Md5} from "ts-md5/dist/esm/md5";
import instance from "./requester";

/**
 * 登录接口
 * @param username 用户名
 * @param password 密码
 */
export const login = (username: string, password: string) => {
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
};

/**
 * 获取每日一言
 */
export const getHitokoto = async() => {
    return instance({
        url: "https://v1.hitokoto.cn/",
        method: "post",
        headers: {
            "Content-Type": 'application/json'
        }
        // params: {"min_length": 5, "max_length": 6}
    });
};

/**
 * 修改用户密码接口
 * @param username
 * @param password
 */
export const changeUserPassword = (username: string, password: string) => {
    return instance({
        url: "/user/api/changepassword",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            "username": username,
            "password": Md5.hashStr(password)
        }
    });
};

export const changeUserHeadImg = (username: string, base64: string) => {
    return instance({
        url: "/user/api/setHeadPortrait",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            "username": username,
            "password": base64
        }
    });
};

export const changeUsername = (username: string) => {
    return instance({
        url: "/user/api/changeUsername",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            "username": username
        }
    });
};

export const registerUser = (username: string, password: string) => {
    return instance({
        url: "/user/api/adduser",
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        data: {
            "username": username,
            "password": Md5.hashStr(password)
        }
    });
};
export const getWeather = () => {
    return instance({
        url: "/user/api/getWeather",
        method: "get"
    });
};
export const setUserExclusiveColor = (color:string, username:string) => {
    return instance({
        url: "/user/api/setUserExclusiveColor",
        method: "get",
        headers:{
            color:color,
            username:username
        }
    });
};
export const autoLogin = () => {
    return instance({
        url: "/user/api/autoLogin",
        method: "get"
    });
};
export const getRouter = (auth:string) => {
    return instance({
        url: "/user/api/getRouter",
        method: "get",
        params:{
            "auth":auth
        }
    });
};