import axios, {AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse, InternalAxiosRequestConfig} from "axios";
import {AxiosInterceptorManager, AxiosInterceptorOptions} from "axios/index";
const location = window.location;
const url = location.protocol + '//' + location.hostname + ":" + location.port;
const instance = axios.create({
    baseURL: url
});
const responseInterceptors = (response: AxiosResponse): AxiosResponse => {
    const token = response.headers.token;
    if (token) {
        window.localStorage.setItem("token", token);
    }
    return response;
};
instance.interceptors.response.use(responseInterceptors);
const requestInterceptors = (request: InternalAxiosRequestConfig<any>):
    InternalAxiosRequestConfig<any> => {
    const token = window.localStorage.getItem("token");
    if (request.headers) {
        request.headers["Access-Token"] = token;
    } else {
        // @ts-ignore
        request.headers = {"Access-Token": token};
    }
    return request;
};
instance.interceptors.request.use(requestInterceptors);

export default instance;