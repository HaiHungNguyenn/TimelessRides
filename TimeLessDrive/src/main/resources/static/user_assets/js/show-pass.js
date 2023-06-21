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