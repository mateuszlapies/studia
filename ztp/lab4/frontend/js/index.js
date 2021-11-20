function load() {
    console.log("load");
    let login = sessionStorage.getItem("login");
    let password = sessionStorage.getItem("pass");
    if(login && password)
        window.location.href = "/dashboard";
}

function submit(e) {
    console.log(e);
    e.preventDefault();
}