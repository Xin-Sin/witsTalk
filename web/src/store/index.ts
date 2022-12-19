import { defineStore } from 'pinia'
import {UserInfo} from "../entities/UserInfo";

export const useStore = defineStore('main', {
    state: () => {
        return{
            userinfo: null as  UserInfo | null
        }
    },
    actions: {
        updateUserInfo(user:UserInfo){
            this.userinfo = user;
        }
    }
})