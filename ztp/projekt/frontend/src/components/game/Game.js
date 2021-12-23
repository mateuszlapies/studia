import {useState} from "react";
import SocketFactory from "../factory/SocketFactory";

export default function Game(props) {
    let [game, setGame] = useState({
        card: undefined,
        cezar: undefined,
        players: [],
        updateGame: (d) => {
            d.updateGame = setGame;
            setGame(d)
        }
    });

    SocketFactory(props.user)
        .then(r => {
            if(!r.subscriptions.info)
                r.subscribe("/sock/info", (f) => game.updateGame(JSON.parse(f.body)), {id: "info"});

            //r.send("/info", {}, "");
        });
    return (
        <div/>
    )
}