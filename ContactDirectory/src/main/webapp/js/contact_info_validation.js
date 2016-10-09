/**
 * Created by Alexandra on 16.09.2016.
 */

var errMsg = document.getElementById("error-message");
var infoForm = document.getElementById("infoForm");
infoForm.addEventListener('submit', validateContactInfoForm);

function firstNameIsValid(elem) {
    if (!validateRequiredText(elem.value)) {
        errMsg.textContent = "Complete the first name correctly!";
        return false;
    }
    if (elem.value.length > 40) {
        errMsg.textContent = "The first name is too long!";
        return false;
    }
    return true;
}

function lastNameIsValid(elem) {
    if (!validateRequiredText(elem.value)) {
        errMsg.textContent = "Complete the last name correctly!";
        return false;
    }
    if (elem.value.length > 40) {
        errMsg.textContent = "The last name is too long!";
        return false;
    }
    return true;
}

function patronymicIsValid(elem) {
    if (elem.value.length > 40) {
        errMsg.textContent = "The patronymic is too long!";
        return false;
    }
    return true;
}

function emailIsValid(elem) {
    if (!validateEmail(elem.value)) {
        errMsg.textContent = "Complete the email address correctly!";
        return false;
    }
    if (elem.value.length > 100) {
        errMsg.textContent = "The email address is too long!";
        return false;
    }
    return true;
}

function webSiteIsValid(elem) {
    if (!validateWebSite(elem.value)) {
        errMsg.textContent = "Complete the web site correctly!";
        return false;
    }
    if (elem.value.length > 200) {
        errMsg.textContent = "The web site address is too long!";
        return false;
    }
    return true;
}

function cityIsValid(elem) {
    if (elem.value.length > 100) {
        errMsg.textContent = "The city name is too long!";
        return false;
    }
    return true;
}

function validateContactInfoForm(evt) {
    errMsg.textContent = "";

    var elem = document.getElementsByName("firstName")[0];
    if (!firstNameIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("lastName")[0];
    if (!lastNameIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("patronymic")[0];
    if (!patronymicIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("birthDate")[0];
    if (!validateDate(elem.value)) {
        errMsg.textContent = "Complete the birthday correctly!";
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("email")[0];
    if (!emailIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("webSite")[0];
    if (!webSiteIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("placeOfWork")[0];
    if (elem.value != null && elem.value.length > 100) {
        errMsg.textContent = "Place of work is too long!";
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("city")[0];
    if (!cityIsValid(elem)) {
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("localAddress")[0];
    if (elem.value.length > 100) {
        errMsg.textContent = "The local address is too long!";
        evt.preventDefault();
        return;
    }

    elem = document.getElementsByName("index")[0];
    if (elem.value.length > 20) {
        errMsg.textContent = "The post index is too long!";
        evt.preventDefault();
        return;
    }
}
