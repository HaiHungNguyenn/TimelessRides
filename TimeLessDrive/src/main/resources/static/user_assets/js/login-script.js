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

// start function for reset password
let x;
const duration = 60;
let timeRemain = duration;
const popup1 = ".pop-ups .pop-up:nth-of-type(1)";
const popup1_esc = ".pop-ups .pop-up:nth-of-type(1) .esc-button";
const popup2_esc = ".pop-ups .pop-up:nth-of-type(2) .esc-button";
const popup2 = ".pop-ups .pop-up-active:nth-of-type(2)";

document.querySelector("#forgot-password-button").addEventListener("click", function () {
    document.querySelector(popup1).className = "pop-up-active";
    document.querySelector(".all-page").classList.add("blur");
    x = setInterval(countDown, 1000);
    var  linkToController = "/hello";
    window.location.href=linkToController;
});

//add event

document.querySelector(popup1_esc).addEventListener("click", function () {
    document.querySelector(popup1).className = "pop-up";
    document.querySelector(".all-page").classList.remove("blur");
    document.getElementById("countdown-reset-password").innerHTML = duration;
    clearInterval(x);
    timeRemain = duration;
});

document.querySelector(popup2_esc).addEventListener("click", function () {
    document.querySelector(popup2).className = "pop-up";
    document.querySelector(".all-page").classList.remove("blur");
});

function countDown() {
    if (timeRemain < 0) {
        clearInterval(x);
        timeRemain = duration;
        showMessage();
    }
    else document.getElementById("countdown-reset-password").innerHTML = timeRemain--;
}

function showMessage() {
    Swal.fire({
        icon: 'success',
        title: 'Code Resent Successfully',
        text: 'A new code has been resent',
        confirmButtonText: 'Ok',
    }).then((result) => {
        if (result.isConfirmed) {
            x = setInterval(countDown, 1000);
        }
    })
}

document.querySelector("#resent-email").addEventListener("click",  function () {
    clearInterval(x);
    timeRemain = duration;
    showMessage();
});