/**
 * layui：select插件,默认选中
 * ps：单个下拉框
 * @param 下拉框的id
 * @param 想要让选中的值：str
 */
 function layuiSelected(id,str){
     //0、设置select的值
     $("#"+id).attr("value",str);
     //0.1把select下的option的selected换成现在的
     $("#"+id).children("option").each(function(){
         if ($(this).text() == str) {
             $(this).attr("selected","selected");
         }else{
             if ($(this).attr("selected") == "selected") {
                 $(this).removeAttr("selected");
             }
         }
     });
     //1、首先设置输框
     $("#"+id).siblings("div[class='layui-unselect layui-form-select']").children("div[class='layui-select-title']").children("input").val(str);
     //2、其次，设置dl下的dd
     $("#"+id).siblings("div[class='layui-unselect layui-form-select']").children("dl").children("dd").each(function(){
         if ($(this).text() == str){
             if (!$(this).hasClass("layui-this")) {
                 $(this).addClass("layui-this");
                 $(this).click();
             }
             return true;
         }else{
             if ($(this).hasClass("layui-this")) {
                 $(this).removeClass("layui-this");
             }
         }
     });
 }