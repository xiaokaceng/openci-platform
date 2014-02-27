var emailManager = {

	baseUrl : 'emailconfiguration/',
	dataGrid : null,
	username : null,
	password : null,
	smtpAddress : null,
	smtpPort : null,
	dialog : null,

	add : function(grid) {
		var self = this;
		self.dataGrid = grid;
		$.get('pages/cis/email-template.html').done(function(data) {
			self.init(data);
		});
	},

	update : function(grid, item) {
		var self = this;
		self.dataGrid = grid;
		$.get('pages/cis/email-template.html').done(function(data) {
			self.init(data, item);
			self.setData(item)
		});
	},

	del : function(grid, indexs) {
		var self = this;
		$.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			'type' : "Post",
			'url' : self.baseUrl + 'remove',
			'data' : JSON.stringify(indexs),
			'dataType' : 'json'
		}).done(function(data) {
			if (data.result) {
				grid.message({
					type : 'success',
					content : '删除成功'
				});
				$(this).modal('hide');
				initEmailGrid();
			} else {
				self.dialog.find('.modal-content').message({
					type : 'error',
					content : '删除失败'
				});
			}
		});
	},
	/**
	 * 初始化
	 */
	init : function(data, item) {
		var self = this;
		var dialog = $(data);
		self.dialog = dialog;
		dialog.find('.modal-header').find('.modal-title').html( item ? '修改配置' : '添加配置');
		self.username = dialog.find('#username');
		self.password = dialog.find('#password');
		self.smtpAddress = dialog.find('#smtpAddress');
		self.smtpPort = dialog.find('#smtpPort');
		dialog.find('#save').on('click', function() {
			self.save(item);
		}).end().modal({
			keyboard : false
		}).on({
			'hidden.bs.modal' : function() {
				$(this).remove();
			},
			'complete' : function() {
				self.dataGrid.message({
					type : 'success',
					content : '保存成功'
				});
				$(this).modal('hide');
				initEmailGrid();
			}
		});
	},

	setData : function(item) {
		var self = this;
		self.username.val(item.username);
		self.password.val(item.password);
		self.smtpAddress.val(item.smtpAddress);
		self.smtpPort.val(item.smtpPort);
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
				self.dialog.find('.modal-content').message({
					type : 'error',
					content : data.result
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
		var username = self.username;
		var password = self.password;
		var smtpAddress = self.smtpAddress;
		var smtpPort = self.smtpPort;
		if (!Validation.notNull(dialog, username, username.val().replace(/\s/g,""), '请输入用户名')) {
			return false;
		}
		if (!Validation.notNull(dialog, password, password.val().replace(/\s/g,""), '请输入密码')) {
			return false;
		}
		if (!Validation.notNull(dialog, smtpAddress, smtpAddress.val().replace(/\s/g,""), '请输入SMTP地址')) {
			return false;
		}
		if (!Validation.notNull(dialog, smtpPort, smtpPort.val(), '请输入SMTP端口')) {
			return false;
		}
		if (!Validation.checkByRegExp(dialog, smtpPort, '^[0-9]*[1-9][0-9]*$', smtpPort.val(), '端口只能为正整数')) {
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
		data['username'] = self.username.val().replace(/\s/g,"");
		data['password'] = self.password.val().replace(/\s/g,"");
		data['smtpAddress'] = self.smtpAddress.val().replace(/\s/g,"");
		data['smtpPort'] = self.smtpPort.val();
		if (item) {
			data['id'] = item.id;
		}
		return data;
	},
	setDefaultEmail : function(id, index) {
		var emailGrid = $('#emailGrid');
		$.get(this.baseUrl + 'setdefault/' + id).done(function(data) {
			if (data.result) {
				emailGrid.message({
					type : 'success',
					content : '设置成功'
				});
				initEmailGrid();
			} else {
				emailGrid.message({
					type : 'error',
					content : '设置失败'
				});
			}
		});
	},
	testUsable : function(id, index) {
		var emailGrid = $('#emailGrid');
		$.get(this.baseUrl + 'setusable/' + id).done(function(data) {
			if (data.result) {
				emailGrid.message({
					type : 'success',
					content : '该配置可用'
				});
				initEmailGrid();
			} else {
				emailGrid.message({
					type : 'error',
					content : '该配置不可用'
				});
			}
		});
	}
}
