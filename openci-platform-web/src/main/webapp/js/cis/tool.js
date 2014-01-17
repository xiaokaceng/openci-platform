var toolManager = {
	
	baseUrl: 'toolconfiguration/',
	dataGrid: null,
	name: null,
	toolType: null,
	serviceUrl: null,
	username: null,
	password: null,
	dialog: null,
	developerId: null,
	
	add: function(grid){
		var self = this;
		self.dataGrid = grid;
		$.get('pages/cis/tool-template.html').done(function(data){
			self.init(data);
		});
	},
	
	update: function(grid, item){
		var self = this;
		self.dataGrid = grid;
		$.get('pages/cis/tool-template.html').done(function(data){
			self.init(data, item);
			self.setData(item)
		});
	},
	
	del: function(grid, items){
		var self = this;
		$.ajax({
		    headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
		    'type': "post",
		    'url': self.baseUrl + 'abolish',
		    'data': JSON.stringify(items[0]),
		    'dataType': 'json'
		 }).done(function(data){
			if(data.result){
				$('body').message({
						type: 'success',
						content: '删除成功'
					});
					$(this).modal('hide');
					grid.grid('refresh');
			}else{
				self.dialog.message({
					type: 'error',
					content: data.actionError
				});
			}
		});
	},
	/**
	 * 初始化
	 */
	init: function(data, item){
		var self = this;
		var dialog = $(data);
		self.dialog = dialog;
		dialog.find('.modal-header').find('.modal-title').html(item ? '修改工具配置':'添加工具');
		self.name = dialog.find('#name');
		self.toolType = dialog.find('#toolType');
        self.serviceUrl = dialog.find('#serviceUrl');
        self.username = dialog.find('#username');
        self.password = dialog.find('#password');
        $.get(self.baseUrl + 'get-tool-type').done(function(data){
        	var contents = [];
        	for(type in data){
        		contents.push({title:type, value:type});
        	}
        	self.toolType.select({
        		title: '请选择',
        		contents: contents
        	});
        	if(item){
        		self.toolType.setValue(item.toolType).attr('disabled', true);
        	}
        });
		dialog.find('#save').on('click',function(){
			self.save(item);
		}).end().modal({
			keyboard: false
		}).on({
				'hidden.bs.modal': function(){
					$(this).remove();
				},
				'complete': function(){
					$('body').message({
						type: 'success',
						content: '保存成功'
					});
					$(this).modal('hide');
					self.dataGrid.grid('refresh');
				}
		});
	},
	
	setData: function(item){
		var self = this;
		self.name.val(item.name);
		self.serviceUrl.val(item.serviceUrl);
		self.username.val(item.username);
		self.password.val(item.password);
	},
	/*
	*   保存数据 id存在则为修改 否则为新增
	 */
	save: function(item){
		var self = this;
		if(!self.validate()){
			return false;
		}
		var url = self.baseUrl + 'create';
		if(item){
			url =  self.baseUrl + 'update';
		}
		$.post(url, self.getAllData(item)).done(function(data){
			if(data.result){
				self.dialog.trigger('complete');
			}else{
				self.dialog.message({
					type: 'error',
					content: data.actionError
				});
			}
		});
	},
	/**
	 * 数据验证
	 */
	validate: function(){
		var self = this;
		var dialog = self.dialog;
		var name = self.name;
		var toolType = self.toolType;
		var serviceUrl = self.serviceUrl;
		var username = self.username;
		var password = self.password;
		if(!Validation.notNull(dialog, name, name.val(), '请输入工具名')){
			return false;
		}
		if(!Validation.notNull(dialog, toolType, toolType.getValue(), '请选择工具')){
			return false;
		}
		if(!Validation.notNull(dialog, serviceUrl, serviceUrl.val(), '请输入工具地址')){
			return false;
		}
		if(!Validation.notNull(dialog, username, username.val(), '请输入用户名')){
			return false;
		}
		if(!Validation.notNull(dialog, password, password.val(), '请输入密码')){
			return false;
		}
		return true;
	},
	/*
	*获取表单数据
	 */
	getAllData: function(item){
		var self = this;
		var data = {};
		if(item){
			data = item;
		}
		data['name'] = self.name.val();
		data['toolType'] = self.toolType.getValue();
		data['serviceUrl'] = self.serviceUrl.val();
		data['username'] = self.username.val();
		data['password'] = self.password.val();
		if(item){
			data['id'] = item.id;	
		}
		return data;
	}
}
