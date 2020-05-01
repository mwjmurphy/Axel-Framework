export class RequestParams {
    constructor() {
        console.log("constructor for RequestParams");
    }
    getParam(key) {
        var urlParams = this.getParams();
        var value = urlParams.get(key);
        return value;
    }
    getParams() {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        return urlParams;
    }
}
