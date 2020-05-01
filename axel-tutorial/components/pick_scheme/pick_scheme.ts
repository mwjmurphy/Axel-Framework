declare const $:any;        // make sure jquery is loaded
declare const moment:any;   // make sure momentjs is loaded
import { RequestParams } from "../common/request_params.js";

export class PickScheme {

    request_selected_theme = "selected_theme";  // comes from http
    selected_theme = "selected_theme";
    default_theme = "solar";
    requestParams:RequestParams;
    constructor () {
        console.log("constructor for PickScheme");
        this.requestParams = new RequestParams(); 
        this.showScheme();
    }

    // Show the select or default theme
    showScheme() {
        let scheme:string = this.requestParams.getParam(this.request_selected_theme);
    
        if (scheme) {
            this.put(this.selected_theme, scheme);
            console.log("value from httprequest:"+scheme);
        }
        scheme = this.get(this.selected_theme);
        if (scheme) {
            console.log("value from get:"+scheme);
            $('head').append('<link rel="stylesheet" href="/bootstrap/' + scheme + '/bootstrap.min.css">');
        } else {
            scheme = this.default_theme;
            console.log("value from default:"+scheme);
            $('head').append('<link rel="stylesheet" href="/bootstrap/' + scheme + '/bootstrap.min.css">');
            this.put(this.selected_theme, scheme);
        }
    }

    setScheme(scheme: string) {
        this.put(this.selected_theme, scheme);
    }

    // put a value
    put(key:string, value:any) : void {
        localStorage.setItem(key, value);
    }

    // get a value
    get(key:string) : any {
        return localStorage.getItem(key);
    }

    // remove a value
    remove(key:string): void {
        localStorage.removeItem(key);
    }

}

var pickScheme = new PickScheme();