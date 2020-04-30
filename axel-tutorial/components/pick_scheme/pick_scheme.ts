declare const $:any;        // make sure jquery is loaded
declare const moment:any;   // make sure momentjs is loaded

export class PickScheme {

    constructor () {
        console.log("constructor for PickScheme");
    }

    setCookie(name: string, val: string) {
        const date = new Date();
        const value = val;
    
        // Set it expire in 7 days
        date.setTime(date.getTime() + (7 * 24 * 60 * 60 * 1000));
    
        // Set it
        document.cookie = name+"="+value+"; expires="+date.toUTCString()+"; path=/";
    }

    getCookie(name: string): any {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        
        if (parts.length == 2) {
            // return parts.pop().split(";").shift();
        }
        return null;
    }

    deleteCookie(name: string) {
        const date = new Date();
    
        // Set it expire in -1 days
        date.setTime(date.getTime() + (-1 * 24 * 60 * 60 * 1000));
    
        // Set it
        document.cookie = name+"=; expires="+date.toUTCString()+"; path=/";
    }

    hello(): string {
        return 'Hello World!!!';

    }
}

var pickScheme = new PickScheme();
//var cookie = pickScheme.getCookie("axelframework");
console.log("To be or not to be");