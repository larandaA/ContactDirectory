/**
 * Created by Alexandra on 16.09.2016.
 */
function validateEmailForm() {
    var errMsg = document.getElementById("errorMessage");

    var elem = document.getElementsByName("emails")[0];
    if (elem.value == null || elem.value.length == 0) {
        errMsg.textContent = "You can't send an email to nobody!";
        return false;
    }
    var emails = elem.value.split(", ");
    for (i = 0; i < emails.length; i++) {
        if (!validateEmail(emails[i])) {
            errMsg.textContent = "Email is not valid!";
            return false;
        }
    }
    elem = document.getElementsByName("topic")[0];
    if (elem.value == null || elem.value.length == 0) {
        errMsg.textContent = "Set a subject of your email, please!";
        return false;
    }
    elem = document.getElementsByName("text")[0];
    if (elem.value == null || elem.value.length == 0) {
        errMsg.textContent = "Your message is empty!";
        return false;
    }
    errMsg.textContent = "";
    return true;
}