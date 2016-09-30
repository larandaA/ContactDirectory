/**
 * Created by Alexandra on 18.09.2016.
 */
var photoWasDefault = false;
var photoIsChosen = false;
var photoIsDeleted = false;
var uploadPhoto = document.getElementById("uploadPhoto");
var photoPreview = document.getElementById("photo-preview");
var noPhoto = document.getElementById("noPhoto");
var firstImg = photoPreview.src;
var defaultImg = document.getElementById("defaultPhoto").value;
var deletePhotoWithPath = document.getElementById("deletePhotoWithPath");
var deletePhotoButton = document.getElementById("deletePhoto");
if (firstImg == null || firstImg.length == 0 || firstImg.indexOf(defaultImg) !== -1) {
    firstImg = "";
    photoPreview.src = defaultImg;
    photoWasDefault = true;
    noPhoto.value = "true;"
}

deletePhotoButton.addEventListener('click', deletePhoto);
uploadPhoto.addEventListener('change', previewImage, false);

function previewImage(evt) {
    if (uploadPhoto.files[0] != undefined) {
        var freader = new FileReader();
        freader.readAsDataURL(uploadPhoto.files[0]);
        freader.onload = function (event) {
            photoPreview.src = event.target.result;
        };
        photoIsChosen = true;
        noPhoto.value = "";
        if(!photoWasDefault && !photoIsDeleted) {
            deletePhotoWithPath.value = firstImg;
        }
    } else {
        deletePhoto(null);
    }
};

function deletePhoto(evt) {
    if (evt != null) {
        evt.stopPropagation();
    }

    if((photoWasDefault || photoIsDeleted) && !photoIsChosen) {
        return;
    }
    if(photoWasDefault && photoIsChosen) {
        photoPreview.src = defaultImg;
        photoIsChosen = false;
        noPhoto.value = "true";
        if (uploadPhoto.files[0] != undefined) {
            //clean upload
            uploadPhoto.value = "";
        }
        return;
    }
    if(!photoWasDefault && photoIsChosen) {
        if(!photoIsDeleted) {
            photoPreview.src = firstImg;
            deletePhotoWithPath.value = "";
            noPhoto.value = "";
        } else {
            photoPreview.src = defaultImg;
            noPhoto.value = "true";
        }
        photoIsChosen = false;
        if (uploadPhoto.files[0] != undefined) {
            //clean upload
            uploadPhoto.value = "";
        }
        return;
    }
    if(!photoIsDeleted && !photoIsChosen) {
        photoIsDeleted = true;
        photoPreview.src = defaultImg;
        deletePhotoWithPath.value = firstImg;
        noPhoto.value = "true";
    }
}
