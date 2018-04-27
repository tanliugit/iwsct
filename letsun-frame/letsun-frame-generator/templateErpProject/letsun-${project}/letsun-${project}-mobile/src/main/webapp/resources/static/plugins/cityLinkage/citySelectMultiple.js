'use strict';;
(function($) {

	// 省份
	var PROVINCE_TYPE = 0;

	// 城市 
	var CITY_TYPE = 1;

	// 区域
	var AREA_TYPE = 2;

	// 城市数据
	var cityList = [];

	// 区域数据
	var areaList = [];

	var form = {};

	var provinceValueObject = {};
	var cityValueObject = {};
	var areaValueObject = {};

	if (layui instanceof Object) {
		form = layui.form;
	}


	/**
	 * 加载模块
	 * @param string  	object options 					参数
	 * @param string  	options.provinceCon 			省份容器
	 * @param string  	options.cityCon 				城市容器
	 * @param string  	options.areaCon 				区域容器
	 * @param string  	options.provinceEle 			省份select
	 * @param string  	options.cityEle 				城市select
	 * @param string  	options.areaEle 				区域select
	 * @param object  	options.multiple 				多选
	 * @param string  	options.multiple.province 		多选省份
	 * @param string  	options.multiple.city 			多选城市
	 * @param string  	options.multiple.area 			多选区域
	 * @param object  	options.def 					默认值
	 * @param array  	options.def.province 			默认省份
	 * @param array  	options.def.city 				默认城市
	 * @param array  	options.def.area 				默认区域
	 */
	function CitySelectMultiple(options) {

		// input元素
		this.provinceEle = options.provinceEle;
		this.cityEle = options.cityEle;
		this.areaEle = options.areaEle;

		// 容器
		this.provinceCon = options.provinceCon;
		this.cityCon = options.cityCon;
		this.areaCon = options.areaCon;

		// 是否多选
		this.multiple = options.multiple || {};

		this.def = options.def;

		this.hasCode = Math.floor((Math.random() * 1000000));

		this.startTop = $(this.provinceEle).height() + 50;
		this.endTop = $(this.provinceEle).height() + 5;

		this.speed = 250;

		// 类型映射
		this.typeMap = [{
			inputEle: this.provinceEle,
			containerEle: this.provinceCon,
			multiple: this.multiple.province,
			selectWrapper: 'js-provinceSelect-wrapper_' + this.hasCode,
			selectEle: 'js-provinceSelect-con_' + this.hasCode,
			valueObject: {},
			textWidth: this.provinceEle.width()
		}, {
			inputEle: this.cityEle,
			containerEle: this.cityCon,
			multiple: this.multiple.city,
			selectWrapper: 'js-citySelect-wrapper_' + this.hasCode,
			selectEle: 'js-citySelect-con_' + this.hasCode,
			valueObject: {},
			// textWidth: this.cityEle.width() - 14 - 30
		}, {
			inputEle: this.areaEle,
			containerEle: this.areaCon,
			multiple: this.multiple.area,
			selectWrapper: 'js-areaSelect-wrapper_' + this.hasCode,
			selectEle: 'js-areaSelect-con_' + this.hasCode,
			valueObject: {},
			// textWidth: this.areaEle.width() - 14 - 30
		}];

		// 数据
		this.data = cityData;

		// 添加省市区容器
		this.insertContainerHTML(PROVINCE_TYPE);
		this.insertContainerHTML(CITY_TYPE);
		this.insertContainerHTML(AREA_TYPE);

		if (this.def instanceof Array) {
			this.render(this.def[0], this.def[1], this.def[2]);
		} else if (this.def instanceof Object) {
			this.render(this.def.province, this.def.city, this.def.area);
		} else {
			this.render('', '', '');
		}

		if (JSON.stringify(this.multiple) === '{}') {
			this.initEvent();
		} else {
			this.initMultipleEvent();
		}


	}

	CitySelectMultiple.prototype.getData = function(value, data) {
		var self = this;
		var result = [];

		for (var i = 0, len = data.length; i < len; i++) {
			if (data[i].name === value) {
				if (data[i].sub) {
					result = data[i].sub;
				}
				break;
			}
		}

		return result;
	}


	CitySelectMultiple.prototype.render = function(defProvince, defCity, defArea) {
		var self = this;

		self.renderHTML(PROVINCE_TYPE, self.data, defProvince, function(cityList) {
			if (defCity) {
				self.renderHTML(CITY_TYPE, cityList, defCity, function(areaList) {
					if (defArea) {
						self.renderHTML(AREA_TYPE, areaList, defArea);
					}
				});
			}

		});

	}

	CitySelectMultiple.prototype.insertContainerHTML = function(type) {
		var self = this;

		var tempHTML = '';

		tempHTML = '<div class="lxcy-citySelect-wrapper" style="top:' + self.startTop + 'px" id="' + self.typeMap[type].selectWrapper + '">';
		tempHTML += '<dl class="lxcy-citySelect-con" id="' + self.typeMap[type].selectEle + '">';

		tempHTML += '</dl></div>';

		self.typeMap[type].containerEle.append(tempHTML);

	}

	CitySelectMultiple.prototype.renderHTML = function(type, data, def, callback) {
		var self = this;
		var tempHTML = '';
		var childList = [];

		if (!data) return;

		data.forEach(function(item, index) {

			if (self.typeMap[type].multiple) {

				if (item.name === '请选择') {
					tempHTML += '<dd><span class="text">请选择</span></dd>';
				} else if (self.isEqual(def, item.name)) {
					tempHTML += '<dd class="multiple-active" checked="checked" data-value="' + item.name + '">';
					tempHTML += '<i class="lxcy-icon"></i>';
					tempHTML += '<span class="text">' + item.name + '</span>';
					tempHTML += '</dd>';
					
					self.typeMap[type].inputEle.val(def.toString());

					if (item.sub) {
						childList = item.sub;
						self.typeMap[type].valueObject[item.name] = childList;
					}
					if (def.length >= 2) {
						if (self.typeMap[type + 1]) {
							self.typeMap[type + 1].containerEle.hide();
						}

					}
				} else {
					tempHTML += '<dd data-value="' + item.name + '">';
					tempHTML += '<i class="lxcy-icon"></i>';
					tempHTML += '<span class="text">' + item.name + '</span>';
					tempHTML += '</dd>';
				}

			} else {

				if (item.name === '请选择') {
					tempHTML += '<dd><span class="text">请选择</span></dd>';
				} else if (self.isEqual(def, item.name)) {
					tempHTML += '<dd class="active" data-value="' + item.name + '">';
					tempHTML += '<span class="text">' + item.name + '</span>';
					tempHTML += '</dd>';

					self.typeMap[type].inputEle.val(def);

					if (item.sub) {
						childList = item.sub;
					} else {
						childList = [];
					}

					if (type + 1 === CITY_TYPE) {
						cityList = childList;
					} else if (type + 1 === AREA_TYPE) {
						areaList = childList;
					}
				} else {
					tempHTML += '<dd data-value="' + item.name + '">';
					tempHTML += '<span class="text">' + item.name + '</span>';
					tempHTML += '</dd>';
				}
			}
		});

		$('#' + self.typeMap[type].selectEle).html(tempHTML);

		if (callback || typeof callback === 'function' && childList.length > 0) {
			callback(childList);
		}

	}

	CitySelectMultiple.prototype.hideAllWrapper = function(type) {
		var self = this;

		$('#' + self.typeMap[PROVINCE_TYPE].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
		$('#' + self.typeMap[CITY_TYPE].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
		$('#' + self.typeMap[AREA_TYPE].selectWrapper).hide().css({
			top: self.startTop,
			opacity: 0
		});
	}

	CitySelectMultiple.prototype.initEvent = function() {
		var self = this;

		$(document).on('click', function() {
			self.hideAllWrapper();
		});

		// 点击省份输入框
		self.provinceCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			$('#' + self.typeMap[CITY_TYPE].selectWrapper).hide();
			$('#' + self.typeMap[AREA_TYPE].selectWrapper).hide();

			if ($('#' + self.typeMap[PROVINCE_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[PROVINCE_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});
		// 点击选中省份
		self.provinceCon.on('click', '#' + self.typeMap[PROVINCE_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			$('#' + self.typeMap[CITY_TYPE].selectEle).html('');
			$('#' + self.typeMap[AREA_TYPE].selectEle).html('');
			$('#' + self.typeMap[PROVINCE_TYPE].selectEle + ' dd').removeClass('active');
			that.addClass('active');

			var value = that.attr('data-value');

			if (!value) {
				that.removeClass('active');
			}

			self.cityCon.find('input[type="text"]').val('');
			self.areaCon.find('input[type="text"]').val('');
			self.provinceCon.find('input[type="text"]').val(value);

			cityList = self.getData(value, self.data);

			if (cityList.length > 0) {
				self.renderHTML(CITY_TYPE, cityList, '');
				self.cityCon.show();
				self.areaCon.show();
			} else {
				self.cityCon.hide();
				self.areaCon.hide();
			}

		});


		// 点击城市
		self.cityCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[CITY_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[CITY_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});
		// 点击选中城市
		self.cityCon.on('click', '#' + self.typeMap[CITY_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			$('#' + self.typeMap[AREA_TYPE].selectEle).html('');
			$('#' + self.typeMap[CITY_TYPE].selectEle + ' dd').removeClass('active');
			that.addClass('active');

			var value = that.attr('data-value');

			if (!value) {
				that.removeClass('active');
			}

			self.areaCon.find('input[type="text"]').val('');
			self.cityCon.find('input[type="text"]').val(value);

			areaList = self.getData(value, cityList);

			if (areaList.length > 0) {
				self.renderHTML(AREA_TYPE, areaList, '');
				self.areaCon.show();
			} else {
				self.areaCon.hide();
			}

		});


		// 点击区域
		self.areaCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[AREA_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}

		});
		// 点击选中区域
		self.areaCon.on('click', '#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			$('#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd').removeClass('active');
			that.addClass('active');

			var value = that.attr('data-value');

			if (!value) {
				that.removeClass('active');
			}

			self.areaCon.find('input[type="text"]').val(value);

		});

	}

	CitySelectMultiple.prototype.initMultipleEvent = function() {
		var self = this;

		$(document).on('click', function() {
			self.hideAllWrapper();
		});

		// 点击省份输入框
		self.provinceCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			$('#' + self.typeMap[CITY_TYPE].selectWrapper).hide();
			$('#' + self.typeMap[AREA_TYPE].selectWrapper).hide();

			if ($('#' + self.typeMap[PROVINCE_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[PROVINCE_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});
		// 点击选中省份
		self.provinceCon.on('click', '#' + self.typeMap[PROVINCE_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			if (self.multiple.province) {
				$('#' + self.typeMap[CITY_TYPE].selectWrapper).hide();
				$('#' + self.typeMap[AREA_TYPE].selectWrapper).hide();
			} else {
				self.hideAllWrapper();
			}

			$('#' + self.typeMap[CITY_TYPE].selectEle).html('');
			$('#' + self.typeMap[AREA_TYPE].selectEle).html('');
			$('#' + self.typeMap[PROVINCE_TYPE].selectEle + ' dd').removeClass('active');
			that.addClass('active');

			var value = that.attr('data-value');

			if (!value) {
				that.removeClass('active');
			}

			self.cityCon.find('input[type="text"]').val('');
			self.areaCon.find('input[type="text"]').val('');
			self.provinceCon.find('input[type="text"]').val(value);

			cityList = self.getData(value, self.data);

			if (cityList.length > 0) {
				self.renderHTML(CITY_TYPE, cityList, '');
				self.cityCon.show();
				self.areaCon.show();
			} else {
				self.cityCon.hide();
				self.areaCon.hide();
			}

		});


		// 点击城市
		self.cityCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[CITY_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[CITY_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}
		});

		// 点击选中城市
		self.cityCon.on('click', '#' + self.typeMap[CITY_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			$('#' + self.typeMap[AREA_TYPE].selectEle).html('');


			var value = that.attr('data-value');

			if (!value) {
				self.removeSelectStatus(that.siblings('dd'));
				self.cityCon.find('input[type="text"]').val('');
				self.areaCon.hide();
				return;
			}

			areaList = self.getData(value, cityList);

			$('#' + self.typeMap[PROVINCE_TYPE].selectWrapper).hide();
			$('#' + self.typeMap[AREA_TYPE].selectWrapper).hide();

			if (that.attr('checked') !== 'checked') {
				that.attr('checked', 'checked');
				that.addClass('multiple-active');
				self.typeMap[1].valueObject[value] = areaList;
			} else {
				that.removeAttr('checked');
				that.removeClass('multiple-active');
				delete self.typeMap[1].valueObject[value];
			}

			form.render();

			var checkedData = self.getCheckedData($('#' + self.typeMap[CITY_TYPE].selectWrapper + ' dd'));

			self.areaCon.find('input[type="text"]').val('');
			self.cityCon.find('input[type="text"]').val(checkedData.data.toString());


			if (checkedData.len <= 1) {
				areaList = self.typeMap[1].valueObject[checkedData.data[0]] || [];
				if (areaList.length > 0) {
					self.renderHTML(AREA_TYPE, areaList, '');
					self.areaCon.show();
				} else {
					self.areaCon.hide();
				}

			} else {
				self.areaCon.hide();
			}
		});


		// 点击区域
		self.areaCon.on('click', function(e) {
			var that = $(this);

			e.stopPropagation();

			self.hideAllWrapper();

			if ($('#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd').length > 0) {
				that.find('#' + self.typeMap[AREA_TYPE].selectWrapper).show().animate({
					top: self.endTop,
					opacity: 1
				}, self.speed);
			}

		});
		// 点击选中区域
		self.areaCon.on('click', '#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd', function(e) {
			var that = $(this);

			e.stopPropagation();

			var value = that.attr('data-value');

			if (!value) {
				self.removeSelectStatus(that.siblings('dd'));
				self.areaCon.find('input[type="text"]').val('');
				return;
			}

			$('#' + self.typeMap[PROVINCE_TYPE].selectWrapper).hide();
			$('#' + self.typeMap[CITY_TYPE].selectWrapper).hide();

			if (that.attr('checked') !== 'checked') {
				that.attr('checked', 'checked');
				that.addClass('multiple-active');
			} else {
				that.removeAttr('checked');
				that.removeClass('multiple-active');
			}

			var checkedData = self.getCheckedData($('#' + self.typeMap[AREA_TYPE].selectWrapper + ' dd'));

			self.areaCon.find('input[type="text"]').val(checkedData.data.toString());

		});

	}

	CitySelectMultiple.prototype.getCheckedData = function(data) {
		var checkedData = {};

		checkedData.len = 0;
		checkedData.data = [];

		$.each(data, function(index, item) {
			if ($(item).attr('checked') === 'checked' && $(item).attr('data-value')) {
				checkedData.len++;
				checkedData.data.push($(item).attr('data-value'));
			}
		});

		return checkedData;
	}

	CitySelectMultiple.prototype.removeSelectStatus = function(ddArr) {
		var self = this;

		$.each(ddArr, function(index, item) {
			$(item).removeAttr('checked');
			$(item).removeClass('multiple-active');
		});
	}

	CitySelectMultiple.prototype.isEqual = function(arr, value) {

		if (!(arr instanceof Array)) return;
		for (var i = 0, len = arr.length; i < len; i++) {
			if (arr[i] == value) {
				return true;
			}
		}
		return false;
	}

	window.CitySelectMultiple = CitySelectMultiple;

})(jQuery)