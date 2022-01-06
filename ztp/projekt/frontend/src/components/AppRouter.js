import {Routes, Route, BrowserRouter} from 'react-router-dom';

import Authorized from "./Authorized";
import {SocketClose} from "./factory/SocketFactory";
import {Api} from "../config/Config";

function Logout() {
    SocketClose();
    window.sessionStorage.clear();
    document.location.href = "/";
}

function Restart() {
    SocketClose();
    fetch(Api + "restart")
        .then(() => document.location.href = "/");
}

export default function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Authorized/>} />
                <Route exact path="/logout" element={<Logout />} />
                <Route exact path="/restart" element={<Restart />} />
            </Routes>
        </BrowserRouter>

    )
}