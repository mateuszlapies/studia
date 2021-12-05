function request(type, target, payload) {
    return new Promise((resolve, reject) => {

        let xhr = new XMLHttpRequest();
        xhr.open(type, target);

        xhr.setRequestHeader("Accept", "application/json");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", "Basic " + btoa(data()));
        xhr.setRequestHeader("Access-Control-Allow-Origin", "true");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if(xhr.status === 200 && xhr.response !== "")
                    resolve(xhr.response);
                else
                    reject();
            }
        }

        xhr.send(payload);
    });
}

function data() {
    return sessionStorage.getItem("login") + ":" +  sessionStorage.getItem("pass");
}