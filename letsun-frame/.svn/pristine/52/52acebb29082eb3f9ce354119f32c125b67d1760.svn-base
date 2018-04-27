$(function() {
	var doc = $(document);
	var win = $(window);

	/* 自定义内容区域滚动条 */
    $('#lxcy-container').mCustomScrollbar({
        scrollInertia: 350,
        autoHideScrollbar: true
    });

	// 所有点击body触发的行为都放在此
	doc.on('click', function() {
		// 隐藏头部个人中心、退出登录
		
	});

	// 打开主体块，关闭侧边栏目
	doc.on('click', '#js-showBody', function(){
		$('#js-lxcySide').animate({width:0}, 200);
		$('#js-lxcyBody').animate({left:20}, 200);
		$('#js-showLeftNav').fadeIn(200);
	});

	// 打开侧边栏目，关闭主体块
	doc.on('click', '#js-showLeftNav', function(){
		$('#js-lxcySide').animate({width:211}, 200);
		$('#js-lxcyBody').animate({left:240}, 200);
		$('#js-showLeftNav').fadeOut(200);
	});
})