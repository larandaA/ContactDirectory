/**
 * Created by Alexandra on 16.09.2016.
 */
/*var singleContactForms = document.getElementsByClassName("singleContactForm");
for(var i = 0; i < singleContactForms.length; i++) {
    singleContactForms[i].addEventListener("submit", singleContactFormListener);
}

function singleContactFormListener(evt) {
    evt.stopPropagation();
    return true;
}
*/

var errMsg = document.getElementById("error-message");
var contactListForm = document.getElementById("contactListForm");
contactListForm.addEventListener('submit', validateContactList);


function validateContactList(evt) {
    errMsg.textContent = "";

    var checkBoxes = document.getElementsByName("checked");
    var checked = 0;
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            checked++;
        }
    }
    if (checked == 0) {
        errMsg.textContent = "Choose at least one contact!";
        evt.preventDefault();
    }
}