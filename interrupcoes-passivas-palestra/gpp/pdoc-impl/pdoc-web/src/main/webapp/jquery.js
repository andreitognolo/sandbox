define(function() {

	function loadPlugins($, req, onload) {
		var plugins = [];
		plugins.push('js/jquery/jquery.globalscanner');
		plugins.push('js/jquery/jquery.set');
		plugins.push('js/jquery/jquery.pops');
		plugins.push('js/jquery/jquery.selectall');
		plugins.push('js/jquery/ext/jquery.ba-hashchange');
		plugins.push('js/jquery/ext/jquery.base64');
		plugins.push('js/jquery/ext/jquery.cookie');
		plugins.push('js/jquery/jqueryui/jqueryui');
		plugins.push('js/jquery/jquery.portal');
		plugins.push('js/jquery/jquery.loading');
		plugins.push('js/jquery/jquery.portalserviceloading');
		plugins.push('js/jquery/jquery.selectall');
		plugins.push('js/jquery/jquery.portalhistory');
		plugins.push('js/ext/moment');
		plugins.push('js/jquery/jquery-sortable-min.js');

		req(plugins, function() {
			onload($);
		});
	}

	return {
		load : function(name, req, onload, config) {
			req([ 'js/jquery/jquery' ], function() {
				jQuery.noConflict();
				loadPlugins(jQuery, req, onload);
			});
		}
	}
});
