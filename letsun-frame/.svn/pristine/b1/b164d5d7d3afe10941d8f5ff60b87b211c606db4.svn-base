$(function() {
	var form = layui.form;
	// 监听启用/禁用操作
	form.on('switch(statusSwitch)', function(obj) {
		var status = this.value == 0 ? 1 : 0;
		$.ajax({
			url : '/system/menu/status',
			data : {
				id : this.id,
				status : status
			},
			type : "post",
			dataType : "json",
			success : function(_data) {
				layer.msg(_data.content, {
					icon : 1,
					time : 1000
				});
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg('保存发生错误');
			}
		});
	});
})