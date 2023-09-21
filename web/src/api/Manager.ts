import instance from "./requester";

export const getRoutes = (page:number, pageNum:number) => {
    return instance({
        url: "/user/sys/getRoutes",
        method: "get",
        headers: {
            "Content-Type": 'application/json'
        },
        params: {
            "page": page,
            "pageNum": pageNum
        }
    });
};
export const getRouteName = () => {
    return instance({
        url: "/user/sys/getRouteName",
        method: "get",
        headers: {
            "Content-Type": 'application/json'
        }
    });
};
export const addRoute = (route:object) => {
    return instance({
        url: "/user/sys/addRoute",
        method: "POST",
        headers:{
            "Content-Type": 'application/json'
        },
        data: route
    });
};
export const updateRoute = (route:object) => {
    return instance({
        url: "/user/sys/updateRoute",
        method: "POST",
        headers:{
            "Content-Type": 'application/json'
        },
        data: route
    });
};
export const updateStatus = (id:number, statusId:number) => {
    return instance({
        url: "/user/sys/updateStatus",
        method: "get",
        headers: {
            "Content-Type": 'application/json'
        },
        params: {
            "id": id,
            "status": statusId
        }
    });
};