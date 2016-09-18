/**
 * Created by Alexandra on 18.09.2016.
 */
var uploadPhoto = document.getElementById("uploadPhoto");
var photoPreview = document.getElementById("photoPreview");
var defaultImg = photoPreview.src;
if (defaultImg == null || defaultImg.length == 0) {
    defaultImg = "img/contacts/default.jpg";
    photoPreview.src = defaultImg;
}

uploadPhoto.addEventListener('change', previewImage, false);

function previewImage(evt) {
    if (uploadPhoto.files[0] != undefined) {
        var freader = new FileReader();
        freader.readAsDataURL(uploadPhoto.files[0]);
        freader.onload = function (event) {
            photoPreview.src = event.target.result;
        };
    } else {
        photoPreview.src = defaultImg;
    }
};
