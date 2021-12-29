import React, {useState} from "react";
import MessageType from "../enums/MessageTypes.json";
import {
    MDBIcon,
    MDBModal,
    MDBModalBody,
    MDBModalContent,
    MDBModalDialog,
    MDBModalHeader,
    MDBModalTitle
} from "mdb-react-ui-kit";
import {MessageContext} from "../contexts/MessageContext";
import {Api} from "../config/Config";

export default function Message() {
    let [display, setDisplay] = useState("");
    let title = (type) => {
        switch(type) {
            default:
            case MessageType.INFO: return "Info";
            case MessageType.ERROR: return "Error";
            case MessageType.WIN: return "Winner!"
        }
    }
    let icon = (type) => {
        switch(type) {
            default:
            case MessageType.INFO: return <MDBIcon className="me-1" fas icon="info-circle" />;
            case MessageType.ERROR: return <MDBIcon className="me-1" fas icon="exclamation-triangle" />;
            case MessageType.WIN: return <MDBIcon className="me-1" fas icon="trophy" />;
        }
    }

    let content = (type, message) => {
        switch (type) {
            default: {
                setDisplay(message);
                break;
            }
            case MessageType.WIN: {
                fetch(Api + "users/" + message)
                    .then(r => r.json())
                    .then(j => setDisplay(j.message.user));
                break;
            }
        }
    }
    return (
        <MessageContext.Consumer>
            {(message) => {
                if(message.displayed) {
                    content(message.type, message.content)
                    setTimeout(() => message.setMessage({displayed: false}), 5000);
                }
                return (
                    <MDBModal show={message.displayed}>
                        <MDBModalDialog>
                            <MDBModalContent>
                                <MDBModalHeader>
                                    <MDBModalTitle>{icon(message.type)}{title(message.type)}</MDBModalTitle>
                                </MDBModalHeader>
                                <MDBModalBody>
                                    {display}
                                </MDBModalBody>
                            </MDBModalContent>
                        </MDBModalDialog>
                    </MDBModal>
                );
            }}
        </MessageContext.Consumer>
    )
}