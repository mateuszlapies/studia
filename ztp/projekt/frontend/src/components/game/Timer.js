import {useEffect, useState} from "react";
import {MDBIcon} from "mdb-react-ui-kit";

export default function Timer(props) {
    let [time, setTime] = useState(Date.parse(props.timestamp) - Date.now());

    useEffect(() => {
        if(props.cezar && time < 0) {
            props.sock.send("/timeout", {}, "");
        }
    }, [props.cezar, props.sock, time]);

    if(props.timestamp && !props.chosen) {
        let timeout = setTimeout(() => setTime(Date.parse(props.timestamp) - Date.now()), 1000);
        if(time < 0) {
            clearTimeout(timeout);
        }

        function formatSeconds(secs) {
            function pad(n) {
                return (n < 10 ? "0" + n : n);
            }
            let m = Math.floor(secs / 60000);
            let s = Math.floor(secs / 1000 - m * 60);
            return pad(isNaN(m) ? 0 : m) + ":" + pad(isNaN(s) ? 0 : s);
        }

        return (
            <div><MDBIcon far icon="clock" className="me-1" />{formatSeconds(time)}</div>
        )
    } else {
        return <></>
    }
}