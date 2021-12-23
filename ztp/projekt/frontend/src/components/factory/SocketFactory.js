import {Ws} from "../../config/Config";
import * as Stomp from "stompjs";

let socketFactory = undefined;

export default function SocketFactory(user) {
    return new Promise((resolve) => {
        if (!socketFactory || (socketFactory.status !== "CONNECT" && socketFactory.status !== "CONNECTED")) {
            socketFactory = Stomp.client(Ws + "sock/endpoint");
            socketFactory.connect({Authorization: "Basic " + btoa(user.user + ":" + user.pass)}, (frame) => {
                resolve(socketFactory);
            })
        } else
            resolve(socketFactory);
    });
}