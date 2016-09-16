/**
 * Created by Alexandra on 16.09.2016.
 */
var emailReg = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/;
var urlReg = /^(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/;
var dateReg = /^[0-9]{2}\.[0-9]{2}\.[0-9]{4}$/;

var symbolReg = /^[a-zA-z]+([ '-][a-zA-Z]+)*$/;
var combinedReg = /[a-zA-Z0-9' -\./]+$/;

var phoneNumberReg = /^[0]*[1-9][0-9]{4,8}$/;
var operatorCodeReg = /^[0]*[1-9][0-9]{0,4}$/;
var countryCodeReg = /^[0]*[1-9][0-9]{0,3}$/;



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

function validateRequiredSymbolText(text) {
    if (text == null || text.length == 0) {
        return false;
    }
    return symbolReg.test(text);
}

function validateNotRequiredSymbolText(text) {
    if (text == null || text.length == 0) {
        return true;
    }
    return symbolReg.test(text);
}

function validateRequiredCombinedText(text) {
    if (text == null || text.length == 0) {
        return false;
    }
    return combinedReg.test(text);
}

function validateNotRequiredCombinedText(text) {
    if (text == null || text.length == 0) {
        return true;
    }
    return combinedReg.test(text);
}

function validateRequiredText(text) {
    if (text == null || text.length == 0) {
        return false;
    }
    return true;
}

function validatePhoneNumber(number) {
    if (number == null || number.length == 0) {
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

function validateCountryCode(code) {
    if (code == null || code.length == 0) {
        return true;
    }
    return countryCodeReg.test(code);
}