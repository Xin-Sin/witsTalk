import { defineStore } from 'pinia'
import {UserInfo} from "../entities/UserInfo";
import {VoiceSettings} from "../entities/VoiceSettings";

export const useStore = defineStore('main', {
    state: () => {
        return{
            userinfo: null as UserInfo | null,
            settings: {noiseSuppression: true, echoCancellation: true, mediaDevice: "default"} as VoiceSettings,
            userRoute: null as Array<any> | null
        }
    }
})
