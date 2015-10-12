console.log('Loading a web page');
var page = require('webpage').create();
var url = 'http://localhost:8000';

setInterval(function() {

  page.open(url, function (status) {
    console.log('generating');
    page.render('report.pdf');
  });

}, 500);