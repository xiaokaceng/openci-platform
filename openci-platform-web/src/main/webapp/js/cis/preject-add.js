$(function() {
	var projectAdd = $('.project-add');
	projectAdd.find('.items').width(5 * $('#content').width());
	projectAdd.wizard();

	projectAdd.find('.checker span').on('click', function() {
		$(this).toggleClass('checked');
	});

	projectAdd.find('#repositoryImplate').select({
		title : '请选择',
		contents : [{
			title : 'JPA',
			value : 'jpa',
			selected : true
		}, {
			title : 'Mybatis',
			value : 'mybatis'
		}]
	}).on('change', function() {
		$('#repositoryImplateValue').val($(this).getValue());
	}).trigger('change');

	projectAdd.find('#mvcImplate').select({
		title : '请选择',
		contents : [{
			title : 'Struts2MVC',
			value : 'jpa'
		}, {
			title : 'SpringMVC',
			value : 'mybatis',
			selected : true
		}]
	}).on('change', function() {
		$('#mvcImplateValue').val($(this).getValue());
	}).trigger('change');

	projectAdd.find('#add').on('click', function() {
		$.get('pages/cis/modual-template.html').done(function(data) {
			var dialog = $(data);
			dialog.find('#modualType').select({
				title : '请选择',
				contents : [{
					title : '基础实施层',
					value : 'jpa'
				}, {
					title : '领域层',
					value : 'mybatis'
				}, {
					title : '应用层接口',
					value : 'mybatis'
				}, {
					title : '应用层实现',
					value : 'mybatis'
				}, {
					title : '视图层',
					value : 'mybatis'
				}]
			});
			dialog.modal({
				keyboard : false
			}).on({
				'hidden.bs.modal' : function() {
					$(this).remove();
				}
			});
		});
	});

	projectAdd.find('#modify').on('click', function() {
		$.get('pages/modual-template.html').done(function(data) {
			var dialog = $(data);
			dialog.find('#modualType').select({
				title : '请选择',
				contents : [{
					title : '基础实施层',
					value : 'jpa'
				}, {
					title : '领域层',
					value : 'mybatis'
				}, {
					title : '应用层接口',
					value : 'mybatis'
				}, {
					title : '应用层实现',
					value : 'mybatis'
				}, {
					title : '视图层',
					value : 'mybatis'
				}]
			});
			dialog.modal({
				keyboard : false
			}).on({
				'hidden.bs.modal' : function() {
					$(this).remove();
				}
			});
		});
	});

	projectAdd.find('#delete').on('click', function() {
		$('.grid').confirm({
			content : '确定要删除所选记录吗?',
			callBack : function() {
			}
		});
	});

	projectAdd.find('#cacheType').select({
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

	projectAdd.find('#monitorType').select({
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

	var columns = [{
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
	console.info(projectAdd.find('#developerGrid'))
	projectAdd.find('#developerGrid').grid({
		identity : 'id',
		columns : columns,
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
	})
});
