let pages = 1;
function updateData(){
    let url = '/check-notification?id=' + id + '&pages=' + pages;
    let notification = $(".Notification_body");
    console.log(pages);
    $.get(url, function (response){
        if(response.length > 0){
            notification.empty();
            for (let i = 0; i < response.length; i++) {
                let h5 = document.createElement("h5");
                h5.innerText = response[i].content;
                let p = document.createElement("p");
                const [hours, minutes, seconds] = response[i].createTime.split(':');
                let date = new Date(response[i].createDate).setHours(hours, minutes, seconds);
                // let now = new Date();
                // const timeDiff = Math.abs(now - date);
                // const hour = Math.floor(timeDiff / (1000 * 60 * 60));
                // const minute = Math.floor((timeDiff / (1000 * 60)) % 60);
                // const second = Math.floor((timeDiff / 1000) % 60);
                // const formattedTime = `${hour}h ${minute}m ${second}s`;
                const formattedTime = formatTime(date);
                p.innerText = formattedTime + " ago";
                let notifyContent = document.createElement("div");
                notifyContent.classList.add("notify_content");
                notifyContent.appendChild(h5);
                notifyContent.appendChild(p);
                let image = document.createElement("div");
                let notifyThumb = document.createElement("div");
                notifyThumb.classList.add("notify_thumb");
                notifyThumb.appendChild(image);
                let outerDiv = document.createElement("div");
                outerDiv.classList.add("single_notify");
                outerDiv.classList.add("d-flex");
                outerDiv.classList.add("align-items-center");
                outerDiv.appendChild(notifyThumb);
                outerDiv.appendChild(notifyContent);
                document.getElementsByClassName("Notification_body")[0].appendChild(outerDiv);
            }
        }else {
            // let notifyContainer = document.getElementById("notify-container");
            // let notifyContent = document.createElement("div");
            // $("#notify-container").empty();
            // notifyContent.classList.add("notify-content");
            // notifyContent.classList.add("text-center");
            // let span = document.createElement("span");
            // span.innerText = "You don't have any notifications";
            // notifyContent.appendChild(span);
            // let arrow = document.createElement("div");
            // arrow.classList.add("notify-content");
            // arrow.classList.add("my-arrow");
            // notifyContainer.appendChild(arrow);
            // notifyContainer.appendChild(notifyContent);
        }
    }, "json");
}

function seeMore(){
    pages += 1;
    updateData();
}

$(document).ready(function (){
    // $(".fa-bell").click(function (){
    //     let e = document.getElementById("notify-container");
    //     if (e.classList.contains("my-style")){
    //         e.classList.remove("my-style");
    //     }else {
    //         e.classList.add("my-style");
    //     }
    // });
    $("#see-more").click(function(){
        seeMore();
    });

    updateData();

    setInterval(function (){
        updateData();
    }, 10000);

});



function formatTime(time){
    let result = '';
    let now = new Date();
    const timeDiff = Math.abs(now - time);
    const day = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    const hour = Math.floor(timeDiff / (1000 * 60 * 60) % 24);
    const minute = Math.floor((timeDiff / (1000 * 60)) % 60);
    const second = Math.floor((timeDiff / 1000) % 60);
    if (day > 0){
        if (day < 2){
            result += `${day} day`;
        }else {
            result += `${day} days`;
        }
    }
    if (hour > 0){
        if (hour < 2){
            result += ` ${hour} hour`;
        }else {
            result += ` ${hour} hours`;
        }
    }
    if (minute > 0){
        if (minute < 2){
            result += ` ${minute} minute`;
        }else {
            result += ` ${minute} minutes`;
        }
    }else {
        if (day === 0 && hour === 0){
            result += `${minute} minutes`;
        }
    }

    return result;
}