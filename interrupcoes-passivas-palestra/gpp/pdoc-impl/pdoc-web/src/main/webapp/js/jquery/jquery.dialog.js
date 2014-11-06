 (function($) {

	// type err|warn|info|success
	$.fn.dialog = function(msg, type, html)
	{
		type = type || 'info';
		var dialog = $(this).find('.dialog-group');
		if(!dialog.length) {
			dialog = $(this).parents().find('> .dialog-group');
		}
		if(!dialog.length) {
			throw '.dialog-group not found';
		}
		if(msg === undefined) {
			return dialog;
		}
		if(msg === null) {
			dialog.html('');
		}
		if(msg) {
			 html !== true
				msg = $('<span>' + msg + '</span>').text();
			return dialog.first().append('<li>').children('li:last').addClass(type).html(msg).click(function() { 
				$(this).hide('normal', function() { $(this).remove(); });
			});
		}
	};
	
})(jQuery);