import {Ws} from "../../config/Config";
import * as Stomp from "stompjs";

let socketFactory = undefined;
let processing = false
export default function SocketFactory(user) {
    return new Promise((resolve, reject) => {
        if(!processing) {
            if (!socketFactory || (socketFactory.status !== "CONNECT" && socketFactory.status !== "CONNECTED")) {
                processing = true;
                socketFactory = Stomp.client(Ws + "sock/endpoint");
                socketFactory.connect({Authorization: "Basic " + btoa(user.user + ":" + user.pass)}, (frame) => {
                    processing = false;
                    resolve(socketFactory);
                })
            } else
                resolve(socketFactory);
        } else
            reject();
    });
}

export function SocketClose() {
    if(socketFactory) {
        socketFactory.close();
    }
}