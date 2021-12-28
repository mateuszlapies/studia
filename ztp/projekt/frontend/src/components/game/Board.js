import {MDBBtn, MDBCol, MDBRow, MDBSpinner} from "mdb-react-ui-kit";
import Card from "./Card";
import Timer from "./Timer";
import {useState} from "react";
import SocketFactory from "../factory/SocketFactory";
import Player from "./Player";
import {InfoContext} from "../../contexts/InfoContext";

export default function Board(props) {
    let [cards, setCards] = useState([]);
    if(props.game.started && cards.length < 11) {
        SocketFactory(props.user)
            .then(r => {
                if (!r.subscriptions.cards)
                    r.subscribe("/user/sock/cards", (f) => setCards(JSON.parse(f.body)), {id: "cards"});
                r.send("/cards", {}, "");
            })
            .catch();
    }
    if(props.game.started) {
        return (
            <>
                <MDBRow>
                    <MDBCol>
                        {props.game.players.map((item, index) => <Player key={index} id={item} user={props.user} started={props.game.started} />)}
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol><Timer timestamp={props.game.timer} /></MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol>
                        <div className="dark-card-container">
                            <Card card={props.game.blackCard}/>
                        </div>
                    </MDBCol>
                    {() => {
                        if(props.game.whiteCards.length > 0)
                            return (
                                <MDBCol size="8">
                                    {props.game.whiteCards.map((item, index) => <Card key={index} card={item}/>)}
                                </MDBCol>
                            )
                    }}
                </MDBRow>
                <MDBRow>
                    <MDBCol>
                        <div className="stack">
                            <div className="stack-container">
                                {cards.map((item, index) => <Card key={index} card={item}/>)}
                            </div>
                        </div>
                    </MDBCol>
                </MDBRow>
            </>
        );
    } else {
        return (
            <>
                <MDBRow>
                    <MDBCol>
                        {props.game.players.map((item, index) => <Player key={index} id={item} user={props.user} started={props.game.started} />)}
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol className="start">
                        <InfoContext.Consumer>
                            {(context) => {
                                if(context.info.id === props.game.cezar && props.game.players.length > 2)
                                {
                                    return (
                                        <div onClick={() => props.sock.send("/start")}>
                                            <MDBBtn>Start ({props.game.players.length} players)</MDBBtn>
                                        </div>
                                    )
                                } else if(props.game.players.length > 2) {
                                    return (
                                        <div>
                                            <MDBSpinner size="sm" className="me-1" />Waiting for host to start the game
                                        </div>
                                    )
                                } else {
                                    return (
                                        <div>
                                            <MDBSpinner size="sm" className="me-1" />Waiting for other players to join
                                        </div>
                                    )
                                }
                            }}
                        </InfoContext.Consumer>
                    </MDBCol>
                </MDBRow>
            </>
        )
    }

}