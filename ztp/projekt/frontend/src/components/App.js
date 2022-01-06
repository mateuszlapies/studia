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
    user: window.sessionStorage.getItem("userpass") ? JSON.parse(window.sessionStorage.getItem("userpass")) : undefined,
    setUser: (d) => {
        window.sessionStorage.setItem("userpass", JSON.stringify(d.user));
        updateUser(d);
    }
  });
  let [info, updateInfo] = useState({
    info: window.sessionStorage.getItem("info") ? JSON.parse(window.sessionStorage.getItem("info")) : undefined,
    setInfo: (d) => {
        window.sessionStorage.setItem("info", JSON.stringify(d.info));
        updateInfo(d)
    }
  });
  let [message, updateMessage] = useState({
    message: {type: MessageType.INFO, content: "", displayed: false},
    setMessage: (d) => {
        let obj = {message: d, setMessage: message.setMessage}
        updateMessage(obj);
    }
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
