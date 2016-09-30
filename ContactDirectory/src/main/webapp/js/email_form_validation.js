/**
 * Created by Alexandra on 16.09.2016.
 */
var errMsg = document.getElementById("error-message");
var emailForm = document.getElementById("emailForm");
emailForm.addEventListener('submit', validateEmailForm);

function validateEmailForm(evt) {
    errMsg.textContent = "";
    var elem = document.getElementsByName("emails")[0];
    if (!validateRequiredText(elem.value)) {
        errMsg.textContent = "You can't send an email to nobody!";
        evt.preventDefault();
        return;
    }
    elem = document.getElementsByName("topic")[0];
    if (!validateRequiredText(elem.value)) {
        errMsg.textContent = "Set a subject of your email, please!";
        evt.preventDefault();
        return;
    }
    elem = document.getElementsByName("text")[0];
    if (!validateRequiredText(elem.value)) {
        errMsg.textContent = "Your message is empty!";
        evt.preventDefault();
        return;
    }
}