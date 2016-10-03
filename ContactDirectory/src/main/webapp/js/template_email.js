/**
 * Created by Alexandra on 02.10.2016.
 */
var tempSelect = document.getElementsByName("template")[0];
var templateText = document.getElementById("templateText");

tempSelect.addEventListener('change', changeTemplate);

function changeTemplate(evt) {
    var temp = tempSelect.options[tempSelect.selectedIndex].value;
    temp = "template" + temp;
    var value = document.getElementsByName(temp)[0].value;
    templateText.value = value;
}