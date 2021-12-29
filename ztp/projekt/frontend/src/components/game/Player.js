import {useEffect, useState} from "react";
import {MDBBadge, MDBIcon} from "mdb-react-ui-kit";
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
        if(!props.sock.subscriptions["submitted-" + props.id]) {
            props.sock.subscribe("/sock/submitted", (frame) => {
                if(frame.body === props.id) {
                    setSub(true);
                    props.sock.send("/info", {}, "");
                }
            }, {id: "submitted-" + props.id});
        } else {
            setSub(false);
        }
    }, [props.sock, props.id, props.chosen]);

    return (
        <div className="player">
            <div className="player-status">
                <div hidden={sub || props.cezar || !props.started}><MDBIcon far icon="hourglass" size="sm" className="me-1" /></div>
                <div hidden={!sub || props.cezar || !props.started}><MDBIcon fas icon="check" size="sm" className="me-1" /></div>
                <div hidden={!props.cezar}><MDBIcon fas icon='crown' size="sm" className="me-1" /></div>
            </div>
            <div className="player-name">{user}<MDBBadge className="ms-1">{props.win}</MDBBadge></div>
        </div>
    )
}