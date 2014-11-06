(function($) {

	NoobScanner = {};
	NoobScanner.ignore = [ '_FirebugCommandLine', 'jQuery[0-9]*', '\\$', 'google', 'PRUM_EPISODES', 'ready', 'def', '_xdc_', 'encodeHTML', 'moment' ];

	var getGlobals = function() {
		var ret = {};
		for ( var i in window) {
			ret[i] = window[i];
		}
		return ret;
	}

	var removeAllObj = function(from, list) {
		for ( var i in list) {
			delete(from[i]);
		}
	}

	var removeAllArray = function(from, list) {
		var removes = [];
		for ( var i in list) {
			var regex = list[i];
			for(var j in from) {
				if(j.match(regex)) {
					removes.push(j);
				}
			}
		}
		for(var i in removes) {
			delete(from[removes[i]])
		}
	}

	NoobScanner.check = function() {
		var list = getGlobals();
		removeAllObj(list, NoobScanner.initial);
		removeAllArray(list, NoobScanner.ignore)
		var print = '';
		for(var i in list) {
			print += i + ', '
		}
		if(print.length) {
			if ( typeof console !== "undefined" && console.warn ) {
				console.warn('Globals Detected', print);
			}
		}
	}

	NoobScanner.initial = getGlobals();

	var scan = function() {
		NoobScanner.check();
		setTimeout(scan, 15000);
	}

	$(window).ready(function() {
		scan();
	});

})(jQuery);
