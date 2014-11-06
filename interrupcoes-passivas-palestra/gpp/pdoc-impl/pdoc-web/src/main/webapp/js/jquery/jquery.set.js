(function($) {

	function Set(args) {
		this.elements = {};
		this.addAll(args)
	}

	function add() {
		this.addAll(arguments);
	}

	function addAll(list) {
		for ( var i = 0; i < list.length; i++) {
			var key = '' + list[i];
			this.elements[key] = list[i];
		}
	}

	function toArray() {
		var array = [];
		for ( var i in this.elements) {
			array.push(this.elements[i]);
		}
		return array;
	}

	function createSet() {
		return new Set(arguments);
	}

	Set.prototype.add = add;
	Set.prototype.addAll = addAll;
	Set.prototype.toArray = toArray;
	$.createSet = createSet;

})(jQuery);