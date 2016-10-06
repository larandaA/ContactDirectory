/**
 * Created by Alexandra on 22.09.2016.
 */
var overlay = document.getElementById("modalOverlay");
var phoneFormDiv = document.getElementById("phoneFormDiv");
var phoneChangesDiv = document.getElementById("phoneChanges");
var phoneTable = document.getElementById("phoneTable");
var phoneFormErrorMes = document.getElementById("phoneFormErrorMes");

var deletePhoneButs = document.getElementsByClassName("deletePhone");
var editPhoneButs = document.getElementsByClassName("editPhone");
var deletePhonesBut = document.getElementById("deleteCheckedPhones");
var createNewPhoneBut = document.getElementById("createNewPhone");

var countryCodeSelect = document.getElementsByName("countryCode")[0];
var operatorCodeInput = document.getElementsByName("operatorCode")[0];
var phoneNumberInput = document.getElementsByName("phoneNumber")[0];
var phoneTypeRads = document.getElementsByName("phoneType");
var phoneCommentArea = document.getElementsByName("phoneComment")[0];
var savePhoneBut = document.getElementById("savePhone");
var cancelPhoneFormBut = document.getElementById("cancelPhoneForm");

var isNew = true;
var trToEdit = null;

phoneFormDiv.style.display = "none";
createNewPhoneBut.addEventListener('click', createNewPhone);
savePhoneBut.addEventListener('click', savePhone);
cancelPhoneFormBut.addEventListener('click',cancelPhoneForm);
deletePhonesBut.addEventListener('click', deleteCheckedPhones);
for (var i = 0; i < deletePhoneButs.length; i++) {
    deletePhoneButs[i].addEventListener('click', deletePhone);
}
for (var i = 0; i < editPhoneButs.length; i++) {
    editPhoneButs[i].addEventListener('click', editPhone);
}

function getPhoneType() {
    var type = "";
    for (var i = 0; i < phoneTypeRads.length; i++) {
        if (phoneTypeRads[i].checked) {
            type = phoneTypeRads[i].value;
        }
    }
    return type;
}

function setPhoneType(value) {
    for (var i = 0; i < phoneTypeRads.length; i++) {
        if (phoneTypeRads[i].value === value) {
            phoneTypeRads[i].checked = true;
        } else {
            phoneTypeRads.checked = false;
        }
    }
}

function getPhoneNumber() {
    return countryCodeSelect.options[countryCodeSelect.selectedIndex].value + ' (' +
        operatorCodeInput.value + ') ' + phoneNumberInput.value;
}

function setCountryCode(value) {
    for (var i = 0; i < countryCodeSelect.options.length; i++) {
        if (countryCodeSelect.options[i].text === value) {
            countryCodeSelect.selectedIndex = i;
        }
    }
}

function buildPhoneRepresentation(id, type) {
    return id + '|' + countryCodeSelect.options[countryCodeSelect.selectedIndex].value +
        '|' + operatorCodeInput.value + '|' + phoneNumberInput.value + '|' + type + '|' + phoneCommentArea.value;
}


function deleteCheckedPhones(evt) {
    var checkPhones = document.getElementsByName("phoneChecked");
    var allRemoved = false;
    while (!allRemoved) {
        allRemoved = true;
        for (var i = 0; i < checkPhones.length; i++) {
            if (!checkPhones[i].checked) {
                continue;
            }
            allRemoved = false;
            var tr = checkPhones[i].parentNode.parentNode;
            var id = "";
            for (var i = 0; i < tr.cells[4].children.length; i++) {
                if (tr.cells[4].children[i].getAttribute("name") === "phoneId") {
                    id = tr.cells[4].children[i].value;
                }
            }

            if (id.length > 0) {
                phoneChangesDiv.appendChild(createInputElement("hidden","deletePhoneWithId", id))
            }
            var par = tr.parentNode;
            par.removeChild(tr);
            break;
        }
        checkPhones = document.getElementsByName("phoneChecked");
    }
}

