(function($) {

	function blocker() {
		return false;
	}

	$.fn.penable = function() {
		$(this).filter(':not(button,input,select,a)').removeClass('pdisabled');
		$(this).find('button,input,select,textarea').attr('disabled', false);
		$(this).filter('button,input,select,textarea').attr('disabled', false);
		$(this).find('button,input,select,textarea').attr('readonly', false);
		$(this).filter('button,input,select,textarea').attr('readonly', false);
		$(this).find('a').unbind('click', blocker);
		$(this).filter('a').unbind('click', blocker);
		return $(this);
	};

	$.fn.pdisable = function() {
		$(this).filter(':not(button,input,select,a)').addClass('pdisabled');
		$(this).find('button,input,select,textarea').attr('readonly', true);
		$(this).filter('button,input,select,textarea').attr('readonly', true);
		$(this).find('button,input,select,textarea').attr('disabled', true);
		$(this).filter('button,input,select,textarea').attr('disabled', true);
		$(this).find('a').bind('click', blocker);
		$(this).filter('a').bind('click', blocker);
		return $(this);
	};

	(function() {
		var sequence = 0;
		$.idgen = function() {
			var ret = sequence++;
			return ret;
		}
	})();

	$.fn.valstr = function() {
		return $.trim($(this).val());
	}
	
	$.fn.valint = function() {
		var ret = $(this).valstr();
		return ret == null ? null : parseInt(ret);
	}

	$.fn.vallong = function() {
		var ret = $(this).valstr();
		return ret == null || ret === '' ? null : Number(ret);
	}

	$.fn.valfloat = function() {
		var ret = $(this).valstr();
		return ret == null ? null : parseFloat(ret);
	}
	
	$.opand = function() {
		for ( var i = 0; i < arguments.length; i++) {
			if (!arguments[i]) {
				return false;
			}
		}
		return true;
	}
	
	$.opge = function(a, b) {
		return (a >= b);
	}
	
	$.opgt = function(a, b) {
		return (a > b);
	}
	
	$.oplt = function(a, b) {
		return (a < b);
	}
	
	$.ople = function(a, b) {
		return (a <= b);
	}
	
	$.fn.htmlstr = function (){
		return $.trim($(this).html());
	}
	
	$.fn.htmlint = function (){
		var ret = $(this).htmlstr();
		return ret == null ? null : parseInt(ret);
	}
})(jQuery);