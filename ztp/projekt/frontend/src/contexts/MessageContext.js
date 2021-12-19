import React from "react";
import MessageType from "../enums/MessageTypes.json";
export const MessageContext = React.createContext({message: {type: MessageType.INFO, content: "", displayed: true}, display: (d) => {}})