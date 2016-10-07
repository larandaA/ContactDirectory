/**
 * Created by Alexandra on 16.09.2016.
 */
var emailReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
var urlReg = /^(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/;
var dateReg = /^[0-9]{2}\.[0-9]{2}\.[0-9]{4}$/;

var phoneNumberReg = /^[\d \-\(\)]+$/;
var operatorCodeReg = /^[0]*[1-9][0-9]{0,4}$/;

var emptyReg = /^\s*$/;


function validateEmail(email) {
    if (email == null || email.length == 0) {
        return true;
    }
    return emailReg.test(email);
}

function validateWebSite(url) {
    if (url == null || url.length == 0) {
        return true;
    }
    return urlReg.test(url);
}

function validateDate(date) {
    if (date == null || date.length == 0) {
        return true;
    }
    if (!dateReg.test(date)) {
        return false;
    }
    var buf = date.split(".");
    var days = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var day = parseInt(buf[0], 10);
    var month = parseInt(buf[1], 10);
    var year = parseInt(buf[2], 10);
    if (!isDateValid(year, month, day)) {
        return false;
    }
    if (day > 0 && day <= days[month]) {
        return true;
    }
    if (day == 29 & month == 2) {
        if (year % 4 == 0 && year % 100 > 0) {
            return true;
        }
        if (year % 400 == 0) {
            return true;
        }
    }
    return false;
}

function isDateValid(year, month, day) {
    var curdate = new Date();
    if (year < curdate.getFullYear()) {
        return true;
    }
    if (year > curdate.getFullYear()) {
        return false;
    }
    if (month > 0 && month < curdate.getMonth() + 1) {
        return true;
    }
    if (month > curdate.getMonth() + 1) {
        return false;
    }
    if(day > curdate.getDate()) {
        return false;
    }
    return true;
}

function validateRequiredText(text) {
    if (text == null || emptyReg.test(text)) {
        return false;
    }
    return true;
}

function validatePhoneNumber(number) {
    if (number == null || number.length == 0) {
        return false;
    }
    if (number.trim().length > 20) {
        return false;
    }
    return phoneNumberReg.test(number);
}

function validateOperatorCode(code) {
    if (code == null || code.length == 0) {
        return true;
    }
    return operatorCodeReg.test(code);
}
