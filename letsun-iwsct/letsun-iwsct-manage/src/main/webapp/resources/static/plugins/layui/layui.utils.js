//弹出层封装
$.dialog = {
	openSimple : function(title, url, width, height) {
		layer.open({
			type : 2,
			title : title,
			icon : 1,
			area : [ width + 'px', height + 'px' ],
			shade : 0.3,
			maxmin : false,
			content : url
		});
		return false;
	},	
	openContent : function(title, url, width, height) {
		layer.open({
			type : 2,
			title : title,
			icon : 1,
			area : [ width + 'px', height + 'px' ],
			shade : 0.3,
			maxmin : true,
			content : url,
			btn : ['关闭'],
			btn2 : function(index, layero) {
				layero.close();
			}
		});
		return false;
	},
	openCallback : function(title, url, width, height,callBack) {
		layer.open({
			type : 2,
			title : title,
			icon : 1,
			area : [ width + 'px', height + 'px' ],
			shade : 0.3,
			maxmin : true,
			content : url,
			btn : [ '确认', '取消' ],
			yes : function(index, layero) {
				callBack(index, layero);
			},
			no : function() {
				layer.close();
			}
		});
		return false;
	}
}

//数据表格[搜索、重置]事件处理
$.table = {
	search : function() {
		layui.table.reload('layui-table', {
			page : {
				curr : 1
			},
			where : $(".layui-form").serializeObject()
		});
		return false;
	},
	reload : function() {
		$('.layui-form')[0].reset();
		layui.table.reload('layui-table');
		return false;
	}
}

//表单提交
$.form = {
	ajaxSubmit : function(url,data) {
		$.ajax({
			url : url,
			data : data,
			type : "post",
			dataType : "json",
			success : function(_data) {
				if (_data.type.toUpperCase() == "SUCCESS") {
					parent.layer.closeAll();
					parent.$.table.reload();
					parent.layer.msg(_data.content, {
						icon : 1,
						time : 1000
					});
				}else{
					parent.layer.msg(_data.content, {
						icon : 0,
						time : 1000
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg('保存发生错误');
			}
		});
	}
}

//数据表格[详情、编辑、删除]事件处理
layer.ready(function() {
	var table = layui.table;
	table.on('tool(layui-table-filter)', function(obj) {
		var data = obj.data;
		var event = obj.event;
		var width = 800;
		var height = 500;
		var params;
		if(obj.event.indexOf('?') != -1){
			var array = obj.event.split('?');
			event = array[0];
			var params = array[1];
			if(obj.event.indexOf('width') != -1){
				width = getValueByName(params,"width");
			}
			if(obj.event.indexOf('height') != -1){
				height = getValueByName(params,"height");
			}
		}
		
		//授权
		if (event === 'grant') {
			$.dialog.openCallback('授权','grant?id='+data.id,width,height,function(index, layero){
				//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var iframeWin = window[layero.find('iframe')[0]['name']]; 
				iframeWin.grantCallback();
			});
			return;
		}
		
		//详情
		if (event === 'detail') {
			$.dialog.openContent('详情','detail?id='+data.id,width,height);
			return;
		}
		//编辑
		if (event === 'edit') {
			$.dialog.openSimple('编辑','edit?id='+data.id,width,height);
			return;
		}
		//删除
		if (event === 'del') {
			layer.confirm('确定要删除么?', function(index) {
				$.form.ajaxSubmit('delete',{id:data.id});
				layer.close(index);
			});
			return;
		}
	});
	
	var form = layui.form;
	//监听iframe子页面表单提交，lay-filter="layui-form-submit-btn"名字要一致
	form.on('submit(layui-form-submit-btn)', function(data){
		$.form.ajaxSubmit(data.form.action,data.field);
	   	return false;
	 });

	 //取消按钮
	 $(document).on('click','#layui-form-cancel-btn',function(){
		parent.layer.closeAll();
	 });
});

//插件扩展
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
}

//获取参数
function getValueByName(url,name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = url.match(reg);
    if(r!=null) {
   	 return  unescape(r[2]); 
    }
    return null;
}