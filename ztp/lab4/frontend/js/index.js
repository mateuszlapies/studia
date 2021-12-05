function load() {
    let login = sessionStorage.getItem("login");
    let password = sessionStorage.getItem("pass");
    if(login && password)
        window.location.href = "/dashboard.html";
    else {
        const form = document.getElementById( "login" );
        form.addEventListener( "submit", (e) => {
            e.preventDefault();
            sessionStorage.setItem("login", e.target.login.value);
            sessionStorage.setItem("pass", e.target.pass.value);
            window.location.href = "/dashboard.html";
        } );
    }
}