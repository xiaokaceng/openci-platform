/**
 * 向导组件
 *
 */+ function($) {"use strict";

	// Wizard PUBLIC CLASS DEFINITION
	// ==============================

	var Wizard = function(element, options) {
		this.init(element, options);
	};

	Wizard.DEFAULTS = {
		easing : 'swing',
		ul : '.nav-tabs',
		items : '.items',
		swing : 'swing',
		speed : 400,
		step : 0,
		totalSteps : 0
	};

	Wizard.prototype.init = function(element, options) {
		var self = this;
		this.$element = $(element);
		this.options = $.extend({}, Wizard.DEFAULTS, options);
		this.ul = this.$element.find(this.options.ul);
		this.items = this.$element.find(this.options.items);
		this.pages = this.items.find('.page');
		this.nextBtn = this.$element.find('#nextBtn');
		this.prevBtn = this.$element.find('#prevBtn');
		this.complateBtn = this.$element.find('#complateBtn');
		this.nextBtn.off().on('click.bs.wizard', function(e) {
			e.preventDefault();
			var $this = $(this);
			var form = $(self.pages[self.options.step]).find('form');
			if (form.length > 0 && !Validator.Validate(form[0], 3)) {
				return;
			}
			if (self.options.step == 0) {
				var projectName = self.$element.find('#projectName');
				if (projectName.length == 0) {
					self.next(e);
					var action = $this.data('action');
					action && self.$element.trigger(action);
				} else {
					$.get('project/is-exist/' + projectName.val()).done(function(result) {
						if (result) {
							projectName.closest('.wizard').message({
								type : 'warning',
								content : '项目名称已经存在'
							});
							projectName.focus();
						}else{
							self.next();
							self.$element.trigger('step' + self.options.step);
						}
					});
				}
			} else if (self.options.step == 1) {
				if (self.$element.find('#modualGrid').getGrid().getAllItems().length == 0) {
					self.$element.find('#modualGrid').message({
						type : 'warning',
						content : '请添加模块'
					});
				}else{
					self.next();
					self.$element.trigger('step' + self.options.step);	
				}
			} else if (self.options.step == 3) {
				if (self.$element.find('#developerGrid').getGrid().getAllItems().length == 0) {
					self.$element.find('#developerGrid').message({
						type : 'warning',
						content : '请添加开发者'
					});
				}else if(self.$element.find('#developerGrid').find('[data-role="isLeader"].checked').length == 0){
					self.$element.find('#developerGrid').message({
						type : 'warning',
						content : '请选择Leader'
					});
				}else{
					self.next();
					self.$element.trigger('step' + self.options.step);
				}
			}else{
				self.next();
				self.$element.trigger('step' + self.options.step);
			}
		});
		this.prevBtn.off().hide().on('click.bs.wizard', $.proxy(function(e) {
			e.preventDefault();
			this.prev();
		}, this));
		this.complateBtn.off().hide().on('click.bs.wizard', $.proxy(function(e) {
			e && e.preventDefault();
			this.$element.trigger('complate');
		}, this));
	};

	Wizard.prototype.next = function() {
		var that = this;
		if (that.options.step == 0) {
			that.prevBtn.show();
		} else if (that.options.step == that.options.totalSteps - 2) {
			that.nextBtn.hide();
			that.complateBtn.show();
		}
		var page = $(this.pages[this.options.step]);
		var n = {
			left : -(page.position().left + page.outerWidth())
		};
		that.items.animate(n, that.options.speed, that.options.swing, function() {
			that.ul.find('li').eq(page.index() + 1).find('a').tab('show');
			that.options.step++;
		});
	};

	Wizard.prototype.prev = function(e) {
		var that = this;
		if (that.options.step != that.options.totalSteps - 2) {
			that.nextBtn.show();
			that.complateBtn.hide();
		}
		if (that.options.step == 1) {
			that.prevBtn.hide();
		}
		var page = $(this.pages[this.options.step]);
		var n = {
			left : page.outerWidth() - page.position().left
		};
		that.items.animate(n, that.options.speed, that.options.swing, function() {
			that.ul.find('li').eq(page.index() - 1).find('a').tab('show');
			that.options.step--;
			console.info(that.options.step)
		});
	};

	// Wizard PLUGIN DEFINITION
	// ========================

	var old = $.fn.wizard;

	$.fn.wizard = function(option) {
		return this.each(function() {
			var $this = $(this);
			var data = $this.data('bs.wizard');
			var options = typeof option == 'object' && option;

			if (!data)
				$this.data('bs.wizard', ( data = new Wizard(this, options)));
		});
	};

	$.fn.wizard.Constructor = Wizard;

	// BUTTON NO CONFLICT
	// ==================

	$.fn.wizard.noConflict = function() {
		$.fn.wizard = old;
		return this;
	};

}(window.jQuery);
