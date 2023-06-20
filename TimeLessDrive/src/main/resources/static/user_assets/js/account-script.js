document.querySelector("#change-password-button").addEventListener("click",function(){
    if(document.getElementsByClassName("change-password active")[0] == null)
        document.getElementsByClassName("change-password")[0].classList.add("active");
    else
        document.getElementsByClassName("change-password active")[0].classList.remove("active");
});

function updateData(){
    let url = '/check-notification?id=' + id;
    let notification = $("#notify-container");
    $.get(url, function (response){
        // if(response.length > notification.children().length){
            notification.empty();
            for (let i = 0; i < response.length; i++) {
                let notifyContent = document.createElement("div");
                notifyContent.classList.add("notify-content");
                let span = document.createElement("span");
                span.innerText = response[i].content;
                let time = document.createElement("div");
                time.classList.add("content-end");
                const [hours, minutes, seconds] = response[i].createTime.split(':');
                let date = new Date(response[i].createDate).setHours(hours, minutes, seconds);
                let now = new Date();
                const timeDiff = Math.abs(now - date);
                const hour = Math.floor(timeDiff / (1000 * 60 * 60));
                const minute = Math.floor((timeDiff / (1000 * 60)) % 60);
                const second = Math.floor((timeDiff / 1000) % 60);
                const formattedTime = `${hour}h ${minute}m ${second}s`;
                time.innerText = formattedTime + " ago";
                notifyContent.appendChild(span);
                notifyContent.appendChild(time);
                document.getElementById("notify-container").appendChild(notifyContent);
            }
        // }
    }, "json");
}

$(document).ready(function (){
    $(".fa-bell").click(function (){
        let e = document.getElementById("notify-container");
        if (e.classList.contains("my-style")){
            e.classList.remove("my-style");
        }else {
            e.classList.add("my-style");
        }
    });
    updateData();
    setInterval(function (){
        updateData();
    }, 10000);
});