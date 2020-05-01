declare const $:any;        // make sure jquery is loaded
declare const moment:any;   // make sure momentjs is loaded

export class RequestParams {

    constructor () {
        console.log("constructor for RequestParams");
    }

    // get a parameter from the incominbg http request
    getParam(key: string): string {
        var urlParams: URLSearchParams = this.getParams();
        var value = urlParams.get(key);
        return value;
    }

    // get all parameter from incominbg http request
    getParams(): URLSearchParams {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        return urlParams;
    }
}