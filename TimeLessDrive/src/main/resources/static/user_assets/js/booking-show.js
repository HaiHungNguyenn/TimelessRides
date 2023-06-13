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

$('#phone-number').keypress(function (e) {
    var key = String.fromCharCode(e.which);
    var pattern=/^[0-9]{1,11}(,[0-9]{0,2})?$/;

    // test this
    var txt = $(this).val() + key;

    if (!pattern.test(txt)) {
        e.preventDefault();
    }
});
