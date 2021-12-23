import React from "react";
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

export default function Message() {
    let title = (type) => {
        switch(type) {
            default:
            case MessageType.INFO: return "Info";
            case MessageType.ERROR: return "Error";
        }
    }
    let icon = (type) => {
        switch(type) {
            default:
            case MessageType.INFO: return <MDBIcon className="me-1" fas icon="info-circle" />;
            case MessageType.ERROR: return <MDBIcon className="me-1" fas icon="exclamation-triangle" />;
        }
    }
    return (
        <MessageContext.Consumer>
            {(message) => {
                if(message.displayed)
                    setTimeout(() => message.setMessage({displayed: false}), 2000);
                return (
                    <MDBModal show={message.displayed}>
                        <MDBModalDialog>
                            <MDBModalContent>
                                <MDBModalHeader>
                                    <MDBModalTitle>{icon(message.type)}{title(message.type)}</MDBModalTitle>
                                </MDBModalHeader>
                                <MDBModalBody>
                                    {message.content}
                                </MDBModalBody>
                            </MDBModalContent>
                        </MDBModalDialog>
                    </MDBModal>
                );
            }}
        </MessageContext.Consumer>
    )
}