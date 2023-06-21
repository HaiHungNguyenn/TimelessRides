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
        if(response.length > 0){
            notification.empty();
            let arrow = document.createElement("div");
            let notifyContainer = document.getElementById("notify-container");
            arrow.classList.add("notify-content");
            arrow.classList.add("my-arrow");
            notifyContainer.appendChild(arrow);
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
                notifyContainer.appendChild(notifyContent);
            }
        }else {
            let notifyContainer = document.getElementById("notify-container");
            let notifyContent = document.createElement("div");
            $("#notify-container").empty();
            notifyContent.classList.add("notify-content");
            notifyContent.classList.add("text-center");
            let span = document.createElement("span");
            span.innerText = "You don't have any notifications";
            notifyContent.appendChild(span);
            let arrow = document.createElement("div");
            arrow.classList.add("notify-content");
            arrow.classList.add("my-arrow");
            notifyContainer.appendChild(arrow);
            notifyContainer.appendChild(notifyContent);
        }
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