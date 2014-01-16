$(function() {
	
	var items = $('.wizard .items').width(5 * $('#content').width());
	$(".wizard").wizard();
	
	$('#repositoryImplate').select({
		title : '请选择',
		contents : [{
			title : 'JPA',
			value : 'jpa',
			selected : true
		}, {
			title : 'Mybatis',
			value : 'mybatis'
		}]
	}).on('change', function(){
		$('#repositoryImplateValue').val($(this).getValue());
	}).trigger('change');
	
	$('#mvcImplate').select({
		title : '请选择',
		contents : [{
			title : 'Struts2MVC',
			value : 'jpa'
		}, {
			title : 'SpringMVC',
			value : 'mybatis',
			selected : true
		}]
	}).on('change', function(){
		$('#mvcImplateValue').val($(this).getValue());
	}).trigger('change');

	$('#add').on('click', function() {
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

	$('#modify').on('click', function() {
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

	$('#delete').on('click', function() {
		$('.grid').confirm({
			content : '确定要删除所选记录吗?',
			callBack : function() {
			}
		});
	});

	$('#cacheType').select({
		title : '请选择缓存类型',
		contents : [{
			title : 'ehCache',
			value : 'jpa'
		}, {
			title : 'memCached',
			value : 'mybatis'
		}]
	});

	$('#monitorType').select({
		title : '请选择监控系统类型',
		contents : [{
			title : '本地',
			value : 'jpa'
		}, {
			title : '分布式',
			value : 'mybatis'
		}]
	});
});
