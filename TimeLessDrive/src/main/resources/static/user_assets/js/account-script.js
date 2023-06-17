document.querySelector("#change-password-button").addEventListener("click",function(){
    if(document.getElementsByClassName("change-password active")[0] == null)
        document.getElementsByClassName("change-password")[0].classList.add("active");
    else
        document.getElementsByClassName("change-password active")[0].classList.remove("active");
});