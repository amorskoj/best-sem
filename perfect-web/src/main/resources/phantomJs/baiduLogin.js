var system = require('system');
var url = "http://www2.baidu.com";

var page = require('webpage').create();
page.settings.loadImages = false;
page.settings.userAgent = 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:33.0) Gecko/20100101 Firefox/33.0';
page.settings.resourceTimeout = 5000;

page.open(url, function (status) {
    if (status != 'success') {
        console.log("HTTP request failed!");
    } else {
        console.log(JSON.stringify(page.cookies));
    }

    page.close();
    phantom.exit();
});