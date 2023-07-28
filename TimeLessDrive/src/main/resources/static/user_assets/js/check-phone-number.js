$('#phone-number').keypress(function (e) {
    var key = String.fromCharCode(e.which);
    var pattern=/^[0-9]{1,10}(,[0-9]{0,2})?$/;

    // test this
    var txt = $(this).val() + key;


    if (!pattern.test(txt)) {
        e.preventDefault();
    }

});
$('#phone-number').keyup(() => checkPhone());
$('#phone-number').keydown(() => checkPhone());


function checkPhone(){
    var input = document.getElementById("phone-number");
    var phone = document.getElementById("phone-comment");
    if(input.value.length != 10)
        phone.innerHTML = "Phone number should have 10 number";
    else
        phone.innerHTML = "";
}

function checkSubmit(){
    let slot =document.querySelector("#booking-form input[name='slot']:checked")
    var phone = document.getElementById("phone-number");
    if(phone.value.length != 10){
        alert("Phone number should have 10 number")
        return false
    }
    else if (slot==null){
        alert("Slot can not be null")
        return false
    }
    else{
        return true
    }
}

let phone_num = document.getElementById("phone-number");
let phone_value = phone_num.getAttribute("value");

phone_num.setAttribute("value", toPhoneNum(phone_value));

function toPhoneNum(phoneRaw){
    let start = 0;
    let end = phoneRaw.indexOf('-');
    let phoneNum;

    phoneNum = phoneRaw.substring(start, end).concat(
        phoneRaw.substring(start = end + 1, end = phoneRaw.indexOf('-', start))
    ).concat(
        phoneRaw.substring(end + 1, phoneRaw.length)
    )

    return phoneNum;
}