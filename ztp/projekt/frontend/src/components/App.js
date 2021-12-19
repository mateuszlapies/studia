import {useState} from "react";

import '../styles/App.css';

import {UserContext} from '../contexts/UserContext';
import {InfoContext} from "../contexts/InfoContext";

import Header from "./Header";
import AppRouter from "./AppRouter";
import {MessageContext} from "../contexts/MessageContext";
import MessageType from "../enums/MessageTypes.json";
import Message from "./Message";

function App() {
  let [user, updateUser] = useState({
    user: sessionStorage.getItem("userpass") ? JSON.parse(sessionStorage.getItem("userpass")) : undefined,
    setUser: (d) => {
        sessionStorage.setItem("userpass", JSON.stringify(d));
        updateUser(d);
    }
  });
  let [info, updateInfo] = useState({
    info: sessionStorage.getItem("info") ? JSON.parse(sessionStorage.getItem("info")) : undefined,
    setInfo: (d) => {
        sessionStorage.setItem("info", JSON.stringify(d));
        updateInfo(d)
    }
  });
  let [message, updateMessage] = useState({
    message: {type: MessageType.INFO, content: "", displayed: true},
    setMessage: (d) => updateMessage(d)
  });
  return (
      <MessageContext.Provider value={message}>
          <InfoContext.Provider value={info}>
              <UserContext.Provider value={user}>
                  <Header/>
                  <AppRouter user={user}/>
                  <Message message={message}/>
              </UserContext.Provider>
          </InfoContext.Provider>
      </MessageContext.Provider>
  );
}

export default App;
