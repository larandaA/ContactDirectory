/**
 * Created by Alexandra on 16.09.2016.
 */
function validateSearch() {

    var errMsg = document.getElementById("errorMessage");

    var elems = document.getElementsByName("firstName");
    for (elem in elems) {
        if (!validateRequiredSymbolText(elem.value)) {
            errMsg.textContent = "Complete the first name correctly!";
            return false;
        }
        if (elem.value.length > 40) {
            errMsg.textContent = "The first name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("lastName");
    for (elem in elems) {
        if (!validateRequiredSymbolText(elem.value)) {
            errMsg.textContent = "Complete the last name correctly!";
            return false;
        }
        if (elem.value.length > 40) {
            errMsg.textContent = "The last name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("patronymic");
    for (elem in elems) {
        if (!validateNotRequiredSymbolText(elem.value)) {
            errMsg.textContent = "Complete the patronymic correctly!";
            return false;
        }
        if (elem.value.length > 40) {
            errMsg.textContent = "The patronymic is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("birthDateBigger");
    for (elem in elems) {
        if (!validateDate(elem.value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            return false;
        }
    }
    elems = document.getElementsByName("birthDateLess");
    for (elem in elems) {
        if (!validateDate(elem.value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            return false;
        }
    }
    elems = document.getElementsByName("city");
    for (elem in elems) {
        if (!validateNotRequiredSymbolText(elem.value)) {
            errMsg.textContent = "Complete the city name correctly!";
            return false;
        }
        if (elem.value.length > 100) {
            errMsg.textContent = "The city name is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("localAddress");
    for (elem in elems) {
        if (!validateNotRequiredCombinedText(elem.value)) {
            errMsg.textContent = "Complete the local address correctly!";
            return false;
        }
        if (elem.value.length > 100) {
            errMsg.textContent = "The local address is too long!";
            return false;
        }
    }
    elems = document.getElementsByName("index");
    for (elem in elems) {
        if (!validateNotRequiredCombinedText(elem.value)) {
            errMsg.textContent = "Complete the post index correctly!";
            return false;
        }
        if (elem.value.length > 20) {
            errMsg.textContent = "The post index is too long!";
            return false;
        }
    }

    errMsg.textContent = "";
    return true;
}