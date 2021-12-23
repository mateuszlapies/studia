import {useState} from "react";
import Offline from "./Offline";
import Loading from "./Loading";
import {Api} from "../config/Config";

export default function Verify(props) {
    let [verified, setVerified] = useState(undefined);

    fetch(Api + "actuator/health")
        .then((r) => r.json())
        .then((j) => setVerified(j.status === "UP"))
        .catch(() => setVerified(false));
    if(verified) {
        return (
            <>
                {props.children}
            </>
        );
    } else if(verified !== undefined) {
        return <Offline/>
    } else {
        return <Loading/>
    }
}