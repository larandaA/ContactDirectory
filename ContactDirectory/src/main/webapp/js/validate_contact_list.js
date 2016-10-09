/**
 * Created by Alexandra on 16.09.2016.
 */

var errMsg = document.getElementById("error-message");
var contactListForm = document.getElementById("contactListForm");
contactListForm.addEventListener('submit', validateContactList);


function validateContactList(evt) {
    errMsg.textContent = "";

    var checkBoxes = document.getElementsByName("checkedId");
    var checked = 0;
    for (var i = 0; i < checkBoxes.length; i++) {
        if (checkBoxes[i].checked) {
            checked++;
            contactListForm.appendChild(createInputElement("hidden", "checked", checkBoxes[i].value));
        }
    }
    if (checked == 0) {
        errMsg.textContent = "Choose at least one contact!";
        evt.preventDefault();
    }
}