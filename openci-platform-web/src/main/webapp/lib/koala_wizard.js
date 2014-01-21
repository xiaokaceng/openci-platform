/**
 * 向导组件
 * 
 */
+function ($) { 
	
	"use strict";

	  // Wizard PUBLIC CLASS DEFINITION
	  // ==============================
	
	  var Wizard = function (element, options) {
	    this.init(element,options);
	  };
	
	  Wizard.DEFAULTS = {
		  easing: 'swing',
		  ul: '.nav-tabs',
		  items: '.items',
		  swing: 'swing',
		  next: '[data-toggle="next"]',
		  prev: '[data-toggle="prev"]',
		  complete: '[data-toggle="complete"]',
		  speed: 400
	  };
	
	  Wizard.prototype.init = function (element, options) {
	  	  var self = this;
		  this.$element = $(element);
		  this.options  = $.extend({}, Wizard.DEFAULTS, options);
		  this.ul = this.$element.find(this.options.ul);
		  this.items = this.$element.find(this.options.items);
		  this.items.find(this.options.next).on('click.bs.wizard',function (e) {
			  e.preventDefault();
			  var form = $(e.currentTarget).closest('.page').find('form');
			  if(form.length > 0 && !Validator.Validate(form[0], 3)){
			  		return;
			  }
			  self.next(e);
			  var action = $(this).data('action');
			  action && self.$element.trigger(action);
	      });
	      this.items.find(this.options.prev).on('click.bs.wizard',$.proxy(function (e) {
			  e.preventDefault();
			  this.prev(e);
	      }, this));
	       this.items.find(this.options.complete).on('click.bs.wizard',$.proxy(function (e) {
			  e && e.preventDefault();
	      }, this));
	  };
	  
	  Wizard.prototype.next = function(e) {
		  var that = this;
		  var target = $(e.currentTarget);
		  var page = target.closest('.page');
		  var n = {left : - (page.position().left + page.outerWidth()) };
		  that.items.animate(n, that.options.speed, that.options.swing, function() {
			  that.ul.find('li').eq(page.index() + 1).find('a').tab('show');
	      });
	  };
  
	  Wizard.prototype.prev = function(e) {
		  var that = this;
		  var target = $(e.currentTarget);
		  var page = target.closest('.page');
		  var n = {left : page.outerWidth() - page.position().left};
		  that.items.animate(n, that.options.speed, that.options.swing, function() {
			  that.ul.find('li').eq(page.index() - 1).find('a').tab('show');
	      });
	 };
	

  // Wizard PLUGIN DEFINITION
  // ========================

  var old = $.fn.wizard;

  $.fn.wizard = function (option) {
    return this.each(function () {
      var $this   = $(this);
      var data    = $this.data('bs.wizard');
      var options = typeof option == 'object' && option;

      if (!data) $this.data('bs.wizard', (data = new Wizard(this, options)));
    });
  };

  $.fn.wizard.Constructor = Wizard;


  // BUTTON NO CONFLICT
  // ==================

  $.fn.wizard.noConflict = function () {
    $.fn.wizard = old;
    return this;
  };

}(window.jQuery);