import instance from "./requester";
/**
 * 获取文件
 * @param idMin 最小id
 * @param pageSize 页面大小
 */
export const getFiles = (idMin: number,pageSize: number) => {
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
/**
 * 获取文件二进制
 * @param md5 文件校验值
 * @param name 文件名
 * @param token 用户token 
 */
export const getShowFile = (md5: string,name:string,token:string) => {
    return instance({
        url: "file/api/downloadFile?md5=" + md5 + "&filename=" + name + "&token=" + token,
        headers: {
            'Content-Type': 'application/json',
        },
    });
}