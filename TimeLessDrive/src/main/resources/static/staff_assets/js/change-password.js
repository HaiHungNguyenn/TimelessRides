let form = document.getElementById("change-password-form");
let btn = document.getElementById("change-password-btn");
let escBtn = document.getElementsByClassName("esc-button")[0];
let dialog = document.getElementById("dialog");

btn.addEventListener("click", () => {
    form.classList.add("active");
    dialog.showModal();
});

escBtn.addEventListener("click", () => {
    form.classList.remove("active");
    dialog.close();
});

