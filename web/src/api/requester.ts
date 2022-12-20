import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
const instance = axios.create({
    baseURL: window.location.protocol + '//' + window.location.hostname + ":" + window.location.port
});
const responseInterceptors = (response: AxiosResponse): AxiosResponse => {
    let token = response.headers.token;
    if (token) {
        window.localStorage.setItem("token", token);
    }
    return response;
}
instance.interceptors.response.use(responseInterceptors,)
const requestInterceptors = (request: AxiosRequestConfig): AxiosRequestConfig => {
    let token = window.localStorage.getItem("token");
    if (request.headers) {
        request.headers["Access-Token"] = token;
    } else {
        request.headers = {"Access-Token": token}
    }
    return request;
}
instance.interceptors.request.use(requestInterceptors);

export default instance;