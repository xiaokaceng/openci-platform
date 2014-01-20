var moduleManager = {

	baseUrl : 'project/',
	dataGrid : null,
	project : null,
	modualName : null,
	basePackage : null,
	moduleType : null,
	functionsGrid : null,
	dependenciesGrid : null,
	dialog : null,

	add : function(project, grid) {
		var self = this;
		self.dataGrid = grid;
		self.project = project;
		$.get('pages/cis/modual-template.html').done(function(data) {
			self.init(data);
		});
	},
	update : function(project, grid, item) {
		var self = this;
		self.dataGrid = grid;
		self.project = project;
		$.get('pages/cis/module-template.html').done(function(data) {
			self.init(data, item);
			self.setData(item)
		});
	},
	del : function(grid, items) {

	},
	/**
	 * 初始化
	 */
	init : function(data, item) {
		var self = this;
		var dialog = $(data);
		self.dialog = dialog;
		dialog.find('.modal-header').find('.modal-title').html( item ? '添加模块' : '添加模块信息');
		self.modualName = dialog.find('#modualName');
		self.basePackage = dialog.find('#basePackage');
		self.moduleType = dialog.find('#moduleType');
		self.functionsGrid = dialog.find('#functionsGrid');
		self.dependenciesGrid = dialog.find('#dependenciesGrid');
		dialog.find('#save').on('click', function() {
			self.save(item);
		}).end().modal({
			keyboard : false
		}).on({
			'hidden.bs.modal' : function() {
				$(this).remove();
			},
			'shown.bs.modal' : function() {
				self.initModualSelect();
			},
			'complete' : function() {
				$('body').message({
					type : 'success',
					content : '保存成功'
				});
				$(this).modal('hide');
				self.dataGrid.grid('refresh');
			}
		});
		//兼容IE8 IE9
		if (window.ActiveXObject) {
			if (parseInt(navigator.userAgent.toLowerCase().match(/msie ([\d.]+)/)[1]) < 10) {
				dialog.trigger('shown.bs.modal');
			}
		}
	},
	initModualSelect : function() {
		var self = this;
		self.moduleType.select({
			title : '请选择',
			contents : [{
				title : '基础实施层',
				value : 'infra'
			}, {
				title : '领域层',
				value : 'bizModel'
			}, {
				title : '应用层接口',
				value : 'applicationInterface'
			}, {
				title : '应用层实现',
				value : 'applicationImpl'
			}, {
				title : '视图层',
				value : 'war'
			}]
		}).on('change', function() {
			self.initGrid($(this).getValue());
		});
	},

	initGrid : function(moduleType) {
		var self = this;
		$.get(self.baseUrl + 'get-functions?moduleType=' + moduleType).done(function(data) {
			var functions = [];
			for (functionName in data.functions) {
				functions.push({
					functionName : functionName,
					functionDesc : data.functions[functionName]
				});
			}
			var columns = [{
				title : '功能名称',
				name : 'functionName',
				width : 150
			}, {
				title : '功能描述',
				name : 'functionDesc',
				width : 150
			}];
			self.functionsGrid.empty().data('koala.grid', null).grid({
				identity : 'functionName',
				columns : columns,
				isShowPages : false,
				isUserLocalData : true,
				localData : functions
			});
		});
		var param = {};
		var project = self.project;
		console.info(project)
		delete project.scanPackages;
		delete project.packageName;
		$.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
		    'type': "Post",
		    'url': self.baseUrl + 'get-dependables',
		    'data': JSON.stringify(project),
		    'dataType': 'json'
		})
		
	},

	setData : function(item) {
		var self = this;

	},
	/*
	 *   保存数据 id存在则为修改 否则为新增
	 */
	save : function(item) {
		var self = this;
		if (!self.validate()) {
			return false;
		}
		var url = self.baseUrl + 'create';
		if (item) {
			url = self.baseUrl + 'update';
		}
		$.post(url, self.getAllData(item)).done(function(data) {
			if (data.result) {
				self.dialog.trigger('complete');
			} else {
				self.dialog.message({
					type : 'error',
					content : data.actionError
				});
			}
		});
	},
	/**
	 * 数据验证
	 */
	validate : function() {
		var self = this;
		var dialog = self.dialog;
		var name = self.name;
		var email = self.email;
		var developerId = self.developerId;
		if (!Validation.notNull(dialog, developerId, developerId.val(), '请输入开发者ID')) {
			return false;
		}
		if (!Validation.notNull(dialog, name, name.val(), '请输入用户名称')) {
			return false;
		}
		if (!Validation.notNull(dialog, email, email.val(), '请输入邮箱')) {
			return false;
		}
		if (!Validation.email(dialog, email, email.val(), '邮箱格式不正确')) {
			return false;
		}
		return true;
	},
	/*
	 *获取表单数据
	 */
	getAllData : function(item) {
		var self = this;
		var data = {};
		if (item) {
			data = item;
		}
		data['developerId'] = self.developerId.val();
		data['name'] = self.name.val();
		data['email'] = self.email.val();
		if (item) {
			data['id'] = item.id;
		}
		return data;
	}
}
