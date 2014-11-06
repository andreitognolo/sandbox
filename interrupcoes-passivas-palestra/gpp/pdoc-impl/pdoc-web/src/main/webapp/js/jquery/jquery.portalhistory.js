(function($) {

	var MAX = 5;
	var urls = [];
	
	function portalHistoryPush(url) {
		urls.unshift(url);
		while(urls.length > MAX) {
			urls.pop();
		}
	}
	
	function search(params) {
		for(var i = 0; i < urls.length; i++) {
			var url = urls[i];
			for(var j = 0; j < params.length; j++) {
				var param = params[j];
				if(url.startsWith(param)) {
					return i;
				}
			}
		}
		return -1;
	}

	function portalHistoryBackLink(args) {
		if(!args || !args.length) {
			throw 'wrong';
		}
		if(urls.length > 1) {
			for(var i = 0; i < args.length; i++) {
				var url = urls[1];
				var arg = args[i];
				if(url.match('^' + arg + '.*$')) {
					return url;
				}
			}
		}
		return args[0];
	}
	
	function portalHistoryBack() {
		var link = portalHistoryBackLink(arguments);
		return $('<a class="btn btn-blue btn-voltar"/>').attr('href', link).text('Voltar');
	}
	
	$.portalHistoryPush = portalHistoryPush;
	$.portalHistory = urls;
	$.portalHistoryBack = portalHistoryBack;
	$.portalHistoryBackLink = portalHistoryBackLink;
	
	// $.portalBack('#Tarefas', '#Processos');
	
})(jQuery);

