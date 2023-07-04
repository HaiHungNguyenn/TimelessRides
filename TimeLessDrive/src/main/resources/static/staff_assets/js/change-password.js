let form = document.getElementById("change-password-form");
let btn = document.getElementById("change-password-btn");
let escBtn = document.getElementsByClassName("esc-button")[0];
let dialog = document.getElementById("dialog");
const currentPasswordInput = document.getElementById("currenPassword");
const newPasswordInput = document.getElementById("password");
const repeatPasswordInput = document.getElementById("confirmPassword");
const message = document.getElementById("message");

btn.addEventListener("click", () => {
    form.classList.add("active");
    dialog.showModal();
});

escBtn.addEventListener("click", () => {
    form.classList.remove("active");
    dialog.close();
});

function checkRepeatPassword() {
    if (newPasswordInput.value === '' && repeatPasswordInput.value === ''){
        message.innerHTML = '';
    } else if (newPasswordInput.value === repeatPasswordInput.value) {
        message.innerHTML = "Matched"
        message.style.color = "green";
    } else {
        message.innerHTML = "Not Matched";
        message.style.color = "red";
    }
}

newPasswordInput.addEventListener("keyup", () => checkRepeatPassword());
newPasswordInput.addEventListener("keydown", () => checkRepeatPassword());
repeatPasswordInput.addEventListener("keyup", () => checkRepeatPassword());
repeatPasswordInput.addEventListener("keydown", () => checkRepeatPassword());

