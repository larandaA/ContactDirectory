/**
 * Created by Alexandra on 16.09.2016.
 */
function validateSearch() {

    var errMsg = document.getElementById("errorMessage");

    var elems = document.getElementsByName("firstName");
    for (i = 0; i < elems.length; i++) {
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
    elems = document.getElementsByName("birthDateBigger");
    for (i = 0; i < elems.length; i++) {
        if (!validateDate(elems[i].value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            return false;
        }
    }
    elems = document.getElementsByName("birthDateLess");
    for (i = 0; i < elems.length; i++) {
        if (!validateDate(elems[i].value)) {
            errMsg.textContent = "Complete the birthday correctly!";
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