export class PickScheme {
    constructor() {
        console.log("constructor for PickScheme");
    }
    setCookie(name, val) {
        const date = new Date();
        const value = val;
        date.setTime(date.getTime() + (7 * 24 * 60 * 60 * 1000));
        document.cookie = name + "=" + value + "; expires=" + date.toUTCString() + "; path=/";
    }
    getCookie(name) {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        if (parts.length == 2) {
        }
        return null;
    }
    deleteCookie(name) {
        const date = new Date();
        date.setTime(date.getTime() + (-1 * 24 * 60 * 60 * 1000));
        document.cookie = name + "=; expires=" + date.toUTCString() + "; path=/";
    }
    hello() {
        return 'Hello World!!!';
    }
}
var pickScheme = new PickScheme();
console.log("To be or not to be");
