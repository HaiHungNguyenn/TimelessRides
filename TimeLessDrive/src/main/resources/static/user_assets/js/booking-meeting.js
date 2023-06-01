var favicon = document.querySelector('link[rel="shortcut icon"]');
window.isDarkMode = (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches);
if (favicon && window.isDarkMode) {
    favicon.href = favicon.href.replaceAll('favicon-2021-light%402x.png', 'favicon-2021-dark%402x.png');
}

JotForm.newDefaultTheme = false;
JotForm.extendsNewTheme = false;
JotForm.singleProduct = false;
JotForm.newPaymentUIForNewCreatedForms = false;
JotForm.clearFieldOnHide = "disable";
JotForm.submitError = "jumpToFirstError";

JotForm.init(function () {
    /*INIT-START*/

    JotForm.calendarMonths = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    JotForm.appointmentCalendarDays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"];
    JotForm.calendarOther = "Today";
    window.initializeAppointment({ "text": { "text": "Question", "value": "Select an Appointment Date" }, "labelAlign": { "text": "Label Align", "value": "Top", "dropdown": [["Auto", "Auto"], ["Left", "Left"], ["Right", "Right"], ["Top", "Top"]] }, "required": { "text": "Required", "value": "No", "dropdown": [["No", "No"], ["Yes", "Yes"]] }, "description": { "text": "Hover Text", "value": "", "textarea": true }, "slotDuration": { "text": "Slot Duration", "value": "60", "dropdown": [[15, "15 min"], [30, "30 min"], [45, "45 min"], [60, "60 min"], ["custom", "Custom min"]], "hint": "Select how long each slot will be." }, "startDate": { "text": "Start Date", "value": "" }, "endDate": { "text": "End Date", "value": "" }, "intervals": { "text": "Intervals", "value": [{ "from": "7:00", "to": "22:00", "days": ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"] }], "hint": "The hours will be applied to the selected days and repeated." }, "useBlockout": { "text": "Blockout Custom Dates", "value": "No", "dropdown": [["No", "No"], ["Yes", "Yes"]], "hint": "Disable certain date(s) in the calendar." }, "blockoutDates": { "text": "Blockout dates", "value": [{ "startDate": "", "endDate": "" }] }, "useLunchBreak": { "text": "Lunch Time", "value": "Yes", "dropdown": [["No", "No"], ["Yes", "Yes"]], "hint": "Enable lunchtime in the calendar." }, "lunchBreak": { "text": "Lunchtime hours", "value": [{ "from": "12:00", "to": "14:00" }] }, "timezone": { "text": "Timezone", "value": "Asia/Bangkok (GMT+07:00)" }, "timeFormat": { "text": "Time Format", "value": "", "dropdown": [["24 Hour", "24 Hour"], ["AM/PM", "AM/PM"]], "icon": "images/blank.gif", "iconClassName": "toolbar-time_format_24" }, "months": { "value": ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], "hidden": true }, "days": { "value": ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"], "hidden": true }, "startWeekOn": { "text": "Start Week On", "value": "", "dropdown": [["Monday", "Monday"], ["Sunday", "Sunday"]], "toolbar": false }, "rollingDays": { "text": "Rolling Days", "value": "", "toolbar": false }, "prevMonthButtonText": { "text": "Previous month", "value": "" }, "nextMonthButtonText": { "text": "Next month", "value": "" }, "prevYearButtonText": { "text": "Previous year", "value": "" }, "nextYearButtonText": { "text": "Next year", "value": "" }, "prevDayButtonText": { "text": "Previous day", "value": "" }, "nextDayButtonText": { "text": "Next day", "value": "" }, "appointmentType": { "hidden": true, "value": "single" }, "dateFormat": { "hidden": true, "value": "mm/dd/yyyy" }, "maxAttendee": { "hidden": true, "value": "5" }, "minScheduleNotice": { "hidden": true, "value": "3" }, "name": { "hidden": true, "value": "selectAn" }, "order": { "hidden": true, "value": "2" }, "qid": { "toolbar": false, "value": "input_24" }, "type": { "hidden": true, "value": "control_appointment" }, "id": { "toolbar": false, "value": "24" }, "qname": { "toolbar": false, "value": "q24_selectAn" }, "cdnconfig": { "CDN": "https://cdn.jotfor.ms/" }, "passive": false, "formProperties": { "products": [{ "currency": "USD", "icon": "", "name": "NON-Refundable Photo Session Deposit", "period": "Monthly", "pid": "1003", "price": "50", "recurringtimes": "No Limit", "required": "", "selected": "", "setupfee": "", "trial": "None" }, { "currency": "USD", "icon": "", "name": "NON-Refundable NEWBORN Session Deposit", "period": "Monthly", "pid": "1004", "price": "100", "recurringtimes": "No Limit", "required": "", "selected": "", "setupfee": "", "trial": "None" }, { "icon": "", "name": "Paying by Gift Certificate", "period": "Monthly", "pid": "1005", "price": "0", "recurringtimes": "No Limit", "required": "", "selected": "", "setupfee": "", "trial": "None" }], "highlightLine": "Enabled", "coupons": false, "useStripeCoupons": false, "taxes": [{ "exemptions": "[]", "rate": "0", "surcharge": "{}" }], "comparePaymentForm": "", "paymentListSettings": false, "newPaymentUIForNewCreatedForms": false, "formBackground": "rgba(255, 255, 255, 0.7)", "newAppointment": false }, "formID": 231493697523465, "isPaymentForm": false, "isOpenedInPortal": false, "isCheckoutForm": false, "themeVersion": "v1" });
    JotForm.setPhoneMaskingValidator('input_32_full', '\u0028\u0023\u0023\u0023\u0023\u0029 \u0023\u0023\u0023 \u0023\u0023\u0023');
    if (window.JotForm && JotForm.accessible) $('input_31').setAttribute('tabindex', 0);
    /*INIT-END*/
});

JotForm.prepareCalculationsOnTheFly([null, null, { "name": "submit", "qid": "2", "text": "Submit", "type": "control_button" }, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, { "name": "heading", "qid": "23", "text": "BOOK YOUR SESSION", "type": "control_head" }, { "name": "selectAn", "qid": "24", "text": "Select an Appointment Date", "type": "control_appointment" }, null, null, null, null, null, null, { "description": "", "name": "otherRequirements", "qid": "31", "subLabel": "", "text": "Other requirements", "type": "control_textarea" }, { "description": "", "name": "phoneNumber32", "qid": "32", "text": "Phone Number", "type": "control_phone" }]);
setTimeout(function () {
    JotForm.paymentExtrasOnTheFly([null, null, { "name": "submit", "qid": "2", "text": "Submit", "type": "control_button" }, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, { "name": "heading", "qid": "23", "text": "BOOK YOUR SESSION", "type": "control_head" }, { "name": "selectAn", "qid": "24", "text": "Select an Appointment Date", "type": "control_appointment" }, null, null, null, null, null, null, { "description": "", "name": "otherRequirements", "qid": "31", "subLabel": "", "text": "Other requirements", "type": "control_textarea" }, { "description": "", "name": "phoneNumber32", "qid": "32", "text": "Phone Number", "type": "control_phone" }]);
}, 20);
