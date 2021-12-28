import {useState} from "react";

export default function Timer(props) {
    let [time, setTime] = useState(props.timestamp - Date.now());
    if(props.timestamp) {
        let timeout = setTimeout(() => setTime(props.timestamp - Date.now()), 1);
        if(time <= 0)
            clearTimeout(timeout);
        setTime(0);

        function formatSeconds(secs) {
            function pad(n) {
                return (n < 10 ? "0" + n : n);
            }
            let h = Math.floor(secs / 3600);
            let m = Math.floor(secs / 60) - (h * 60);
            let s = Math.floor(secs - h * 3600 - m * 60);
            return pad(m) +":"+ pad(s);
        }

        return (
            <div>{formatSeconds(time)}</div>
        )
    } else {
        return <></>
    }
}