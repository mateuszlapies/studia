import {MDBContainer, MDBIcon} from "mdb-react-ui-kit";

export default function Offline() {
    return (
        <MDBContainer>
            <div className="offline-container">
                <div className="offline-icon"><MDBIcon fas icon="flushed" size="5x" /></div>
                <div><h3>Service is unfortunately currently offline</h3></div>
            </div>
        </MDBContainer>
    );
}