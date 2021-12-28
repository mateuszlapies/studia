import SocketFactory from "../factory/SocketFactory";
import {useEffect, useState} from "react";
import {MDBIcon, MDBSpinner} from "mdb-react-ui-kit";
import {Api} from "../../config/Config";

export default function Player(props) {
    let [sub, setSub] = useState(false);
    let [user, setUser] = useState("");

    useEffect(() => {
        fetch(Api + "users/" + props.id)
        .then(r => r.json())
        .then(j => setUser(j.message))
    });

    useEffect(() => {
        SocketFactory(props.user)
            .then(r => {
                if(r.subscriptions["submitted-" + props.user.user])
                    r.subscribe("/sock/submitted", (r) => props.id === r ? setSub(true) : null, {id: "submitted-" + props.user.user});
            });
    }, [props.user])


    return (
        <div className="player">
            <div className="player-status" hidden={!props.started}>
                <div hidden={sub}><MDBIcon far icon="hourglass" size="sm" className="me-1" /></div>
                <div hidden={!sub}><MDBIcon fas icon="check" /></div>
            </div>
            <div className="player-name">{user}</div>
        </div>
    )
}