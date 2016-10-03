/**
 * Created by Alexandra on 01.10.2016.
 */
function createInputElement(type, name, value) {
    var input = document.createElement("input");
    input.setAttribute("type", type);
    input.setAttribute("name", name);
    input.setAttribute("value", value);
    return input;
}

function createButtonElement(type, className, textContent, clickListener) {
    var bt = document.createElement("button");
    bt.setAttribute("type", type);
    bt.setAttribute("class", className);
    bt.textContent = textContent;
    bt.addEventListener('click', clickListener);
    return bt;
}