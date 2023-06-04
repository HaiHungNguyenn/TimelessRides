// Mask license plate
// var licensePlate = document.getElementById('license-plate');
//
// licensePlate.addEventListener('input', function (e) {
//     var x = e.target.value.replace(/[a-z]/g, '').match(/(\d{0,2})(\d{0,3})(\d{0,3})/);
//     e.target.value = !x[2] ? x[1] :  x[1] + '-' + x[2] + (x[3] ? '.' + x[3] : '');
// });

// Preview images
const MAX_IMAGE_NUM = 4;

function previewImages() {
    const previews = document.querySelectorAll('.image img'); // select all <img> elements
    const fileInput = document.getElementById('car-image'); // get all uploaded files

    for (let i = 0; i < MAX_IMAGE_NUM; i++) {
        const file = fileInput.files[i]; // get the uploaded file for the current input element
        const preview = previews[i]; // get the corresponding <img> element

        const reader = new FileReader(); // create a new FileReader object

        reader.onloadend = function () {
            preview.src = reader.result; // set the src of the <img> element to the uploaded image
            preview.style.display = 'inline-block';
        }

        if (file) {
            reader.readAsDataURL(file); // read the uploaded file as a URL
        } else {
            preview.src = ""; // if no file is selected, clear the <img> element
        }
    }
}
// Check num of files
// $(function () {
//     $("input[type='file']").change(function () {
//         var $fileUpload = $("input[type='file']");
//         if (parseInt($fileUpload.get(0).files.length) > MAX_IMAGE_NUM) {
//             this.value = "";
//             alert("You can only upload a maximum of " + MAX_IMAGE_NUM + " files");
//             previewImages();
//         }
//     });
// });
// Check file size
function uploadField () {
    var uploadField = document.getElementById("car-image");
    for (let i = 0; i < uploadField.files.length; i++) {
        if (uploadField.files[i].size > 2097152) {
            alert("File is too big!");
            uploadField.value = "";
        }
    };
};