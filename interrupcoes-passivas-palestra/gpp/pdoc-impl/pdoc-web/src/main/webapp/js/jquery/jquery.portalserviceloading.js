(function($) {

	function panel() {
		var ret = $('.bgservicequeue');
		if (!ret.length) {
			ret = $('<div class="bgservicequeue">loading <span class="queue"/><span class="cancel">X</span></div>');
			$('body').append(ret);
			ret.find('.cancel').click(function() {
				$.ajaxlimit.halt();
			});
			ret.hide();
		}
		return ret;
	}

	function bgservice(evt, reqs) {
		var p = panel();
		var num = reqs.pends.length + reqs.running.l;
		p.find('.queue').text(num);
		if(num > 10) {
			p.show();
		} else {
			p.hide();
		}
	}

	$(window).ready(function() {
		$(document).bind('bgservicequeue.portal', bgservice);
		$(document).bind('bgservicecomplete.portal', bgservice);
		$(document).bind('bgservicehalt.portal', bgservice);
	});

})(jQuery);