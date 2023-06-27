document.querySelector("#change-password-button").addEventListener("click",function(){
    if(document.getElementsByClassName("change-password active")[0] == null)
        document.getElementsByClassName("change-password")[0].classList.add("active");
    else
        document.getElementsByClassName("change-password active")[0].classList.remove("active");
});

const defaultAvatar = "https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg";

window.addEventListener("load",() => {
    const preview = document.getElementById("avatar-wrapper");
    if(avatar == defaultAvatar) return;
    else{
        preview.style.setProperty('--avatar-image', "url('"+avatar+"')");
    }
})

document.getElementById("avatar").addEventListener("change",(evt) => {
    const avatar = evt.target.files[0];
    const preview = document.getElementById("avatar-wrapper");
    const reader = new FileReader();
    reader.onloadend = function () {
        preview.style.setProperty('--avatar-image', "url('"+reader.result+"')");
    }
    reader.readAsDataURL(avatar);
})




// function updateData(){
//     let url = '/check-notification?id=' + id;
//     let notification = $(".Notification_body");
//     $.get(url, function (response){
//         if(response.length > 0){
//             notification.empty();
//             for (let i = 0; i < response.length; i++) {
//                 let h5 = document.createElement("h5");
//                 h5.innerText = response[i].content;
//                 let p = document.createElement("p");
//                 const [hours, minutes, seconds] = response[i].createTime.split(':');
//                 let date = new Date(response[i].createDate).setHours(hours, minutes, seconds);
//                 let now = new Date();
//                 const timeDiff = Math.abs(now - date);
//                 const hour = Math.floor(timeDiff / (1000 * 60 * 60));
//                 const minute = Math.floor((timeDiff / (1000 * 60)) % 60);
//                 const second = Math.floor((timeDiff / 1000) % 60);
//                 const formattedTime = `${hour}h ${minute}m ${second}s`;
//                 p.innerText = formattedTime + " ago";
//                 let notifyContent = document.createElement("div");
//                 notifyContent.classList.add("notify_content");
//                 notifyContent.appendChild(h5);
//                 notifyContent.appendChild(p);
//                 let image = document.createElement("img");
//                 image.src = "../../admin_assets/img/staf/2.png";
//                 image.alt = "staff_image";
//                 let notifyThumb = document.createElement("div");
//                 notifyThumb.classList.add("notify_thumb");
//                 notifyThumb.appendChild(image);
//                 let outerDiv = document.createElement("div");
//                 outerDiv.classList.add("single_notify");
//                 outerDiv.classList.add("d-flex");
//                 outerDiv.classList.add("align-items-center");
//                 outerDiv.appendChild(notifyThumb);
//                 outerDiv.appendChild(notifyContent);
//                 document.getElementsByClassName("Notification_body")[0].appendChild(outerDiv);
//             }
//         }else {
//             // let notifyContainer = document.getElementById("notify-container");
//             // let notifyContent = document.createElement("div");
//             // $("#notify-container").empty();
//             // notifyContent.classList.add("notify-content");
//             // notifyContent.classList.add("text-center");
//             // let span = document.createElement("span");
//             // span.innerText = "You don't have any notifications";
//             // notifyContent.appendChild(span);
//             // let arrow = document.createElement("div");
//             // arrow.classList.add("notify-content");
//             // arrow.classList.add("my-arrow");
//             // notifyContainer.appendChild(arrow);
//             // notifyContainer.appendChild(notifyContent);
//         }
//     }, "json");
// }
//
// $(document).ready(function (){
//     // $(".fa-bell").click(function (){
//     //     let e = document.getElementById("notify-container");
//     //     if (e.classList.contains("my-style")){
//     //         e.classList.remove("my-style");
//     //     }else {
//     //         e.classList.add("my-style");
//     //     }
//     // });
//     updateData();
//     setInterval(function (){
//         updateData();
//     }, 10000);
// });