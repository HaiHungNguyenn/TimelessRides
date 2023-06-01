const loginPill = document.getElementById("tab-login");
const login = document.getElementsByClassName("tab-pane fade")[0];
const registerPill = document.getElementById("tab-register");
const register = document.getElementsByClassName("tab-pane fade")[1];

document.getElementById("tab-login").addEventListener("click", switchLogin);
document.getElementById("tab-register").addEventListener("click", switchRegister);

function switchRegister(){
    loginPill.className = "nav-link";
    registerPill.className = "nav-link active";
    login.className = "tab-pane fade";
    register.className = "tab-pane fade show active";
}

function switchLogin(){
    registerPill.className = "nav-link";
    loginPill.className = "nav-link active";
    register.className = "tab-pane fade";
    login.className = "tab-pane fade show active";
}

var check = function() {
    if(document.getElementById('registerPassword').value == "" &&
        document.getElementById('registerRepeatPassword').value == ""){
        document.getElementById('message').innerHTML = '';
    }
    if (document.getElementById('registerPassword').value ==
        document.getElementById('registerRepeatPassword').value) {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'Matched';
    }
    else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Not Matched';
    }
}