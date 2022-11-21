import axios, {AxiosRequestConfig, AxiosResponse} from "axios";

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

export default instance;