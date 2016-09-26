/**
 * Created by Alexandra on 23.09.2016.
 */
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
var deleteAttFileBut = document.getElementById("deleteAttFile");
var saveAttBut = document.getElementById("saveAtt");
var cancelAttFormBut = document.getElementById("cancelAttForm");

var aisNew = true;
var atrToEdit = null;
var inputFile = null;
var nextId = 1;
var isFileDeleted = false;

attFormDiv.style.display = "none";
downloadAttHref.style.display = "none";
createNewAttBut.addEventListener('click', createNewAtt);
saveAttBut.addEventListener('click', saveAtt);
cancelAttFormBut.addEventListener('click',cancelAttForm);
deleteAttsBut.addEventListener('click', deleteCheckedAtts);
deleteAttFileBut.addEventListener('click', deleteAttFile);
for (var i = 0; i < deleteAttButs.length; i++) {
    deleteAttButs[i].addEventListener('click', deleteAtt);
}
for (var i = 0; i < editAttButs.length; i++) {
    editAttButs[i].addEventListener('click', editAtt);
}


function deleteAttFile(evt) {
    if ((isFileDeleted || aisNew) && inputFile.files[0] == undefined) {
        return;
    }
    if ((isFileDeleted || aisNew) && inputFile.files[0] != undefined) {
        inputFile.value = "";
    }
    if (!isFileDeleted) {
        isFileDeleted = true;
        inputFile.style.display = "block";
    }
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
                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "deleteAttWithId");
                input.setAttribute("value", id);
                attChangesDiv.appendChild(input);
            }
            if (path.length > 0) {
                var input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", "deleteFileWithPath");
                input.setAttribute("value", path);
                attChangesDiv.appendChild(input);
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
    isFileDeleted = false;
    attCommentArea.value = "";
    inputFile = document.createElement("input");
    inputFile.setAttribute("type", "file");
    inputFile.setAttribute("name", "attFileN" + nextId);
    nextId++;
    attFormDiv.appendChild(inputFile);
    downloadAttHref.style.display = "none";
    attFormDiv.style.display = "block";
}

function editAtt(evt) {
    attFormErrorMes.textContent = "";
    aisNew = false;
    atrToEdit = evt.target.parentNode.parentNode;
    var path = "";
    for (var i = 0; i < atrToEdit.cells[4].children.length; i++) {
        if (atrToEdit.cells[4].children[i].getAttribute("name") === "attPath") {
            path = atrToEdit.cells[4].children[i].value;
        }
        if (atrToEdit.cells[4].children[i].getAttribute("type") === "file") {
            inputFile = atrToEdit.cells[4].children[i];
        }
    }
    if (path.length > 0) {
        isFileDeleted = false;
        inputFile.style.display = "none";
        downloadAttHref.href = path;
        downloadAttHref.style.display = "block";
    } else {
        isFileDeleted = true;
        inputFile.style.display = "block";
        downloadAttHref.href = "";
        downloadAttHref.style.display = "none";
    }
    attNameInput.value = atrToEdit.cells[1].textContent;
    attCommentArea.value = atrToEdit.cells[3].textContent;
    attFormDiv.appendChild(inputFile);
    attFormDiv.style.display = "block";
}

function saveAtt(evt) {
    if (!validateRequiredCombinedText(attNameInput.value)) {
        attFormErrorMes.textContent = "Attachment name is not correct!";
        return;
    }
    if ((isFileDeleted || aisNew) && inputFile.files[0] == undefined) {
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
        var path = null;
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
            if (atrToEdit.cells[4].children[i].getAttribute("name") === "attPath") {
                path = atrToEdit.cells[4].children[i];
            }
        }
        if (isFileDeleted && path != null) {
            var input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("name", "deleteFileWithPath");
            input.setAttribute("value", path.value);
            attChangesDiv.appendChild(input);
            atrToEdit.cells[4].removeChild(path)

        }
        if (id.length > 0) {
            if (isFileDeleted) {
                updateInput.value = id + '|' +  attNameInput.value +
                    '|f=' + inputFile.name + '|'  + attCommentArea.value;
            } else {
                updateInput.value = id + '|' +  attNameInput.value +
                    '|p=' + path.value + '|'  + attCommentArea.value;
            }
        } else {
            createInput.value = '|' + attNameInput.value +
                '|f=' + inputFile.name + '|'  + attCommentArea.value;
        }
        atrToEdit.cells[4].appendChild(inputFile);
        inputFile.style.display = "none";

    } else {
        var tr = document.createElement("tr");

        var td0 = document.createElement("td");
        var chbox = document.createElement("input");
        chbox.setAttribute("type", "checkbox");
        chbox.setAttribute("name", "attChecked");
        td0.appendChild(chbox);
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
        var inputCreate = document.createElement("input");
        inputCreate.setAttribute("type", "hidden");
        inputCreate.setAttribute("name", "createAtt");
        inputCreate.setAttribute("value", '|' + attNameInput.value +
            '|f=' + inputFile.name + '|'  + attCommentArea.value);
        td4.appendChild(inputCreate);
        var inputId = document.createElement("input");
        inputId.setAttribute("type", "hidden");
        inputId.setAttribute("name", "attId");
        inputId.setAttribute("value", "");
        td4.appendChild(inputId);
        var editBt = document.createElement("button");
        editBt.setAttribute("type", "button");
        editBt.className = "editAtt";
        editBt.textContent = "Edit";
        editBt.addEventListener('click', editAtt);
        td4.appendChild(editBt);
        var delBt = document.createElement("button");
        delBt.setAttribute("type", "button");
        delBt.className = "deleteAtt";
        delBt.textContent = "Delete";
        delBt.addEventListener('click', deleteAtt);
        td4.appendChild(delBt);
        td4.appendChild(inputFile);
        inputFile.style.display = "none";
        tr.appendChild(td4);

        attTable.appendChild(tr);
    }
    attFormDiv.style.display = "none";
}

function cancelAttForm(evt) {
    if (aisNew) {
        attFormDiv.removeChild(inputFile);
    } else {
        inputFile.style.display = "none";
        if (!isFileDeleted) {
            inputFile.value = "";
        }
        atrToEdit.cells[4].appendChild(inputFile);
    }
    attFormDiv.style.display = "none";
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
        var input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", "deleteAttWithId");
        input.setAttribute("value", id);
        attChangesDiv.appendChild(input);
    }
    if (path.length > 0) {
        var input = document.createElement("input");
        input.setAttribute("type", "hidden");
        input.setAttribute("name", "deleteFileWithPath");
        input.setAttribute("value", path);
        attChangesDiv.appendChild(input);
    }
    var tr = evt.target.parentNode.parentNode;
    var trPar = tr.parentNode;
    trPar.removeChild(tr);

}