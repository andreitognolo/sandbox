(function($) {

	// Form functions
	$.form = 
	{
		// Focus first input
		focusFirst : function()
		{
			$('input:text:visible:first').focus();
		}
	};

})(jQuery);