require([ 'jquery!' ], function($) {

	$(window).hashchange(function() {
		var hash = this.location.hash || '#';
		hash = $.trim(hash);
		if (!hash || hash == '#') {
			this.location.hash = '#PaginaA';
			return;
		}
		$.portalHistoryPush(hash);
		hash = hash.substring(1);
		hash = hash.split('?')[0];

		require([ 'comp!pdoc/page/' + hash ], function(page) {
			if ($.ajaxlimit) {
				$.ajaxlimit.halt();
			}
			page.open();
		});
	});

	$.ajaxSetup({
		error : function(xhr, status, error) {
			(function(opts, xhr, status, error) {
				console.log(error);
				require([ 'comp!pdoc/template/errorPopup', 'js/util/message' ], function(errorPopup, message) {
					if (!opts.jsonError || !opts.jsonError.element || opts.jsonError.element.restart) {
						errorPopup.show(opts.jsonError);
					} else {
						message.error(opts.jsonError.element.msg);
					}
				});

			})(this, xhr, status, error);
		}
	});

	function adapt(opts, func, jqXHR) {
		opts.success = function(resp) {
			if (resp && resp.error) {
				if (opts.error) {
					opts.jsonError = resp;
					opts.error(jqXHR, 'error', resp.errorText);
				}
				return;
			}
			if (func && typeof func == 'function') {
				func.apply(opts, arguments);
			}
		}
	}

	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		if (!options.crossDomain) {
			if (options.url.indexOf('error=json') < 0) {
				if (options.url.indexOf('?') < 0) {
					options.url += '?error=json';
				} else {
					options.url += '&error=json'
				}
			}
			adapt(options, options.success, jqXHR);
		}
	});

	$.ajaxPrefilter(function(options, originalOptions, jqXHR) {
		var isNotCrossDomain = !options.crossDomain;
		var isHTTPGet = options.type == 'GET';
		var isService = options.url.substring(0, 1) === 's';

		if (isNotCrossDomain && isHTTPGet && isService) {
			var decoded = decodeURI(options.data);

			for (var i = 0; i < decoded.length; i++) {
				if (decoded.charCodeAt(i) > 127) {
					options.type = 'POST';
				}
			}
		}

	});

	function keepSessionAlive() {
		setTimeout(function() {
			$.ajax({
				type : 'POST',
				url : 's/Ping/ping',
				complete : keepSessionAlive,
				loading : false
			});
		}, 60 * 1000 * 10);
	}

	$(window).ready(function() {
		$(window).hashchange();
		keepSessionAlive();
		window.ready = true;
	});

});
