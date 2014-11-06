(function($) {

	function bind(root) {
		root.find('.row-select-all').click(function() {
			root.selectAll($(this).prop('checked'));
		});
	    root.find('.row-selectable').click(function() {
	    	var checked = !root.find('.row-selectable:not(:checked)').length;
    		root.find('.row-select-all').prop('checked', checked);
	    });
	}
	
	$.fn.selectAll = function(select) {
		if(arguments.length) {
			select = !!select;
			$(this).each(function() {
				var input = $(this).find('.row-selectable');
				if (!input.prop('disabled')) input.prop('checked', !!select);
			});
			return $(this);
		}
		$(this).each(function() {
			bind($(this));
		});
		return $(this);
	};

})(jQuery);