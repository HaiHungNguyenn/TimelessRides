document.querySelector("#show-booking").addEventListener("click",function(){
    document.querySelector(".pop-up").className="pop-up-active";
    document.querySelector(".all-page").classList.add("blur");
});

document.querySelector(".pop-up .esc-button").addEventListener("click",function(){
    document.querySelector(".pop-up-active").className="pop-up";
    document.querySelector(".all-page").classList.remove("blur");
});

