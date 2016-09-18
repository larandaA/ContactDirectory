/**
 * Created by Alexandra on 16.09.2016.
 */

function validateContactInfo() {
    var errMsg = document.getElementById("errorMessage");

    var uploadPhoto = document.getElementById("uploadPhoto");
    if (uploadPhoto.files[0] != undefined) {
        if (!uploadPhoto.files[0].name.match(/.*\.jpg/)
            && !uploadPhoto.files[0].name.match(/.*\.png/)) {
            errMsg.textContent = "Choose an image for contact photo!";
            return false;
        }
    }

    var elems = document.getElementsByName("firstName");
    for (i = 0; i < elems.length; i++) {
        alert(elems[i]);
        alert(elems[i].value);
        if (!validateRequiredSymbolText(elems[i].value)) {
            errMsg.textContent = "Complete the first name correctly!";
            return false;
        }
        if (elems[i].value.length > 40) {
            errMsg.textContent = "The first name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("lastName");
    for (i = 0; i < elems.length; i++) {
        if (!validateRequiredSymbolText(elems[i].value)) {
            errMsg.textContent = "Complete the last name correctly!";
            return false;
        }
        if (elems[i].value.length > 40) {
            errMsg.textContent = "The last name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("patronymic");
    for (i = 0; i < elems.length; i++) {
        if (!validateNotRequiredSymbolText(elems[i].value)) {
            errMsg.textContent = "Complete the patronymic correctly!";
            return false;
        }
        if (elems[i].value.length > 40) {
            errMsg.textContent = "The patronymic is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("birthDate");
    for (i = 0; i < elems.length; i++) {
        if (!validateDate(elems[i].value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            return false;
        }
    }
    elems = document.getElementsByName("email");
    for (i = 0; i < elems.length; i++) {
        if (!validateEmail(elems[i].value)) {
            errMsg.textContent = "Complete the email address correctly!";
            return false;
        }
        if (elems[i].value.length > 100) {
            errMsg.textContent = "The email address is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("webSite");
    for (i = 0; i < elems.length; i++) {
        if (!validateWebSite(elems[i].value)) {
            errMsg.textContent = "Complete the web site correctly!";
            return false;
        }
        if (elems[i].value.length > 200) {
            errMsg.textContent = "The web site address is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("placeOfWork");
    for (i = 0; i < elems.length; i++) {
        if (elems[i].value != null && elems[i].value.length > 100) {
            errMsg.textContent = "Place of work is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("city");
    for (i = 0; i < elems.length; i++) {
        if (!validateNotRequiredSymbolText(elems[i].value)) {
            errMsg.textContent = "Complete the city name correctly!";
            return false;
        }
        if (elems[i].value.length > 100) {
            errMsg.textContent = "The city name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("localAddress");
    for (i = 0; i < elems.length; i++) {
        if (!validateNotRequiredCombinedText(elems[i].value)) {
            errMsg.textContent = "Complete the local address correctly!";
            return false;
        }
        if (elems[i].value.length > 100) {
            errMsg.textContent = "The local address is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("index");
    for (i = 0; i < elems.length; i++) {
        if (!validateNotRequiredCombinedText(elems[i].value)) {
            errMsg.textContent = "Complete the post index correctly!";
            return false;
        }
        if (elems[i].value.length > 20) {
            errMsg.textContent = "The post index is too long!";
            return false;
        }
    }

    errMsg.textContent = "";
    return true;
}
