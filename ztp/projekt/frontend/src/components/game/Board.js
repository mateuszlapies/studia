import {
    MDBBtn,
    MDBCol, MDBInput,
    MDBModal,
    MDBModalBody,
    MDBModalContent,
    MDBModalDialog, MDBModalFooter, MDBModalHeader,
    MDBRow,
    MDBSpinner
} from "mdb-react-ui-kit";
import Card from "./Card";
import Timer from "./Timer";
import {useContext, useEffect, useState} from "react";
import Player from "./Player";
import {InfoContext} from "../../contexts/InfoContext";
import {SubmitContext} from "../../contexts/SubmitContext";
import MessageType from "../../enums/MessageTypes.json";
import {MessageContext} from "../../contexts/MessageContext";

export default function Board(props) {
    let [cards, setCards] = useState([]);
    let [submit, setSubmit] = useState({
        list: [],
        changeList: (b,d) => {
            let list = submit.list;
            if(list.indexOf(d) > -1) {
                list.splice(list.indexOf(d), 1);
            } else {
                if(b > list.length)
                    list.push(d);
            }
            setSubmit({list: list, changeList: submit.changeList, clearList: submit.clearList});
        },
        clearList: () => {
            setSubmit({list: [], changeList: submit.changeList, clearList: submit.clearList});
        }
    });
    let [submitted, setSubmitted] = useState(false);
    let [selected, setSelected] = useState([]);
    const message = useContext(MessageContext);

    useEffect(() => {
        if(props.game && props.game.started) {
            if (!props.sock.subscriptions.cards)
                props.sock.subscribe("/user/sock/cards", (f) => {
                    setCards(JSON.parse(f.body));
                }, {id: "cards"});
            if(!props.sock.subscriptions.win)
                props.sock.subscribe("/sock/win", (f) => {
                    submit.clearList();
                    setSelected([]);
                    setSubmitted(false);
                    message.setMessage({type: MessageType.WIN, content: f.body, displayed: true});
                    props.sock.send("/info", {}, "");
                }, {id: "win"});
            props.sock.send("/cards", {}, "");
        }
    }, [props.game, props.sock, submit.changeList, message, submit])

    if(props.game.started) {
        return (
            <>
                <MDBRow>
                    <MDBCol>
                        {
                            Object.keys(props.game.players).map((item, index) => <Player key={index} id={item} win={props.game.players[item]}
                                                                            sock={props.sock}
                                                                            chosen={props.game.chosen}
                                                                            started={props.game.started}
                                                                            cezar={item === props.game.cezar}/>)
                        }
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol>
                        <InfoContext.Consumer>
                            {(context) => (
                                <Timer chosen={props.game.chosen} timestamp={props.game.timestamp}
                                       sock={props.sock} cezar={context.info.id === props.game.cezar}/>
                            )}
                        </InfoContext.Consumer>
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol>
                        <div className="dark-card-container">
                            <Card card={props.game.blackCard}/>
                        </div>
                    </MDBCol>
                    <InfoContext.Consumer>
                        {(context) => {
                            if(props.game.chosen) {
                                if (props.game && props.game.whiteCards.length > 0 && props.game.chosen)
                                    if(context.info.id === props.game.cezar) {
                                        return (
                                            <MDBCol size="8">
                                                <div className="text-center">Select the best answer</div>
                                                <div className="end-stack">
                                                    <div className="end-stack-container">
                                                        {props.game.whiteCards.map((item, index) => (
                                                            <div id={index} key={index} className="end-stack-sm-container"
                                                                 onClick={() => setSelected(item)}>
                                                                {item.map((it, id) => <Card key={id} card={it} cezar={true}/>)}
                                                            </div>)
                                                        )}
                                                    </div>
                                                </div>
                                            </MDBCol>
                                        )
                                    } else {
                                        return (
                                            <MDBCol>
                                                <div className="end-stack">
                                                    <div className="end-stack-container">
                                                        {props.game.whiteCards.map((item, index) => (
                                                            <div key={index} className="end-stack-sm-container">
                                                                {item.map((it, id) => <Card key={id} card={it} cezar={true}/>)}
                                                            </div>)
                                                        )}
                                                    </div>
                                                </div>
                                            </MDBCol>
                                        )
                                    }
                            } else {
                                if(context.info.id !== props.game.cezar) {
                                    if (submit.list.length === props.game.blanks) {
                                        return (
                                            <MDBCol>
                                                <MDBBtn disabled={submitted} onClick={() => {
                                                    setSubmitted(true);
                                                    props.sock.send("/submitted", {}, JSON.stringify(submit.list));
                                                }}>Submit</MDBBtn>
                                            </MDBCol>
                                        )
                                    } else {
                                        return (
                                            <MDBCol>
                                                <div>You need to select {props.game.blanks - submit.list.length} more
                                                    card(s)
                                                </div>
                                            </MDBCol>
                                        )
                                    }
                                } else {
                                    return (
                                        <MDBCol>
                                            <div>
                                                <MDBSpinner size="sm" className="me-1"/>Waiting for other players to pick cards
                                            </div>
                                        </MDBCol>
                                    )
                                }
                            }
                        }}
                    </InfoContext.Consumer>
                </MDBRow>
                <MDBRow>
                    <MDBCol>
                        <div className="stack">
                            <div className="stack-container">
                                <SubmitContext.Provider value={submit}>
                                    <InfoContext.Consumer>
                                        {(context) => (
                                            cards.map((item, index) => <Card key={index} card={item} submitted={submitted} blanks={props.game.blanks} cezar={context.info.id === props.game.cezar}/>)
                                        )}
                                    </InfoContext.Consumer>
                                </SubmitContext.Provider>
                            </div>
                        </div>
                    </MDBCol>
                </MDBRow>
                <MDBModal show={selected.length > 0} tabIndex='-1'>
                    <MDBModalDialog>
                        <MDBModalContent>
                            <MDBModalHeader>
                                Select the winner!
                            </MDBModalHeader>
                            <MDBModalBody className="text-center">
                                {selected.map((it, id) => <Card key={id} card={it} cezar={true}/>)}
                            </MDBModalBody>
                            <MDBModalFooter>
                                <MDBRow>
                                    <MDBCol>
                                        <MDBBtn color="danger" onClick={() => setSelected([])}>Cancel</MDBBtn>
                                    </MDBCol>
                                    <MDBCol>
                                        <MDBBtn onClick={() => {
                                            props.sock.send("/select", {}, JSON.stringify(selected));
                                            setSelected([]);
                                        }}>Confirm</MDBBtn>
                                    </MDBCol>
                                </MDBRow>
                            </MDBModalFooter>
                        </MDBModalContent>
                    </MDBModalDialog>
                </MDBModal>
            </>
        );
    } else {
        return (
            <>
                <MDBRow>
                    <MDBCol>
                        {Object.keys(props.game.players).map((item, index) => <Player key={index} id={item} win={props.game.players[item]}
                                                                         sock={props.sock}
                                                                         started={props.game.started}
                                                                         cezar={item === props.game.cezar}/>)}
                    </MDBCol>
                </MDBRow>
                <MDBRow>
                    <MDBCol className="start">
                        <InfoContext.Consumer>
                            {(context) => {
                                if(context.info.id === props.game.cezar && Object.keys(props.game.players).length > 2)
                                {
                                    return (
                                        <div className="start-cezar">
                                            <form onSubmit={(e) => {e.preventDefault(); props.sock.send("/start", {}, JSON.stringify({time: e.target.time.value, rounds: e.target.rounds.value}))}}>
                                                <MDBRow>
                                                    <MDBCol className="start-elements">
                                                        <MDBInput className="start-elements" label='Time limit (seconds)' name="time" type='number' />
                                                    </MDBCol>
                                                    <MDBCol className="start-elements">
                                                        <MDBInput  label='Round limit' name="rounds" type='number' />
                                                    </MDBCol>
                                                </MDBRow>
                                                <MDBRow>
                                                    <MDBCol className="start-elements">
                                                        <MDBBtn  type="submit">Start ({Object.keys(props.game.players).length} players)</MDBBtn>
                                                    </MDBCol>
                                                </MDBRow>
                                            </form>
                                        </div>
                                    )
                                } else if(Object.keys(props.game.players).length > 2) {
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