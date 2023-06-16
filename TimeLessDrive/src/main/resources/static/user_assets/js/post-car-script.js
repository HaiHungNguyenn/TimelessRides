// Mask license plate
// var licensePlate = document.getElementById('license-plate');
//
// licensePlate.addEventListener('input', function (e) {
//     var x = e.target.value.replace(/[a-z]/g, '').match(/(\d{0,2})(\d{0,3})(\d{0,3})/);
//     e.target.value = !x[2] ? x[1] :  x[1] + '-' + x[2] + (x[3] ? '.' + x[3] : '');
// });
// Preview images
let filesToUpload = []; //store prev images

async function previewImages() {
    const previews = document.getElementById('images'); // select all <img> elements
    let fileInput = Array.from(document.getElementById('car-image').files); //change file list to array

    previews.innerHTML = "";
    fileInput = filesToUpload.concat(fileInput); //concat prev and new images

    //change the value on input tag
    let fileBuffer = new DataTransfer(); //change array back to file list
    let loadedImages = [];
    let toLoad = fileInput.length;
    let synchronizedPreview = (ordinal, image) => {
        loadedImages[ordinal] = image;
        if (--toLoad > 0) return;
        setPreview(loadedImages);
    };

    for (let i=0; i < fileInput.length; i++) {
        fileBuffer.items.add(fileInput[i]);
        const ordinal = i;
        if(fileInput[i]){
            const reader = new FileReader();
            reader.onloadend = function () {
                synchronizedPreview(ordinal, reader.result);
            }
            reader.readAsDataURL(fileInput[i]);
        }
        else{
            synchronizedPreview(ordinal, fileInput[i]);
        }

    }
    document.getElementById('car-image').files = fileBuffer.files;
    filesToUpload = fileInput;
}

function setPreview(images){
    let fileInput = document.getElementById('car-image').files;
    const previews = document.getElementById('images'); // select all <img> elements
    let i= 0;
    for(let i=0;i < images.length; i++) {
        previews.innerHTML +=
            '<div class="image" id="' + i + '">' +
                '<img src="' + images[i] + '">' +
                '<p>'+fileInput.item(i).name+'</p>' +
                '<span class="close">' +
                    '<i class="fa fa-trash-o"></i>' +
                '</span>' +
            '</div>';
    }
}
$(document).on('click', '.close', function () {
    const parentID = $(this).parent().attr("id");
    const fileInput = document.getElementById('car-image');
    const preview = document.getElementsByClassName('image'); // select all <img> elements
    let deletePoint = false;

    $(this).parent().remove();

    let fileBuffer = new DataTransfer(); //change array back to file list

    for (let i = 0; i < fileInput.files.length; i++) {

        if (i != parentID) {
            fileBuffer.items.add(fileInput.files[i]);
        }
    }
    fileInput.files = fileBuffer.files;
    filesToUpload = Array.from(fileBuffer.files);
    for (let i = 0; i < fileInput.files.length; i++) {
        preview[i].setAttribute("id", "" + i);
    }
});

function uploadField() {
    var uploadField = document.getElementById("car-image");
    for (let i = 0; i < uploadField.files.length; i++) {
        if (uploadField.files[i].size > 2097152) {
            alert("File is too big!");
            uploadField.value = "";
        }
    }
    ;
};

function selectPackage(packageId) {
    var selectedPackage = document.getElementById(packageId);
    var isSelected = selectedPackage.classList.contains("selected");

    var packages = document.getElementsByClassName("pricing-card");
    for (var i = 0; i < packages.length; i++) {
        packages[i].classList.remove("selected");
    }

    if (!isSelected) {
        selectedPackage.classList.add("selected");
        var plans = document.getElementsByName("plan");
        plans.forEach(function(element){
           element.remove();
        });
        var plan = document.createElement("input");
        plan.name = "plan";
        switch (packageId) {
            case "pricing-card-1":
                plan.value = "plan 1";
                break;
            case "pricing-card-2":
                plan.value = "plan 2";
                break;
            case "pricing-card-3":
                plan.value = "plan 3";
                break;
        }
        plan.classList.add("d-none");
        var parent = document.getElementById(packageId);
        parent.appendChild(plan);
    }
}