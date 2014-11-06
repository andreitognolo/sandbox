var page = require('webpage').create();

var url = phantom.args[0];
var token = phantom.args[1];
var exp = phantom.args[2];
var file = phantom.args[3];
if (token) {
	token = JSON.parse(token);
	phantom.addCookie(token);
}

// console.log('url', url);
// phantom.exit();

page.open(url, function(status) {
	page.viewportSize = { width: 480, height: 800 };
	page.paperSize = { format : 'a4', orientation : 'portrait', margin : '1cm' };
	page.render(file)
	phantom.exit();
});
