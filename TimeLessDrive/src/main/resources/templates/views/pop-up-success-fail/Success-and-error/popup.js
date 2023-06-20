var buttons = document.querySelectorAll(".modal_btns button");
var close_btns = document.querySelectorAll(".close_btn");
var modal_wrapper = document.querySelector(".modal_wrapper");
var s_modal = document.querySelector(".s_modal");
var e_modal = document.querySelector(".e_modal");

buttons.forEach(function (btn) {
  btn.addEventListener("click", function () {
    modal_wrapper.classList.add("active");
    if(btn.classList.contains("success_btn")){
      s_modal.classList.add("active");
      e_modal.classList.remove("active");
    }
    else if(btn.classList.contains("error_btn")){
      s_modal.classList.remove("active");
      e_modal.classList.add("active");
    }
  });
});

close_btns.forEach(function (close) {
  close.addEventListener("click", function () {
    modal_wrapper.classList.remove("active");
    s_modal.classList.remove("active");
    e_modal.classList.remove("active");
  });
});
