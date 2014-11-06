(function($) {

	function showPop(pop) {
		pop.find('.pop-head-controls').append('<button class="pop-close">X</button>');
		pop.find('.pop-close').click(function() {
			pop.unpops();
		});
		return pop;
	}

	function createPops(pop, me){
		me.show();
		pop.addClass('pop-opened');
		if (me.closest('html').length > 0) {
			me.before('<div class="pop-mark" />');
			me.data('pop-mark', me.prev('.pop-mark'));
		}
		pop.children('.pop-main').html(me);
		
		pop.find('textarea:first').focus();
		pop.find('input:first').focus();
		
		var ret = showPop(pop);
		return ret;
	}
	
	$(document).keyup(function(e) {
		if (e.keyCode == 27) {
			$('.pop').unpops();
		}
	});
	
	$.fn.popsBasic = function(header){
		var pop = $('body').prepend('<div class="pop"><div class="pop-overlay" /><div class="pop-main" /></div>').children('div.pop:first');
		var body = $('<div/>').addClass('modal-basic').addClass('pop-content');
		var popHeader = $('<div/>').addClass('pop-head').html('<h3>' + (header || 'Visualizar') +'</h3><div class="pop-head-controls"></div>');
		var content  = $('<div/>').addClass('modal-content').html($(this));
		var footer = $('<div/>').addClass('btn-group').addClass('btn-group-right').html('<button class="btn-sair btn btn-blue">Sair</button>');
		
		footer.find('button').click(function(){
			pop.unpops();
		});
		
		body.append(popHeader).append(content).append(footer);
		
		return createPops(pop, body);
	}
	
	$.fn.pops = function() {
		var code = '<div class="pop"><div class="pop-overlay" /><div class="pop-main" /></div>';
		var popups = $('body').children('.pop:last');
		var pop = null;
		if(popups.length) {
			pop = popups.after(code).next();	
		} else {
			pop = $('body').prepend(code).children('div.pop:first');
		}
		var me = $(this);
		me.addClass('pop-content');
		pop.prevAll('.pop').hide();
		return createPops(pop, me);
	}

	$.fn.unpops = function() {
		var me = $(this).closest('.pop');
		me.prev('.pop').show();
		var main = me.find(' > .pop-main').children();
		main.hide();
		main.removeClass('pop-opened');
		main.find('.pop-head-controls .pop-close').remove();
		var mark = main.data('pop-mark');
		if (mark) {
			mark.after(main);
			mark.remove();
		}
		me.remove();
	}

})(jQuery);