import {MDBContainer} from "mdb-react-ui-kit";

export default function Body(props) {
    return (
        <MDBContainer>
            {props.children}
        </MDBContainer>
    );
}