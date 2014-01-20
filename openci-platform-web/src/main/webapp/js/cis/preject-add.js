$(function() {
	var projectDto = {};
	var projectAdd = $('.project-add');
	projectAdd.find('.items').width(5 * $('#content').width());

	projectAdd.wizard().on({
		'step1' : function() {
			var $this = $(this);
			var projectName = $this.find('#projectName').val();
			var groupId = $this.find('#groupId').val();
			var artifactId = $this.find('#artifactId').val();
			var version = $this.find('#version').val();
			var dbProtocol = $this.find('#dbProtocolValue').val();
			var mvcProtocol = $this.find('#mvcProtocolValue').val();
			projectDto.projectName = projectName;
			var projectForCreate = {};
			projectForCreate.groupId = groupId;
			projectForCreate.artifactId = artifactId;
			projectForCreate.version = version;
			projectForCreate.dbProtocol = dbProtocol;
			projectForCreate.mvcProtocol = mvcProtocol;
			projectDto.projectForCreate = projectForCreate;
			initModuleGrid([]);
		}
	});

	projectAdd.find('#projectName').on('keyup', function() {
		var projectName = $(this).val();
		if (projectName && projectName.length > 0) {
			projectAdd.find('#artifactId').val($(this).val());
		}
	});

	projectAdd.find('#dbProtocol').select({
		title : '请选择',
		contents : [{
			title : 'JPA',
			value : 'JPA',
			selected : true
		}, {
			title : 'Mybatis',
			value : 'Mybatiss'
		}]
	}).on('change', function() {
		$('#dbProtocolValue').val($(this).getValue());
	}).trigger('change');

	projectAdd.find('#mvcProtocol').select({
		title : '请选择',
		contents : [{
			title : 'Struts2MVC',
			value : 'Struts2MVC'
		}, {
			title : 'SpringMVC',
			value : 'SpringMVC',
			selected : true
		}]
	}).on('change', function() {
		$('#mvcProtocolValue').val($(this).getValue());
	}).trigger('change');

	var selectSubSystem = projectAdd.find('.select-sub-system');
	selectSubSystem.find('.checker span').on('click', function() {
		$(this).toggleClass('checked');
	});
	selectSubSystem.find('#cacheType').select({
		title : '请选择',
		contents : [{
			title : 'EhCache',
			value : 'ehcache',
			selected : true
		}, {
			title : 'Memcached',
			value : 'memcached'
		}]
	}).on('change', function() {
		$('#cacheTypeValue').val($(this).getValue());
	}).trigger('change');

	selectSubSystem.find('#monitorType').select({
		title : '请选择',
		contents : [{
			title : '本地',
			value : 'local'
		}, {
			title : '分布式',
			value : 'distributed',
			selected : true
		}]
	}).on('change', function() {
		$('#monitorTypeValue').val($(this).getValue());
	}).trigger('change');

	var toolColumns = [{
		title : '工具名',
		name : 'name',
		width : 250
	}, {
		title : '工具地址',
		name : 'serviceUrl',
		width : 'auto'
	}];
	$.get('toolconfiguration/get-all-usable').done(function(data) {
		projectAdd.find('#toolsGrid').grid({
			identity : 'id',
			columns : toolColumns,
			isShowPages : false,
			isUserLocalData : true,
			localData : data
		}).on({
			'complate' : function() {
				var grid = $(this);
				grid.find('.isUseCas span').on('click', function(e) {
					e.preventDefault();
					e.stopPropagation();
					var $this = $(this);
					$this.toggleClass('checked');
					if ($this.hasClass('checked')) {
						var $tr = $this.closest('tr');
						if (!$tr.hasClass('success')) {
							$tr.click();
						}
					}
				});
				grid.find('.isUseCas').closest('td').find('input').on('click', function(e) {
					e.preventDefault();
					e.stopPropagation();
				});
			}
		})
	});

	var selectedDevelopers = {};
	var developerColumns = [{
		title : '开发者ID',
		name : 'developerId',
		width : 150
	}, {
		title : '用户名称',
		name : 'name',
		width : 150
	}, {
		title : '邮箱',
		name : 'email',
		width : 'auto'
	}];

	projectAdd.find('#developerGrid').grid({
		identity : 'id',
		columns : developerColumns,
		querys : [{
			title : '开发者ID',
			value : 'developerId'
		}, {
			title : '姓名',
			value : 'name'
		}, {
			title : '邮箱',
			value : 'email'
		}],
		url : 'developer/pagingquery'
	}).on('selectedRow', function(e, result) {
		var data = result.item;
		if (result.checked) {
			selectedDevelopers[data.id] = data;
		} else {
			delete selectedDevelopers[data.id];
		}
		console.info(selectedDevelopers)
	});

	var save = function() {

	};

	var initModuleGrid = function(data) {
		var columns = [{
			title : '模块名称',
			name : 'moduleName',
			width : 150
		}, {
			title : '包路径',
			name : 'basePackage',
			width : 250
		}, {
			title : '模块类型',
			name : 'moduleType',
			width : 120,
			render : function(item, name, index) {
				switch(item[name]) {
					case 'infra':
						return '基础实施层';
						break;
					case 'bizModel':
						return '领域层';
						break;
					case 'applicationInterface':
						return '应用层接口';
						break;
					case 'applicationImpl':
						return '应用层实现';
						break;
					case 'war':
						return '视图层';
						break;
					default:
						return '';
				}
			}
		}, {
			title : '模块依赖',
			name : 'dependencies',
			width : 250,
			render : function(item, name, index) {
				return item[name].join(',');
			}
		}, {
			title : '功能依赖',
			name : 'functions',
			width : 'auto',
			render : function(item, name, index) {
				return item[name].join(',');
			}
		}];
		var buttons = [{
			content : '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>',
			action : 'add'
		}, {
			content : '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>',
			action : 'modify'
		}, {
			content : '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>',
			action : 'delete'
		}, {
			content : '<button class="btn btn-info" type="button"><span class="glyphicon glyphicon-ok"><span>使用默认模块</button>',
			action : 'useDefaultModule'
		}];
		projectAdd.find('#modualGrid').off().empty().data('koala.grid', null).grid({
			identity : 'moduleName',
			buttons : buttons,
			columns : columns,
			isShowPages : false,
			isUserLocalData : true,
			localData : data
		}).on({
			'add' : function() {
				console.info(projectDto)
				moduleManager.add(projectDto.projectForCreate, $(this));
			},
			'modify' : function(e, data) {
				var indexs = data.data;
				var $this = $(this);
				if (indexs.length == 0) {
					$this.message({
						type : 'warning',
						content : '请选择一条记录进行修改'
					});
					return;
				}
				if (indexs.length > 1) {
					$this.message({
						type : 'warning',
						content : '只能选择一条记录进行修改'
					});
					return;
				}
				moduleManager.update(projectDto.projectForCreate, $this, data.item[0]);
			},
			'delete' : function(e, data) {
				var indexs = data.data;
				var $this = $(this)
				if (indexs.length == 0) {
					$this.message({
						type : 'warning',
						content : '请选择要操作的记录'
					});
					return;
				}
				$this.confirm({
					content : '确定要删除所选记录吗?',
					callBack : function() {
						moduleManager.del($this, data.item);
					}
				});
			},
			'useDefaultModule': function(){
				var param = {};
				param['projectName'] = projectDto.projectName;
				param['projectForCreate.artifactId'] = projectDto.projectForCreate.artifactId;
				param['projectForCreate.dbProtocol'] = projectDto.projectForCreate.dbProtocol;
				param['projectForCreate.groupId'] = projectDto.projectForCreate.groupId;
				param['projectForCreate.mvcProtocol'] = projectDto.projectForCreate.mvcProtocol;
				param['projectForCreate.version'] = projectDto.projectForCreate.version;
				$.post('project/generate-default-modules', param).done(function(result) {
					projectDto = result;
					initModuleGrid(projectDto.projectForCreate.module);
				});
			}
		});
	}
});
