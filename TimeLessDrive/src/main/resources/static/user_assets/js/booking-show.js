document.querySelector("#show-booking").addEventListener("click",function(){
    document.querySelector(".pop-up").className="pop-up-active";
    document.querySelector(".all-page").classList.add("blur");
});

document.querySelector(".pop-up .esc-button").addEventListener("click",function(){
    document.querySelector(".pop-up-active").className="pop-up";
    document.querySelector(".all-page").classList.remove("blur");
});

document.querySelector('#nav-1').addEventListener("click",function (){
    document.querySelector('#nav-2').className = "nav-link";
    this.className = "nav-link active";
    document.querySelector('#tabs-1').className = "tab-pane active";
    document.querySelector('#tabs-2').className = "tab-pane";
});

document.querySelector('#nav-2').addEventListener("click",function (){
    document.querySelector('#nav-1').className = "nav-link";
    this.className = "nav-link active";
    document.querySelector('#tabs-1').className = "tab-pane";
    document.querySelector('#tabs-2').className = "tab-pane active";
});
