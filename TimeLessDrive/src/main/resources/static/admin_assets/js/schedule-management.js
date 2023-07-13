function setToMonday(date) {
    var day = date.getDay() || 7;
    if (day !== 1)
        date.setHours(-24 * (day - 1));
    else date.setHours(0);
    return date;
}

function setToSunday(date) {
    var day = 7 - (date.getDay() || 7);
    if (day !== 0)
        date.setHours(24 * day);
    return date;
}

//format: date/month
function getDatesAndMonthsOfWeek(monday, sunday) {
    const days = [];
    let date
    for (let i = new Date(monday); i <= new Date(sunday); i.setDate(i.getDate() + 1)) {
        date = new Date(i)
        days.push(date.getDate() + "/" + date.getMonth(date.setMonth(date.getMonth() + 1)))
    }
    return days
}

//format: year-month-date
function getDatesOfWeek(monday, sunday) {
    const dates = [];
    for (let i = new Date(monday); i <= new Date(sunday); i.setDate(i.getDate() + 1)) {
        let [date] = new Date(i).toISOString().split('T')
        dates.push(date)
    }
    return dates
}


//get url param
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
let date1 = urlParams.get("date");

//change date input
const dateInput = document.querySelector("#formId input");
if (date1 == null) {
    date1 = new Date()
    dateInput.valueAsDate = date1;
} else dateInput.value = date1;
dateInput.addEventListener("change", () => {
    document.getElementById("formId").submit();
})

//get first, last date of week
const monday = setToMonday(new Date(Date.parse(date1)))
const sunday = setToSunday(new Date(Date.parse(date1)))

console.log(monday)
console.log(sunday)
console.log(getDatesOfWeek(monday, sunday))

const table = document.querySelector("#calendar table");
//get all dates in week
const dateOfWeek = getDatesAndMonthsOfWeek(monday, sunday)
table.innerHTML += "<thead>\n" +
    "<tr>\n" +
    "  <td></td>\n" +
    "  <td>Monday <p><b>" + dateOfWeek[0] + "</b></p></td>\n" +
    "  <td>Tuesday <p><b>" + dateOfWeek[1] + "</b></p></td>\n" +
    "  <td>Wednesday <p><b>" + dateOfWeek[2] + "</b></p></td>\n" +
    "  <td>Thursday <p><b>" + dateOfWeek[3] + "</b></p></td>\n" +
    "  <td>Friday <p><b>" + dateOfWeek[4] + "</b></p></td>\n" +
    "  <td>Saturday <p><b>" + dateOfWeek[5] + "</b></p></td>\n" +
    "  <td>Sunday <p><b>" + dateOfWeek[6] + "</b></p></td>\n" +
    "</tr>\n" +
    "</thead>"

const dates = []
document.getElementsByName("dates").forEach(
    date => dates.push(date.getAttribute("value")));
console.log(dates);
const days = []
for (var dateString of dates) {
    let day = new Date(Date.parse(dateString)).getDay() || 7;
    days.push(day)
}
console.log(days)

const slots = []
document.getElementsByName("slots").forEach(
    slot => slots.push(slot.getAttribute("value")));
console.log(slots);

const cars = []
document.getElementsByName("cars").forEach(
    car => cars.push(car.getAttribute("value")));
console.log(cars);

table.innerHTML += "<tbody></tbody>"
const tableBody = document.querySelector("#calendar table tbody");
let tableRender = "";
//render all meeting slots in selected week
for (let i = 0; i < slots.length; i++) {
    tableRender += "<tr>" +
        "  <td>" + slots[i] + "</td>";
    for (let j = 1; j <= 7; j++) {
        if (j == days[i]) {
            tableRender += "<td>" +
                "<a id='detail-btn' href='./slot-detail?date=" + dates[i] + "&slot=" + slots[i] + "'>View detail</a><br>"

            //check there still meeting in same time
            if (slots[i] == slots[i + 1]) {
                let count = 1
                //count cars in 1 slot
                while (days[i] == days[i + 1] && slots[i] == slots[i + 1]) {
                    if (cars[i] != cars[i + 1]) count++
                    i++
                }
                //check overlap meetings in this slot
                //if there is no overlap count is equal to 1
                if (count == 1) {
                    tableRender += "<a href='#'>" + cars[i] + "</a>" +
                        "</td>"
                    if (slots[i] == slots[i + 1]) i++
                } else {
                    tableRender += "<a href='#'>" + count + " Cars</a>" +
                        "</td>"
                    if (slots[i] == slots[i + 1]) i++
                }
                console.log(count)
            } else {
                tableRender += "<a href='#'>" + cars[i] + " Cars</a>" +
                    "</td>"
            }
        } else tableRender += "<td> - </td>"
    }
    tableRender += "</tr>"
}
tableBody.innerHTML += tableRender;

const prevBtn = document.getElementById("prev-btn")
const nextBtn = document.getElementById("next-btn");

prevBtn.addEventListener("click", () => {
    let prevWeekDay = new Date(dateInput.value)
    prevWeekDay.setHours(-24 * 6);
    dateInput.valueAsDate = prevWeekDay;
    document.getElementById("formId").submit();
})

nextBtn.addEventListener("click", () => {
    let nextWeekDay = new Date(dateInput.value)
    nextWeekDay.setHours(24 * 8);
    dateInput.valueAsDate = nextWeekDay;
    document.getElementById("formId").submit();
})