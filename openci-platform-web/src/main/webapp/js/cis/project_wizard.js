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
			var $this = $(this);
			if($this.hasClass('disabled')){
				return;
			}
			$(this).addClass('disabled');
			e.preventDefault();
			var $this = $(this);
			var form = $(self.pages[self.options.step]).find('form');
			if (form.length > 0 && !Validator.Validate(form[0], 3)) {
				$this.removeClass('disabled');
				return;
			}
			if (self.options.step == 0) {
				var projectName = self.$element.find('#projectName');
				if (projectName.length == 0) {
					self.next(e);
					var action = $this.data('action');
					action && self.$element.trigger(action);
				} else {
					var reg = /[\u0391-\uFFE5]+/;
			    	if (reg.test(projectName.val())) {
			    		showErrorMessage($('body'), projectName, '项目名称不合法');
			    		$this.removeClass('disabled');
			    		return;
			    	}
					$.get('project/is-exist/' + projectName.val()).done(function(result) {
						if (result) {
							projectName.closest('.wizard').message({
								type : 'warning',
								content : '项目名称已经存在'
							});
							projectName.focus();
							$this.removeClass('disabled');
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
					$this.removeClass('disabled');
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
					$this.removeClass('disabled');
				}else if(self.$element.find('#developerGrid').find('[data-role="isLeader"].checked').length == 0){
					self.$element.find('#developerGrid').message({
						type : 'warning',
						content : '请选择Leader'
					});
					$this.removeClass('disabled');
				}else{
					self.next();
					self.$element.trigger('step' + self.options.step);
				}
			}else{
				self.next();
				self.$element.trigger('step' + self.options.step);
			}
		});
		this.prevBtn.off().hide().on('click.bs.wizard', function(e) {
			var $this = $(this);
			if($this.hasClass('disabled')){
				return;
			}
			$(this).addClass('disabled');
			e.preventDefault();
			self.prev();
		});
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
			$(self.nextBtn).removeClass('disabled');
		});
	};

	Wizard.prototype.prev = function(e) {
		var self = this;
		if (self.options.step != self.options.totalSteps - 2) {
			self.nextBtn.show();
			self.complateBtn.hide();
		}
		if (self.options.step == 1) {
			self.prevBtn.hide();
		}
		var page = $(self.pages[self.options.step]);
		var n = {
			left : page.outerWidth() - page.position().left
		};
		self.items.animate(n, self.options.speed, self.options.swing, function() {
			self.ul.find('li').eq(page.index() - 1).find('a').tab('show');
			self.options.step--;
			$(self.prevBtn).removeClass('disabled');
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
