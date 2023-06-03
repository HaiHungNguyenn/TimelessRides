// Check phone input
var phoneInput = document.getElementById('phone-number');
var myForm = document.forms.myForm;
var result = document.getElementById('phone-number-result');  // only for debugging purposes

phoneInput.addEventListener('input', function (e) {
    var x = e.target.value.replace(/\D/g, '').match(/(\d{0,4})(\d{0,3})(\d{0,3})/);
    e.target.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
});

myForm.addEventListener('submit', function (e) {
    phoneInput.value = phoneInput.value.replace(/\D/g, '');
    result.innerText = phoneInput.value;  // only for debugging purposes

    e.preventDefault(); // You wouldn't prevent it
});

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
// Remove image
function removeImage(button) {
    const preview = button.previousElementSibling; // get the <img> element preceding the input element
    const file = document.getElementById('car-image').files;

    preview.src = ""; // clear the src attribute of the <img> element
    preview.style.display = 'none';
    button.style.display = 'none';

    var fileBuffer = new DataTransfer();
    const index = parseInt(button.id);

    // append the file list to an array iteratively
    for (let i = 0; i < file.length; i++) {
        // Exclude file in specified index
        if (index !== i)
            fileBuffer.items.add(file[i]);
    }

    // Assign buffer to file input
    document.getElementById("car-image").files = fileBuffer.files; // <-- according to your file input reference
}
// Check num of files
$(function () {
    $("input[type='file']").change(function () {
        var $fileUpload = $("input[type='file']");
        if (parseInt($fileUpload.get(0).files.length) > MAX_IMAGE_NUM) {
            this.value = "";
            alert("You can only upload a maximum of " + MAX_IMAGE_NUM + " files");
            previewImages();
        }
    });
});
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