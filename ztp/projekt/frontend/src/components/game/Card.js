import {useEffect, useState} from "react";
import {Api} from "../../config/Config";
import {MDBBadge, MDBCheckbox, MDBSpinner} from "mdb-react-ui-kit";
import {SubmitContext} from "../../contexts/SubmitContext";

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
                        <div>{info.text.replaceAll("_", "___")}</div>
                        <div className="dark-card-pick">
                            Pick <MDBBadge className="dark-card-blanks" color="light">{info.blanks}</MDBBadge>
                        </div>
                    </div>
                )
            } else {
                return (
                    <SubmitContext.Consumer>
                        {(context) => {
                            return (
                                <div className={"gcard w" + (!props.cezar ? " clickable" : "")} onClick={() => context.changeList(props.blanks, props.card)}>
                                    <div hidden={props.cezar} className="gcard-checkbox">
                                        <MDBCheckbox type="checkbox" checked={context.list.indexOf(props.card) > -1} disabled/>
                                    </div>
                                    <div>{info.text}</div>
                                </div>
                            )
                        }}
                    </SubmitContext.Consumer>
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