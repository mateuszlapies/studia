import {Routes, Route, BrowserRouter} from 'react-router-dom';

import Authorized from "./Authorized";

function Logout() {
    sessionStorage.clear();
    document.location.href = "/";
}

export default function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Authorized/>} />
                <Route exact path="/logout" element={<Logout />} />
            </Routes>
        </BrowserRouter>

    )
}