import Body from "./Body";
import Login from "./Login";
import Game from "./game/Game";
import {UserContext} from "../contexts/UserContext";

export default function Authorized() {
    return (
        <Body>
            <UserContext.Consumer>
                {(context) => {
                    if(context.user) {
                        return <Game user={context.user} />
                    } else {
                        return <Login/>;
                    }
                }}
            </UserContext.Consumer>
        </Body>
    )
}