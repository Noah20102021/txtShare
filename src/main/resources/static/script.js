window.addEventListener("DOMContentLoaded", (event) => {

});
let host = "";
fetch("/config")
    .then(response => response.text())
    .then(data => {
        host = data;
        document.getElementById("psCommand").innerText = `irm "${host}/setup" | iex`;
        document.getElementById("bashCommand").innerText = `. <(curl -s ${host}/setup)`;
    });




async function callApi(url, displayElementId) {
    const displayElement = document.getElementById(displayElementId);
    displayElement.innerText = "Loading...";
    displayElement.style.color = "gray";

    try {
        const response = await fetch(url);
        const data = await response.text();

        displayElement.innerText = data;


        if (data.toLowerCase().includes("failed")) {
            displayElement.style.color = "red";
        } else {
            displayElement.style.color = "green";
        }
    } catch (error) {
        displayElement.innerText = "Network error: can't reach server!";
        displayElement.style.color = "red";
    }
}


document.getElementById("pushForm").addEventListener("submit", (event) => {
    event.preventDefault();
    const key = encodeURIComponent(document.getElementById("pushKey").value);
    const text = encodeURIComponent(document.getElementById("pushText").value);
    const passField = document.getElementById("pushPass");

    let url = `/push/${key}?value=${text}`;
    if (passField && passField.value) {
        url += `&password=${encodeURIComponent(passField.value)}`;
    }

    callApi(url, "pushResult");
});


document.getElementById("getForm").addEventListener("submit", (event) => {
    event.preventDefault();
    const key = encodeURIComponent(document.getElementById("getKey").value);
    const passField = document.getElementById("getPass");

    let url = `/get/${key}`;
    if (passField && passField.value) {
        url += `?password=${encodeURIComponent(passField.value)}`;
    }

    callApi(url, "getResult");
});