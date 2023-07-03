let showPassButtons = document.querySelectorAll(".show-pass-button");

showPassButtons.forEach((button) => {
    button.addEventListener("click", () => {
        let input = button.previousElementSibling.previousElementSibling;
        if (input.type === "password") {
            button.className = "show-pass-button fa fa-eye-slash";
            input.type = "text";
        } else {
            button.className = "show-pass-button fa fa-eye";
            input.type = "password";
        }
    });
});

const currentPasswordInput = document.getElementById("currentPassword");
const newPasswordInput = document.getElementById("newPass");
const repeatPasswordInput = document.getElementById("confirmNewPass");
const message = document.getElementById("message");

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

function checkChangePasswordSubmit() {
    if (currentPasswordInput.value === "") {
        alert("Please enter current password");
        return false;
    } else if (newPasswordInput.value === "") {
        alert("Please enter new password");
        return false;
    } else if (repeatPasswordInput.value === "") {
        alert("Please repeat new password");
        return false;
    } else if (message.innerHTML === "Matched") {
        return true;
    } else if (message.innerHTML === "Not Matched") {
        alert("Password doesn't match");
        return false;
    } else {
        alert("Please enter new password");
        return false;
    }
}