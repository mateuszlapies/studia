import {useState} from "react";

import {
    MDBBtn,
    MDBInput,
    MDBTabs,
    MDBTabsContent,
    MDBTabsItem,
    MDBTabsLink,
    MDBTabsPane
} from 'mdb-react-ui-kit';
import {UserContext} from "../contexts/UserContext";
import {InfoContext} from "../contexts/InfoContext";
import {MessageContext} from "../contexts/MessageContext";

import "../styles/Login.css";
import MessageTypes from "../enums/MessageTypes.json";

export default function Login(props) {
    let [isLogin, setIsLogin] = useState(true);

    let onLogin = (e, user, info, message) => {
        e.preventDefault();
        let d = {user: e.target.user.value, pass: e.target.pass.value}
        fetch("http://localhost:8080/me", {
            headers: {
                "Authorization": "Basic " + btoa(d.user + ':' + d.pass)
            }
        })
            .then(r => r.ok ? r.json() : null)
            .then(j => {
                info({info: j.message});
                user({user: d});
            })
            .catch((e) => {
                message({type: MessageTypes.ERROR, content: "Login failed: " + JSON.stringify(e), displayed: true})
                document.getElementById('login').reset();
            })
    }

    let onRegister = (e, message) => {
        e.preventDefault();
        let d = {user: e.target.user.value, pass: e.target.pass.value}
        fetch("http://localhost:8080/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(d)
        })
            .then((r) => {
                if (r.ok) {
                    setIsLogin(true);
                    message({type: MessageTypes.INFO, content: "Registration successful"});
                }
            })
            .catch(() => message({type: MessageTypes.ERROR, content: "Registration failed: " + JSON.stringify(e), displayed: true}))
            .finally(() => document.getElementById('register').reset())
    }

    return (
        <UserContext.Consumer>
            {(user) => {
                if(!user.user) {
                    return (
                        <div className="login-container">
                            <div className="login">
                                <MDBTabs className='mb-3'>
                                    <MDBTabsItem>
                                        <MDBTabsLink onClick={() => setIsLogin(!isLogin)} active={isLogin}>
                                            Login
                                        </MDBTabsLink>
                                    </MDBTabsItem>
                                    <MDBTabsItem>
                                        <MDBTabsLink onClick={() => setIsLogin(!isLogin)} active={!isLogin}>
                                            Register
                                        </MDBTabsLink>
                                    </MDBTabsItem>
                                </MDBTabs>

                                <MDBTabsContent>
                                    <MessageContext.Consumer>
                                        {(message) => (
                                            <InfoContext.Consumer>
                                                {(info) => (

                                                    <>
                                                        <MDBTabsPane show={isLogin}>
                                                            <h3>Login</h3>
                                                            <form id="login" onSubmit={(e) => onLogin(e, user.setUser, info.setInfo, message.setMessage)}>
                                                                <MDBInput className="login-margin" label='User' name='user' type='text' required />
                                                                <MDBInput className="login-margin" label='Password'  name='pass' type='password' required />
                                                                <MDBBtn className="login-margin-submit" type='submit'>Login</MDBBtn>
                                                            </form>
                                                        </MDBTabsPane>
                                                        <MDBTabsPane show={!isLogin}>
                                                            <h3>Register</h3>
                                                            <form id="register" onSubmit={(e) => onRegister(e, message.setMessage)}>
                                                                <MDBInput className="login-margin" label='User' name='user' type='text' required />
                                                                <MDBInput className="login-margin" label='Password'  name='pass' type='password' required />
                                                                <MDBBtn className="login-margin-submit" type='submit'>Register</MDBBtn>
                                                            </form>
                                                        </MDBTabsPane>
                                                    </>

                                                )}
                                            </InfoContext.Consumer>
                                        )}
                                    </MessageContext.Consumer>
                                </MDBTabsContent>
                            </div>
                        </div>
                    )
                }
            }}
        </UserContext.Consumer>
    )
}