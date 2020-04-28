"use strict";
var PickScheme = /** @class */ (function () {
    function PickScheme() {
    }
    PickScheme.prototype.setCookie = function (name, val) {
        var date = new Date();
        var value = val;
        // Set it expire in 7 days
        date.setTime(date.getTime() + (7 * 24 * 60 * 60 * 1000));
        // Set it
        document.cookie = name + "=" + value + "; expires=" + date.toUTCString() + "; path=/";
    };
    PickScheme.prototype.getCookie = function (name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) {
            return parts.pop().split(";").shift();
        }
        return null;
    };
    PickScheme.prototype.deleteCookie = function (name) {
        var date = new Date();
        // Set it expire in -1 days
        date.setTime(date.getTime() + (-1 * 24 * 60 * 60 * 1000));
        // Set it
        document.cookie = name + "=; expires=" + date.toUTCString() + "; path=/";
    };
    return PickScheme;
}());
var pickScheme = new PickScheme();
var cookie = pickScheme.getCookie("axelframework");
console.log("cookie:" + cookie);
