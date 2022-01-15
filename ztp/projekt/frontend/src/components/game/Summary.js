import {MDBCol, MDBIcon, MDBRow} from "mdb-react-ui-kit";
import {useEffect, useState} from "react";
import {Api} from "../../config/Config";

export default function Summary(props) {
    let [winner, setWinner] = useState("");

    useEffect(() => {
        checkWinner();
    }, [props.game.ended]);

    let checkWinner = () => {
        let values = Object.values(props.game.players);
        let value = Math.max(...values);
        let id = Object.keys(props.game.players)[values.indexOf(value)];
        fetch(Api + "users/" + id)
            .then(r => r.json())
            .then(j => setWinner(j.message));
    }

    return (
        <>
            <MDBRow style={{"text-align": "center", "padding-top": "1rem"}}>
                    <MDBCol>
                        <div><MDBIcon size="10x" fas icon="trophy"/></div>
                        <div style={{"padding": ".5rem"}}>Game over</div>
                    </MDBCol>
            </MDBRow>
            <MDBRow style={{"text-align": "center"}}>
                <MDBCol>
                    <div>and the winner is {winner}!</div>
                </MDBCol>
            </MDBRow>
        </>
    );
}