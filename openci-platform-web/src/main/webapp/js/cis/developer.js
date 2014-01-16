var developerManager = {
	
	baseUrl: 'developer/',
	dataGrid: null,
	name: null,
	email: null,
	dialog: null,
	
	add: function(grid){
		var self = this;
		self.dataGrid = grid;
		$.get('pages/cis/developer-template.html').done(function(data){
			self.init(data);
		});
	},
	
	/**
	 * 初始化
	 */
	init: function(data, id){
		var self = this;
		var dialog = $(data);
		self.dialog = dialog;
		dialog.find('.modal-header').find('.modal-title').html(id ? '修改开发者信息':'添加开发者');
		self.name = dialog.find('#name');
        self.email = dialog.find('#email');
		dialog.find('#save').on('click',function(){
			self.save(id);
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
	
	/*
	*   保存数据 id存在则为修改 否则为新增
	 */
	save: function(id){
		var self = this;
		if(!self.validate(id)){
			return false;
		}
		var url = self.baseUrl + 'create';
		if(id){
			url =  self.baseUrl + 'update';
		}
		$.post(url, self.getAllData(id)).done(function(data){
			if(data.result == 'success'){
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
	validate: function(item){
		var self = this;
		var dialog = self.dialog;
		var name = self.name;
		var email = self.email;
		if(!Validation.notNull(dialog, name, name.val(), '请输入用户名称')){
			return false;
		}
		if(!Validation.notNull(dialog, email, email.val(), '请输入邮箱')){
			return false;
		}
		if(!Validation.email(dialog, email, email.val(), '邮箱格式不正确')){
			return false;
		}
		return true;
	},
	/*
	*获取表单数据
	 */
	getAllData: function(id){
		var self = this;
		var data = {};
		data['name'] = self.name.val();
		data['email'] = self.email.val();
		if(id){
			data['id'] = id;	
		}
		return data;
	}
}
