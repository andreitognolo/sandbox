(function($) {

	jQuery.fn.center = function() {
		this.css("position", "absolute");
		this.css("top", ($(window).height() - this.outerHeight()) / 2.5
				+ $(window).scrollTop() + "px");
		this.css("left", ($(window).width() - this.outerWidth()) / 2
				+ $(window).scrollLeft() + "px");
		return this;
	}

	var loading = 0;

	function loadingPanel() {
		var loading = $('.loading');
		if (!$('.loading').length) {
			loading = $('<div class="loading"><div class="overlay" /><div class="load" /></div>');
			loading.hide();
		}
		$('body').append(loading);
		$('.loading .load').center();
		return loading;
	}

	function show() {
		var loading = loadingPanel();
		loading.show();
	}

	function hide() {
		loadingPanel().hide();
	}

	function unwrite(opts) {
		if (opts.loadingMessage) {
			opts.loadingMessage.css('color', '#ccc')
		}
	}

	function write(opts) {
		var panel = $(".loading .load");
		var msg = $('<p>').text(opts.desc || '...');
		panel.append(msg);
		opts.loadingMessage = panel.find('p:last');
	}

	function pushLoading(opts) {
		opts = opts || {};
		if (!opts.loading) {
			return;
		}
		loading++;
		if (loading == 1) {
			show();
			$('.loading .load').html('');
		}
		if (opts.desc) {
			write(opts);
		}
	}

	function popLoading(opts) {
		opts = opts || {};
		if (!opts.loading) {
			return;
		}
		if (loading == 1) {
			hide();
		}
		if (loading <= 0) {
			throw 'popLoading without pushLoading: ' + loading;
		}
		loading--;
		unwrite(opts);
	}

	$.pushLoading = pushLoading;
	$.popLoading = popLoading;
	$.loading = function() {
		return loading;
	};

	$.ajaxSetup({
		loading : true
	});

	$(window).ready(function() {

		$(document).ajaxSend(function(evt, xhr, ajax) {
			pushLoading(ajax);

		});
		$(document).ajaxComplete(function(evt, xhr, ajax) {
			popLoading(ajax);
		});
	});

})(jQuery);