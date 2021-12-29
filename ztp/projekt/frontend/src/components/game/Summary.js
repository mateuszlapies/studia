import {MDBCol, MDBRow} from "mdb-react-ui-kit";
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
            <MDBRow>
                <MDBCol/>
                    <MDBCol>
                        <div>Game over!</div>
                    </MDBCol>
                <MDBCol/>
            </MDBRow>
            <MDBRow>
                <MDBCol/>
                <MDBCol>
                    <div>The winner is {winner}</div>
                </MDBCol>
                <MDBCol/>
            </MDBRow>
        </>
    );
}