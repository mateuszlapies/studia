import {
    MDBBadge,
    MDBCollapse,
    MDBContainer,
    MDBIcon,
    MDBNavbar, MDBNavbarBrand,
    MDBNavbarItem,
    MDBNavbarLink,
    MDBNavbarNav,
    MDBNavbarToggler
} from "mdb-react-ui-kit";
import {UserContext} from "../contexts/UserContext";
import {useState} from "react";
import {InfoContext} from "../contexts/InfoContext";

export default function Header() {
    let [show, setShow] = useState(true);
    return (
        <MDBNavbar expand='lg' light bgColor='light'>
            <MDBContainer fluid>
                <MDBNavbarBrand href='#'>Cards Against Humanity</MDBNavbarBrand>

                <MDBNavbarToggler
                    aria-controls='navbarSupportedContent'
                    aria-expanded='false'
                    aria-label='Toggle navigation'
                    onClick={() => setShow(!show)}
                >
                    <MDBIcon icon='bars' fas />
                </MDBNavbarToggler>

                <MDBCollapse navbar show={show}>
                    <MDBNavbarNav className='mr-auto mb-2 mb-lg-0'>
                        <InfoContext.Consumer>
                            {(context) => {
                                if(context.info) {
                                    return (
                                        <MDBNavbarItem>
                                            <MDBNavbarLink>
                                                {context.info.user}
                                                <MDBIcon fas icon='crown' className="ms-1" size='sm' color="gold" />
                                                <MDBBadge color='danger' notification pill>
                                                    {context.info.history.length}
                                                </MDBBadge>
                                            </MDBNavbarLink>
                                        </MDBNavbarItem>
                                    )
                                }
                            }}
                        </InfoContext.Consumer>
                        <UserContext.Consumer>
                            {
                                (context) => {
                                    if(context.user)
                                        return (
                                            <MDBNavbarItem active={window.location.pathname === "/logout"}>
                                                <MDBNavbarLink href='logout'>Logout</MDBNavbarLink>
                                            </MDBNavbarItem>
                                        )
                                    return (
                                        <MDBNavbarItem active={window.location.pathname === "/login"}>
                                            <MDBNavbarLink href='/'>Login</MDBNavbarLink>
                                        </MDBNavbarItem>
                                    )
                                }
                            }
                        </UserContext.Consumer>
                    </MDBNavbarNav>
                </MDBCollapse>
            </MDBContainer>
        </MDBNavbar>
    );
}