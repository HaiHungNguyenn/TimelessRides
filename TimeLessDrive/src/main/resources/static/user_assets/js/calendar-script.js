const date = new Date();

const renderCalendar = () => {
    date.setDate(1);

    const monthDays = document.querySelector(".days");
    monthDays.innerHTML = "";

    const lastDay = new Date(
        date.getFullYear(),
        date.getMonth() + 1,
        0
    ).getDate();

    const prevLastDay = new Date(
        date.getFullYear(),
        date.getMonth(),
        0
    ).getDate();

    const firstDayIndex = date.getDay();

    const lastDayIndex = new Date(
        date.getFullYear(),
        date.getMonth() + 1,
        0
    ).getDay();

    const nextDays = 7 - lastDayIndex - 1;

    const months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
    ];

    document.querySelector(".date h1").innerHTML = months[date.getMonth()];

    document.querySelector(".date p").innerHTML = new Date().toDateString();

    let days = "";
    //add prev months days
    for (let x = firstDayIndex; x > 0; x--) {
        days += `<div class="prev-date">${prevLastDay - x + 1}</div>`;
    }

    for (let i = 1; i <= lastDay; i++) {
        days += `<div data="${new Date(date.getFullYear(), date.getMonth(), i)}">${i}</div>`;
    }

    for (let j = 1; j <= nextDays; j++) {
        days += `<div class="next-date">${j}</div>`;
    }
    monthDays.innerHTML = days;
};

const renderSlot = () => {
    const slotInsert = document.getElementById("slots");
    slotInsert.innerHTML = "";
    const slots = [
        "7",
        "8",
        "9",
        "10",
        "11",
        "12",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
    ];
    if(document.getElementsByClassName("today")[0]==null) return;
    for (let slot of slots) {
        slotInsert.innerHTML += `<input type="radio" name="slot" id="${slot}" value="${slot}">
                              <label for="${slot}">${slot+":00"}</label>`
    }
}

const addDatetoInput = (active) => {
    document.querySelector(".date p").innerHTML = new Date(active.getAttribute("data")).toDateString();
    const date = new Date(active.getAttribute("data"));
    const inputs = document.querySelectorAll(".slots input");
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].value = new Date(date.getFullYear(), date.getMonth(), date.getDate(), inputs[i].id);
    }
}

const switchDate = () => {
    const days = document.querySelectorAll(".days div:not(.next-date):not(.prev-date)");
    for (let i = 0; i < days.length; i++) {
        days[i].addEventListener("click", function () {
            const active = document.getElementsByClassName("today")[0];
            if(active)
                active.classList.toggle("today");
            days[i].classList.toggle("today");
            renderSlot();
            addDatetoInput(this);
        });
    }
}


document.querySelector(".prev").addEventListener("click", () => {
    date.setMonth(date.getMonth() - 1);
    renderCalendar();
    renderSlot();
    switchDate();
});

document.querySelector(".next").addEventListener("click", () => {
    date.setMonth(date.getMonth() + 1);
    renderCalendar();
    renderSlot();
    switchDate();
});


renderCalendar();
renderSlot();
switchDate();
addDatetoInput(document.getElementsByClassName("today")[0]);