import {useEffect, useState} from "react";
import SocketFactory from "../factory/SocketFactory";

import "../../styles/Game.css";
import Board from "./Board";

export default function Game(props) {
    let [game, setGame] = useState({
        started: false,
        cezar: undefined,
        blackCard: undefined,
        whiteCards: [],
        players: []
    });
    let [sock, setSock] = useState(undefined);

    useEffect(() => {
        SocketFactory(props.user)
            .then(r => {
                if(!r.subscriptions.info)
                    r.subscribe("/sock/info", (f) => setGame(JSON.parse(f.body)), {id: "info"});
                r.send("/info", {}, "");
                setSock(r);
            })
            .catch();
    }, [props.user]);
    return (
        <Board game={game} user={props.user} sock={sock} />
    )
}