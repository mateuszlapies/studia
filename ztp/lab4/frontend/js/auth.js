let books = [];
let admin;

function table() {
    let table = document.getElementById("books");
    table.children[Symbol.iterator]((e) => table.removeChild(e));
    let string = "<tr>" +
        "<th>Title</th>" +
        "<th>Author</th>" +
        "<th>Year</th>" +
    "</tr>";
    console.log(books);
    string += books.map((item) =>
        "<tr>" +
        "<td>" + item.title + "</td>" +
        "<td>" + item.author + "</td>" +
        "<td>" + item.year + "</td>" +
        (admin ? "<td><button name='remove' id='" + item.id + "'>-</button></td>" : "") +
        "</tr>"
    ).join("");
    table.innerHTML = string;
    if(admin) {
        setupEvents();
    }
}

function setupEvents() {
    document.getElementsByName("remove").forEach((el, index) => el.addEventListener("click", (e) => {
        request("DELETE", "http://localhost:8080/dashboard/" + e.target.id)
            .then(() => {books.splice(index, 1); table()})
    }))
}

function add() {
    if(admin === true) {
        document.getElementById("add").addEventListener("submit", (e) => {
            e.preventDefault();
            if(e.target.title.value !== "" && e.target.author.value !== "" && e.target.year.value !== "") {
                request("POST", "http://localhost:8080/dashboard", JSON.stringify({title: e.target.title.value, author: e.target.author.value, year: e.target.year.value}))
                    .then(r => JSON.parse(r))
                    .then(j => {books.push(j.books[0]); table()})
            }
        });
    } else if (admin === false) {
        document.getElementById("add").remove()
    }
}

function dashboard() {
    request("GET", "http://localhost:8080/dashboard")
        .then(r => JSON.parse(r))
        .then(j => {books = j.books; table()});
    isAdmin();
    document.getElementById("logout").addEventListener("click", () => logout());
}

function isAdmin() {
    request("GET", "http://localhost:8080/verify")
        .then(() => {
            admin = true;
            add();
            table();
        })
        .catch(() => {
            admin = false;
            add();
            table();
        });
}

function logout() {
    sessionStorage.clear();
    window.location.href = '/';
}