import "../../styles/Game.css";
import Board from "./Board";
import {useEffect, useState} from "react";
import SocketFactory from "../factory/SocketFactory";

export default function Game(props) {
    let [game, setGame] = useState(undefined);
    let [sock, setSock] = useState(undefined)
    useEffect(() => {
        SocketFactory(props.user)
            .then(r => {
                if(!r.subscriptions.info)
                    r.subscribe("/sock/info", (f) => setGame(JSON.parse(f.body)), {id: "info"});
                r.send("/info", {}, "");
                setSock(r);
            })
            .catch();
    }, [props.user, sock]);

    if(game) {
        return (
            <Board game={game} sock={sock} user={props.user} />
        )
    } else
        return <></>

}