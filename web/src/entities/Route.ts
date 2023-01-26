export interface Route{
    path: string;
    name: string;
    parentId: number | string;
    component: string;
    title: string;
    auth: string;
    status: number | boolean;
    remark: string;
    updateId: number;
    createId: number;
    createTime: string;
    updateTime: string;
    id: number;
}