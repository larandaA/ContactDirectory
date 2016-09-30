/**
 * Created by Alexandra on 16.09.2016.
 */
var errMsg = document.getElementById("error-message");
var searchForm = document.getElementById("searchForm");
searchForm.addEventListener('submit', validateSearchForm);

function validateSearchForm(evt) {

    errMsg.textContent = "";

    elems = document.getElementsByName("birthDateBigger");
    for (i = 0; i < elems.length; i++) {
        if (!validateDate(elems[i].value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            evt.preventDefault();
            return;
        }
    }
    elems = document.getElementsByName("birthDateLess");
    for (i = 0; i < elems.length; i++) {
        if (!validateDate(elems[i].value)) {
            errMsg.textContent = "Complete the birthday correctly!";
            evt.preventDefault();
            return;
        }
    }
}