function createNewPhone(evt) {
    phoneFormErrorMes.textContent = "";
    isNew = true;
    operatorCodeInput.value = "";
    phoneNumberInput.value = "";
    phoneCommentArea.value = "";
    for (var i = 0; i < phoneTypeRads.length; i++) {
        phoneTypeRads[i].checked = false;
    }
    countryCodeSelect.selectedIndex = 0;

    overlay.style.display = "block";
    phoneFormDiv.style.display = "block";
}

function editPhone(evt) {
    phoneFormErrorMes.textContent = "";
    isNew = false;
    trToEdit = evt.target.parentNode.parentNode;

    var phone = trToEdit.cells[1].textContent.split(' ');
    countryCodeSelect.selectedIndex = 0;
    setCountryCode(phone[0]);
    operatorCodeInput.value = phone[1].substr(1, phone[1].length - 2);
    phoneNumberInput.value = phone[2];
    setPhoneType(trToEdit.cells[2].textContent);
    phoneCommentArea.value = trToEdit.cells[3].textContent;

    overlay.style.display = "block";
    phoneFormDiv.style.display = "block";
}

function savePhone(evt) {
    if (!validatePhoneNumber(phoneNumberInput.value)) {
        phoneFormErrorMes.textContent = "Phone number is not correct! Be sure it's not empty and contains only 5-9 digits with no spaces.";
        return;
    }
    if (!validateOperatorCode(operatorCodeInput.value)) {
        phoneFormErrorMes.textContent = "Operator code is not correct! Be sure it's empty or contains only 1-5 digits with no spaces.";
        return;
    }
    phoneFormErrorMes.textContent = "";
    if (!isNew) {
        trToEdit.cells[1].textContent = getPhoneNumber();
        var type = getPhoneType();
        trToEdit.cells[2].textContent = type;
        trToEdit.cells[3].textContent = phoneCommentArea.value;

        var updateInput;
        var createInput;
        var id = "";
        for (var i = 0; i < trToEdit.cells[4].children.length; i++) {
            if (trToEdit.cells[4].children[i].getAttribute("name") === "updatePhone") {
                updateInput = trToEdit.cells[4].children[i];
            }
            if (trToEdit.cells[4].children[i].getAttribute("name") === "createPhone") {
                createInput = trToEdit.cells[4].children[i];
            }
            if (trToEdit.cells[4].children[i].getAttribute("name") === "phoneId") {
                id = trToEdit.cells[4].children[i].value;
            }
        }
        if (id.length > 0) {
            updateInput.value = buildPhoneRepresentation(id, type);
        } else {
            createInput.value = buildPhoneRepresentation("", type);
        }


    } else {
        var tr = document.createElement("tr");

        var td0 = document.createElement("td");
        td0.appendChild(createInputElement("checkbox", "phoneChecked", ""));
        tr.appendChild(td0);

        var td1 = document.createElement("td");
        td1.textContent = getPhoneNumber();
        tr.appendChild(td1);

        var td2 = document.createElement("td");
        var type = getPhoneType();
        td2.textContent = type;
        tr.appendChild(td2);

        var td3 = document.createElement("td");
        td3.textContent = phoneCommentArea.value;
        tr.appendChild(td3);

        var td4 = document.createElement("td");
        td4.appendChild(createInputElement("hidden", "createPhone", buildPhoneRepresentation("", type)));
        td4.appendChild(createInputElement("hidden", "phoneId", ""));
        td4.appendChild(createButtonElement("button", "btn list-btn editPhone", "Edit", editPhone));
        td4.appendChild(createButtonElement("button", "btn list-btn deletePhone", "Delete", deletePhone));
        tr.appendChild(td4);

        phoneTable.appendChild(tr);
    }
    phoneFormDiv.style.display = "none";
    overlay.style.display = "none";
}

function cancelPhoneForm(evt) {
    overlay.style.display = "none";
    phoneFormDiv.style.display = "none";
}

function deletePhone(evt) {
    sib = evt.target.previousElementSibling;
    while (sib.getAttribute("name") !== "phoneId") {
        sib = sib.previousElementSibling;
    }
    if (sib.value.length > 0) {
        phoneChangesDiv.appendChild(createInputElement("hidden", "deletePhoneWithId", sib.value));
    }
    var tr = sib.parentNode.parentNode;
    var trPar = tr.parentNode;
    trPar.removeChild(tr);

}