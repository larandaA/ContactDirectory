/**
 * Created by Alexandra on 23.09.2016.
 */
var overlay = document.getElementById("modalOverlay");
var attFormDiv = document.getElementById("attFormDiv");
var attChangesDiv = document.getElementById("attChanges");
var attTable = document.getElementById("attTable");
var attFormErrorMes = document.getElementById("attFormErrorMes");

var deleteAttButs = document.getElementsByClassName("deleteAtt");
var editAttButs = document.getElementsByClassName("editAtt");
var deleteAttsBut = document.getElementById("deleteCheckedAtts");
var createNewAttBut = document.getElementById("createNewAtt");

var attNameInput = document.getElementsByName("attName")[0];
var attCommentArea = document.getElementsByName("attComment")[0];
var downloadAttHref = document.getElementById("downloadAttFile");
var saveAttBut = document.getElementById("saveAtt");
var cancelAttFormBut = document.getElementById("cancelAttForm");
var fileUploadDiv = document.getElementById("fileUploadDiv");

var aisNew = true;
var atrToEdit = null;
var inputFile = null;
var nextId = 1;

attFormDiv.style.display = "none";
downloadAttHref.style.display = "none";
fileUploadDiv.style.display = "none";
createNewAttBut.addEventListener('click', createNewAtt);
saveAttBut.addEventListener('click', saveAtt);
cancelAttFormBut.addEventListener('click',cancelAttForm);
deleteAttsBut.addEventListener('click', deleteCheckedAtts);
for (var i = 0; i < deleteAttButs.length; i++) {
    deleteAttButs[i].addEventListener('click', deleteAtt);
}
for (var i = 0; i < editAttButs.length; i++) {
    editAttButs[i].addEventListener('click', editAtt);
}

function buildAttRepresentation(id, fileInputName) {
    return id + '|' +  attNameInput.value +
        '|' + fileInputName + '|'  + attCommentArea.value;
}

function deleteCheckedAtts(evt) {
    var checkAtts = document.getElementsByName("attChecked");
    var allRemoved = false;
    while (!allRemoved) {
        allRemoved = true;
        for (var i = 0; i < checkAtts.length; i++) {
            if (!checkAtts[i].checked) {
                continue;
            }
            allRemoved = false;
            var tr = checkAtts[i].parentNode.parentNode;
            var id = "";
            var path = "";
            for (var i = 0; i < tr.cells[4].children.length; i++) {
                if (tr.cells[4].children[i].getAttribute("name") === "attId") {
                    id = tr.cells[4].children[i].value;
                }
                if (tr.cells[4].children[i].getAttribute("name") === "attPath") {
                    path = tr.cells[4].children[i].value;
                }
            }

            if (id.length > 0) {
                attChangesDiv.appendChild(createInputElement("hidden", "deleteAttWithId", id));
            }
            if (path.length > 0) {
                attChangesDiv.appendChild(createInputElement("hidden", "deleteFileWithPath", path));
            }
            var par = tr.parentNode;
            par.removeChild(tr);
            break;
        }
        checkAtts = document.getElementsByName("attChecked");
    }
}


function createNewAtt(evt) {
    attFormErrorMes.textContent = "";
    aisNew = true;
    attCommentArea.value = "";
    attNameInput.value = "";
    inputFile = createInputElement("file", "attFileN" + nextId, "");
    inputFile.style.display = "block";

    nextId++;
    fileUploadDiv.appendChild(inputFile);
    downloadAttHref.style.display = "none";
    fileUploadDiv.style.display = "block";
    overlay.style.display = "block";
    attFormDiv.style.display = "block";
}

function editAtt(evt) {
    attFormErrorMes.textContent = "";
    aisNew = false;
    atrToEdit = evt.target.parentNode.parentNode;
    var path = "";
    for (var i = 0; i < atrToEdit.cells[4].children.length; i++) {
        if (atrToEdit.cells[4].children[i].getAttribute("name") === "attDownloadPath") {
            path = atrToEdit.cells[4].children[i].value;
        }
    }
    downloadAttHref.href = path;
    downloadAttHref.style.display = "block";
    attNameInput.value = atrToEdit.cells[1].textContent;
    attCommentArea.value = atrToEdit.cells[3].textContent;
    overlay.style.display = "block";
    attFormDiv.style.display = "block";
}

function saveAtt(evt) {
    if (!validateRequiredText(attNameInput.value) || attNameInput.value.indexOf("|") >= 0) {
        attFormErrorMes.textContent = "Attachment name is not correct! It must not be empty or contain '|'.";
        return;
    }
    if (aisNew && inputFile.files[0] == undefined) {
        attFormErrorMes.textContent = "Attachment must have a file!";
        return;
    }
    attFormErrorMes.textContent = "";
    if (!aisNew) {
        atrToEdit.cells[1].textContent = attNameInput.value;
        atrToEdit.cells[3].textContent = attCommentArea.value;

        var updateInput;
        var createInput;
        var id = "";
        for (var i = 0; i < atrToEdit.cells[4].children.length; i++) {
            if (atrToEdit.cells[4].children[i].getAttribute("name") === "updateAtt") {
                updateInput = atrToEdit.cells[4].children[i];
            }
            if (atrToEdit.cells[4].children[i].getAttribute("name") === "createAtt") {
                createInput = atrToEdit.cells[4].children[i];
            }
            if (atrToEdit.cells[4].children[i].getAttribute("name") === "attId") {
                id = atrToEdit.cells[4].children[i].value;
            }
        }
        updateInput.value = buildAttRepresentation(id, "");

    } else {
        var tr = document.createElement("tr");

        var td0 = document.createElement("td");
        td0.appendChild(createInputElement("checkbox", "attChecked", ""));
        tr.appendChild(td0);

        var td1 = document.createElement("td");
        td1.textContent = attNameInput.value;
        tr.appendChild(td1);

        var td2 = document.createElement("td");
        td2.textContent = "now";
        tr.appendChild(td2);

        var td3 = document.createElement("td");
        td3.textContent = attCommentArea.value;
        tr.appendChild(td3);

        var td4 = document.createElement("td");
        td4.appendChild(createInputElement("hidden", "createAtt", buildAttRepresentation("", inputFile.name)));
        td4.appendChild(createInputElement("hidden", "attId", ""));
        td4.appendChild(createButtonElement("button", "btn list-btn deleteAtt", "Delete", deleteAtt));
        inputFile.style.display = "none";
        td4.appendChild(inputFile);
        tr.appendChild(td4);

        attTable.appendChild(tr);

        inputFile = null;
    }
    attFormDiv.style.display = "none";
    overlay.style.display = "none";
}

function cancelAttForm(evt) {
    if (aisNew) {
        fileUploadDiv.removeChild(inputFile);
    }
    downloadAttHref.href = "";
    inputFile = null;
    attFormDiv.style.display = "none";
    overlay.style.display = "none";
}

function deleteAtt(evt) {
    td = evt.target.parentNode;
    var id = "";
    var path ="";
    for (var i = 0; i < td.children.length; i++) {
        if (td.children[i].getAttribute("name") === "attId") {
            id = td.children[i].value;
        }
        if (td.children[i].getAttribute("name") === "attPath") {
            path = td.children[i].value;
        }
    }
    if (id.length > 0) {
        attChangesDiv.appendChild(createInputElement("hidden", "deleteAttWithId", id));
    }
    if (path.length > 0) {
        attChangesDiv.appendChild(createInputElement("hidden", "deleteFileWithPath", path));
    }
    var tr = evt.target.parentNode.parentNode;
    var trPar = tr.parentNode;
    trPar.removeChild(tr);

}