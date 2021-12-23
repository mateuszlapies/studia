import {MDBContainer, MDBSpinner} from "mdb-react-ui-kit";

export default function Loading() {
    return (
        <MDBContainer>
            <div className="loading-container">
                <MDBSpinner role='status'>
                    <span className='visually-hidden'>Loading...</span>
                </MDBSpinner>
            </div>
        </MDBContainer>
    );
}