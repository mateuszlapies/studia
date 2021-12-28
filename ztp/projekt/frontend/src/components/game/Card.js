import {useEffect, useState} from "react";
import {Api} from "../../config/Config";
import {MDBBadge, MDBSpinner} from "mdb-react-ui-kit";

export default function Card(props) {
    let [info, setInfo] = useState(undefined);
    useEffect(() => {
        fetch(Api + "cards/" + props.card)
            .then((r) => r.json())
            .then((j) => setInfo(j.message));
    }, [props.card]);
    if(props.card) {
        if(info) {
            if(info.color === "b") {
                return (
                    <div className="gcard b dark-card">
                        <div>{info.text.replace("_", "___")}</div>
                        <div className="dark-card-pick">
                            Pick <MDBBadge className="dark-card-blanks" color="light">{info.blanks}</MDBBadge>
                        </div>
                    </div>
                )
            } else {
                return (
                    <div className="gcard w">
                        <div>{info.text}</div>
                    </div>
                )
            }

        } else {
            return (
                <div className="gcard">
                    <div className="gcard-spinner">
                        <MDBSpinner/>
                    </div>
                </div>
            )
        }
    } else {
        return <></>
    }
}