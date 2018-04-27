$(function(){
    var form = layui.form;
    //自定义验证规则
    form.verify({
        title: function(value){
            if(value.length < 5){
                return '标题至少得5个字符啊';
            }
        },
        fname: function(value){
            if(value.length < 4){
                return '请输入至少4位的用户名';
            }
        },
        contact: function(value){
            if(value.length < 4){
                return '内容请输入至少4个字符';
            }
        },
        price: function(value){
        	if(value){
        		var reg =/^[1-9]\d*(\.\d+)?$/;
        	    if (!reg.test(value)) {
        	        return '请输入大于0的数字';
        	    } 
        	}
        },
        integer: function(value){
        	if(value){
        		var reg = /^[1-9]\d*$/;
            	if (!reg.test(value)) {
            		return '请输入正整数';
            	} 
        	}
        },
        noChinese:function(value){
        	var reg = /[\u4E00-\u9FA5]/i;
        	if (reg.test(value)) {
        		return '商品编码不能为中文';
        	} 
        }
    });

});

