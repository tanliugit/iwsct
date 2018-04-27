'use strict';;
(function($) {

	// 一级类型
	var LEVEL_TYPE_1 = 0;

	// 二级类型
	var LEVEL_TYPE_2 = 1;

	// 三级类型
	var LEVEL_TYPE_3 = 2;

	// 一级数据
	var levelList_1 = [];

	// 二级数据
	var levelList_2 = [];

	// 三级数据
	var levelList_3 = [];

	var form = {};

	if (layui instanceof Object) {
		form = layui.form;
	}


	/**
	 * 加载模块
	 * @param string  	object options 					参数
	 * @param string  	options.levelCon_1 				多选一级容器
	 * @param string  	options.levelCon_2 				多选二级容器
	 * @param string  	options.levelCon_3 				多选三容器
	 * @param string  	options.levelEle_1 				一级select
	 * @param string  	options.levelEle_2 				二级select
	 * @param string  	options.levelEle_3 				三级select
	 * @param string  	options.levelURL_1 				一级数据url
	 * @param string  	options.levelURL_2 				二级数据url
	 * @param string  	options.levelURL_3 				三级数据url
	 * @param object  	options.multiple 				开启多选
	 * @param string  	options.multiple.level_1 		多选一级
	 * @param string  	options.multiple.level_2 		多选二级
	 * @param string  	options.multiple.level_3 		多选三级
	 * @param object  	options.def 					默认值
	 * @param array  	options.def.level_1 			默认一级
	 * @param array  	options.def.level_2 			默认二级
	 * @param array  	options.def.level_3 			默认三级
	 */
	function MultiLevelLinkage(options) {

		// input元素
		this.levelEle_1 = options.levelEle_1;
		this.levelEle_2 = options.levelEle_2;
		this.levelEle_3 = options.levelEle_3;

		// 容器
		this.levelCon_1 = options.levelCon_1;
		this.levelCon_2 = options.levelCon_2;
		this.levelCon_3 = options.levelCon_3;

		// ID
		this.levelID_1 = options.levelID_1;
		this.levelID_2 = options.levelID_2;
		this.levelID_3 = options.levelID_3;

		// 接口
		this.levelURL_1 = options.levelURL_1;
		this.levelURL_2 = options.levelURL_2;
		this.levelURL_3 = options.levelURL_3;

		// 是否多选
		this.multiple = options.multiple || {};

		this.def = options.def || {};

		this.hasCode = Math.floor((Math.random() * 100000));

		this.startTop = $(this.levelEle_1).height() + 50;
		this.endTop = $(this.levelEle_1).height() + 5;

		this.speed = 250;

		// 类型映射
		this.typeMap = [{
			inputEle: this.levelEle_1,
			containerEle: this.levelCon_1,
			multiple: this.multiple.level_1,
			selectWrapper: 'js-provinceSelect-wrapper_' + this.hasCode,
			selectEle: 'js-provinceSelect-con_' + this.hasCode,
			name: 'categoryName', // 名称
			inputId: this.levelID_1 // ID
		}, {
			inputEle: this.levelEle_2,
			containerEle: this.levelCon_2,
			multiple: this.multiple.level_2,
			selectWrapper: 'js-citySelect-wrapper_' + this.hasCode,
			selectEle: 'js-citySelect-con_' + this.hasCode,
			name: 'brandName', // 名称
			inputId: this.levelID_2 // ID
		}, {
			inputEle: this.levelEle_3,
			containerEle: this.levelCon_3,
			multiple: this.multiple.level_3,
			selectWrapper: 'js-areaSelect-wrapper_' + this.hasCode,
			selectEle: 'js-areaSelect-con_' + this.hasCode,
			name: 'productName', // 名称
			inputId: this.levelID_3 // ID
		}];

		// 添加容器
		this.insertContainerHTML(LEVEL_TYPE_1);
		this.insertContainerHTML(LEVEL_TYPE_2);
		this.insertContainerHTML(LEVEL_TYPE_3);

		// 第一次加载
		// 加载一级
		var self = this;
		if (this.def.level_1 instanceof Array) {
			this.typeMap[LEVEL_TYPE_1].containerEle.parents('.js-levelCon').show();
			this.getData(this.levelURL_1, {}, function(res) {
				self.render(LEVEL_TYPE_1, res, self.def.level_1);
			});
		} else {
			this.getData(this.levelURL_1, {}, function(res) {
				self.render(LEVEL_TYPE_1, res, '');
			});
		}

		//　加载二级
		if (this.def.level_2 instanceof Array) {
			this.typeMap[LEVEL_TYPE_2].containerEle.parents('.js-levelCon').show();
			this.getData(self.levelURL_2, {categoryId:this.def.level_1.toString()}, function(res) {
				self.render(LEVEL_TYPE_2, res, self.def.level_2);
			});
		} else {
			this.getData(this.levelURL_2, {}, function(res) {
				self.render(LEVEL_TYPE_2, res, '');
			});
		}

		// 加载三级
		if (this.def.level_3 instanceof Array) {
			this.typeMap[LEVEL_TYPE_3].containerEle.parents('.js-levelCon').show();
			this.getData(self.levelURL_3, {brandId:this.def.level_2.toString()}, function(res) {
				self.render(LEVEL_TYPE_3, res, self.def.level_3);
			});
		} else {
			this.getData(this.levelURL_3, {}, function(res) {
				self.render(LEVEL_TYPE_3, res, '');
			});
		}

		this.initMultipleEvent();
	}

	MultiLevelLinkage.prototype.getData = function(url, data, callback) {
		var self = this;

		$.ajax({
			type: 'POST',
			url: url,
			data: data,
			dataType: 'json',
			success: function(res) {
				callback(res);
			},
			error: function(res) {
				console.error(res.status);
			}
		});

	}


	MultiLevelLinkage.prototype.render = function(type, data, def) {
		var self = this;

		self.renderHTML(type, data, def);

	}

	MultiLevelLinkage.prototype.insertContainerHTML = function(type) {
		var self = this;

		var tempHTML = '';

		tempHTML = '<div class="lxcy-citySelect-wrapper" style="top:' + self.startTop + 'px" id="' + self.typeMap[type].selectWrapper + '">';
		tempHTML += '<dl class="lxcy-citySelect-con" id="' + self.typeMap[type].selectEle + '">';

		tempHTML += '</dl></div>';

		self.typeMap[type].containerEle.append(tempHTML);

	}

	MultiLevelLinkage.prototype.renderHTML = function(type, data, def) {
		var self = this;
		var tempHTML = '';
		var childList = [];

		var def = def || '';

		if (!data) return;

		if (!(data instanceof Array)) return;

		tempHTML += '<dd><span class="text">请选择</span></dd>';

		data.forEach(function(item, index) {

			if (self.typeMap[type].multiple) {

				if (self.isEqual(def, item.id)) {
					tempHTML += '<dd class="multiple-active" checked="checked" data-value="' + item.id + '">';
					tempHTML += '<i class="lxcy-icon"></i>';
					tempHTML += '<span class="text">' + item[self.typeMap[type].name] + '</span>';
					tempHTML += '</dd>';

					self.typeMap[type].inputEle.val(self.getNameByIdArr(data, def, type));
					self.typeMap[type].inputId.val(def.toString());

					if (def.length >= 1) {
						if (self.typeMap[type + 1]) {
							self.typeMap[type + 1].containerEle.parents('.js-levelCon').show();
						}
					} else {
						if (self.typeMap[type + 1]) {
							self.typeMap[type + 1].containerEle.parents('.js-levelCon').hide();
						}
					}
				} else {
					tempHTML += '<dd data-value="' + item.id + '">';
					tempHTML += '<i class="lxcy-icon"></i>';
					tempHTML += '<span class="text">' + item[self.typeMap[type].name] + '</span>';
					tempHTML += '</dd>';
				}

			} else {

				if (self.isEqual(def, item.id)) {
					tempHTML += '<dd class="active" data-value="' + item.id + '">';
					tempHTML += '<span class="text">' + item[self.typeMap[type].name] + '</span>';
					tempHTML += '</dd>';

					self.typeMap[type].inputEle.val(self.getNameByIdArr(data, def, type));
					self.typeMap[type].inputId.val(def.toString());

				} else {
					tempHTML += '<dd data-value="' + item.id + '">';
					tempHTML += '<span class="text">' + item[self.typeMap[type].name] + '</span>';
					tempHTML += '</dd>';
				}
			}
		});

		$('#' + self.typeMap[type].selectEle).html(tempHTML);
	}

	MultiLevelLinkage.prototype.hideAllWrapper = function(type) {
		var self = this;

		$('#' + self.typeMap[LEVEL_TYPE_1].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
		$('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
		$('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
	}

	MultiLevelLinkage.prototype.initMultipleEvent = function() {
		var self = this;

		$(document).on('click', function() {
			self.hideAllWrapper();
		});

		/*********************一级类别事件 start*********************/
		// 点击一级输入框
		self.levelCon_1.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			$('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper).hide();
			$('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper).hide();

			if ($('#' + self.typeMap[LEVEL_TYPE_1].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[LEVEL_TYPE_1].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});
		// 点击一级选项
		self.levelCon_1.on('click', '#' + self.typeMap[LEVEL_TYPE_1].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			if (self.multiple.level_1) {
				$('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper).hide();
				$('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper).hide();
			} else {
				self.hideAllWrapper();
			}

			$('#' + self.typeMap[LEVEL_TYPE_2].selectEle).html('');
			$('#' + self.typeMap[LEVEL_TYPE_3].selectEle).html('');
			$('#' + self.typeMap[LEVEL_TYPE_1].selectEle + ' dd').removeClass('active');
			that.addClass('active');

			var text = that.text();
			var value = that.attr('data-value');

			self.levelCon_2.find('input[type="text"]').val('');
			self.levelCon_3.find('input[type="text"]').val('');
			self.levelCon_1.find('input[type="text"]').val(text);
			self.levelCon_1.find('input[type="hidden"]').val(value);

			if (!value) {
				that.removeClass('active');
				self.levelCon_2.parents('.js-levelCon').hide();
				self.levelCon_3.parents('.js-levelCon').hide();
				return;
			}

			//　加载二级
			self.getData(self.levelURL_2, {
				categoryId: value
			}, function(res) {

				self.render(LEVEL_TYPE_2, res);

				if (res.length > 0) {
					// self.levelCon_2.show();
					// self.levelCon_3.show();
					self.levelCon_2.parents('.js-levelCon').show();
					self.levelCon_3.parents('.js-levelCon').show();
				} else {
					// self.levelCon_2.hide();
					// self.levelCon_3.hide();
					self.levelCon_2.parents('.js-levelCon').hide();
					self.levelCon_3.parents('.js-levelCon').hide();
				}
			});
		});

		/*********************一级类别事件 end*********************/


		/*********************二级类别事件 start*********************/
		// 点击二级
		self.levelCon_2.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});

		// 点击二级选项
		self.levelCon_2.on('click', '#' + self.typeMap[LEVEL_TYPE_2].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			$('#' + self.typeMap[LEVEL_TYPE_3].selectEle).html('');

			var text = that.text();
			var value = that.attr('data-value');

			if (!value) {
				self.removeCheckedStatus(that.siblings('dd'));
				self.levelCon_2.find('input[type="text"]').val('');
				self.levelCon_2.find('input[type="hidden"]').val('');
				// self.levelCon_3.hide();
				self.levelCon_3.parents('.js-levelCon').hide();
				return;
			}

			// 隐藏一三级下拉
			$('#' + self.typeMap[LEVEL_TYPE_1].selectWrapper).hide();
			$('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper).hide();

			// 切换二级的选中状态
			if (that.attr('checked') !== 'checked') {
				that.attr('checked', 'checked');
				that.addClass('multiple-active');
			} else {
				that.removeAttr('checked');
				that.removeClass('multiple-active');
			}

			form.render();

			// 获取二级选中数据
			var checkedData = self.getCheckedData($('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper + ' dd'));

			//　加载三级
			self.getData(self.levelURL_3, {
				brandId: checkedData.data.toString()
			}, function(res) {
				self.render(LEVEL_TYPE_3, res);
			});

			self.levelCon_3.find('input[type="text"]').val('');
			self.levelCon_2.find('input[type="text"]').val(checkedData.dataName.toString());
			self.levelCon_2.find('input[type="hidden"]').val(checkedData.data.toString());

			
			if (checkedData.len >= 1) {
				// self.levelCon_3.show();
				self.levelCon_3.parents('.js-levelCon').show();
			} else {
				// self.levelCon_3.hide();
				self.levelCon_3.parents('.js-levelCon').hide();
			}

		});

		/*********************二级类别事件 end*********************/

		/*********************三级类别事件 start*********************/
		// 点击三级
		self.levelCon_3.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}

		});
		// 点击选中三级选项
		self.levelCon_3.on('click', '#' + self.typeMap[LEVEL_TYPE_3].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			var value = that.attr('data-value');

			// 选中了 “请选择”
			if (!value) {
				self.removeCheckedStatus(that.siblings('dd'));
				self.levelCon_3.find('input[type="text"]').val('');
				self.levelCon_3.find('input[type="hidden"]').val('');
				return;
			}

			$('#' + self.typeMap[LEVEL_TYPE_1].selectWrapper).hide();
			$('#' + self.typeMap[LEVEL_TYPE_2].selectWrapper).hide();

			if (that.attr('checked') !== 'checked') {
				that.attr('checked', 'checked');
				that.addClass('multiple-active');
			} else {
				that.removeAttr('checked');
				that.removeClass('multiple-active');
			}

			var checkedData = self.getCheckedData($('#' + self.typeMap[LEVEL_TYPE_3].selectWrapper + ' dd'));

			self.levelCon_3.find('input[type="text"]').val(checkedData.dataName.toString());
			self.levelCon_3.find('input[type="hidden"]').val(checkedData.data.toString());

		});

		/*********************三级类别事件 end*********************/

	}

	MultiLevelLinkage.prototype.getCheckedData = function(data) {
		var checkedData = {};

		checkedData.len = 0;
		checkedData.data = [];
		checkedData.dataName = [];

		$.each(data, function(index, item) {
			if ($(item).attr('checked') === 'checked' && $(item).attr('data-value')) {
				checkedData.len++;
				checkedData.data.push($(item).attr('data-value'));
				checkedData.dataName.push($(item).text());
			}
		});

		return checkedData;
	}

	MultiLevelLinkage.prototype.removeCheckedStatus = function(ddArr) {
		var self = this;

		$.each(ddArr, function(index, item) {
			$(item).removeAttr('checked');
			$(item).removeClass('multiple-active');
		});
	}

	MultiLevelLinkage.prototype.isEqual = function(arr, value) {

		if (!(arr instanceof Array)) return;

		for (var i = 0, len = arr.length; i < len; i++) {
			if (arr[i] == value) {
				return true;
			}
		}
		return false;
	}

	MultiLevelLinkage.prototype.getNameByIdArr = function(data, def, type) {

		var result = [];
		var self = this;

		if (!(data instanceof Array)) return;

		if (!(def instanceof Array)) return;

		for (var i = 0, len = def.length; i < len; i++) {
			for (var j = 0, jlen = data.length; j < jlen; j++) {
				if (data[j].id == def[i]) {
					result.push(data[j][self.typeMap[type].name]);
				}
			}
		}

		return result.toString();
	}

	window.MultiLevelLinkage = MultiLevelLinkage;

})(jQuery)