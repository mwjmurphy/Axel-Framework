import { RequestParams } from "../common/request_params.js";
export class PickScheme {
    constructor() {
        this.request_selected_theme = "selected_theme";
        this.selected_theme = "selected_theme";
        this.default_theme = "solar";
        console.log("constructor for PickScheme");
        this.requestParams = new RequestParams();
        this.showScheme();
    }
    showScheme() {
        let scheme = this.requestParams.getParam(this.request_selected_theme);
        if (scheme) {
            this.put(this.selected_theme, scheme);
            console.log("value from httprequest:" + scheme);
        }
        scheme = this.get(this.selected_theme);
        if (scheme) {
            console.log("value from get:" + scheme);
            $('head').append('<link rel="stylesheet" href="/bootstrap/' + scheme + '/bootstrap.min.css">');
        }
        else {
            scheme = this.default_theme;
            console.log("value from default:" + scheme);
            $('head').append('<link rel="stylesheet" href="/bootstrap/' + scheme + '/bootstrap.min.css">');
            this.put(this.selected_theme, scheme);
        }
    }
    setScheme(scheme) {
        this.put(this.selected_theme, scheme);
    }
    put(key, value) {
        localStorage.setItem(key, value);
    }
    get(key) {
        return localStorage.getItem(key);
    }
    remove(key) {
        localStorage.removeItem(key);
    }
}
var pickScheme = new PickScheme();
