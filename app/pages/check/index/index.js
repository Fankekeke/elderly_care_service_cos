const app = getApp();
let http = require('../../../utils/request')
Page({
	data: {
		StatusBar: app.globalData.StatusBar,
		CustomBar: app.globalData.CustomBar,
		hidden: true,
		current: 0,
		lines: 0,
		swiperlist: [{
			id: 0,
			url: 'https://www.huolala.cn/rs/img/housemove/tab_1_bg.png',
			type: 1
		}, {
			id: 1,
			url: 'https://www.huolala.cn/rs/img/housemove/tab_3_bg.png',
			type: 2
		}, {
			id: 2,
			url: 'https://www.huolala.cn/rs/img/housemove/tab_4_bg.png',
			type: 3
		}],
		iconList: [{
			id: 1,
			icon: 'questionfill',
			color: 'red',
			name: '公告',
			type: 1
		}, {
			id: 3,
			icon: 'shopfill',
			color: 'yellow',
			name: '论坛',
			type: 3
		}],
		Headlines: [{
			id: 1,
			title: "配送员入驻",
			type: 1
		}, {
			id: 2,
			title: "配送大作战",
			type: 2
		}],
		shopInfo: [],
		postInfo: [],
		commodityHot: [],
		keys: '',
		videosrc: "http://wxsnsdy.tc.qq.com/105/20210/snsdyvideodownload?filekey=30280201010421301f0201690402534804102ca905ce620b1241b726bc41dcff44e00204012882540400&bizid=1023&hy=SH&fileparam=302c020101042530230204136ffd93020457e3c4ff02024ef202031e8d7f02030f42400204045a320a0201000400",
		addressList: [],
		startPoint: {
			show: false,
			id: null,
			address: '',
			point: null
		},
		endPoint: {
			show: false,
			id: null,
			address: '',
			point: null
		},
		userInfo: null,
		staffInfo: null,
		staffData: null,
		withdraw: null,
		orderList: [],
		memberData: null,
		userData: null
	},
	onLoad: function () {},
	onShow() {
		wx.getStorage({
			key: 'userInfo',
			success: (res) => {
				this.setData({
					userInfo: res.data
				})
				this.home()
			},
			fail: res => {
				wx.showToast({
					title: '请先进行登录',
					icon: 'none',
					duration: 2000
				})
			}
		})
	},
	postDetail (e) {
		console.log(e.currentTarget.dataset.postid)
		wx.navigateTo({
			url: '/pages/shop/goods/details?courseId=' + e.currentTarget.dataset.postid
	});
	},
	withdrawal() {
		console.log(this.data.staffData)
		if (this.data.staffData.price <= 0) {
			wx.showToast({
				title: '当前余额不足',
				icon: 'none',
				duration: 2000
			})
			return false
		}
		wx.showModal({
			title: '提示',
			content: '是否要进行提现',
			success: (res) => {
				if (res.confirm) {
					http.post('withdrawInfoAdd', {
						staffId: this.data.userInfo.id
					}).then((r) => {
						wx.showToast({
							title: '提现审核中...',
							icon: 'none',
							duration: 2000
						})
						setTimeout(() => {
							this.home()
						}, 1000);
					})
				} else if (res.cancel) {
					console.log('用户点击取消')
				}
			}
		})
	},
	/**
	 * 选择位置
	 */
	startChooseLocation() {
		const _this = this;
		wx.chooseLocation({
			success(res) {
				console.log(res)
				_this.setData({
					['startPoint.startAddress']: res.address,
					['startPoint.point']: {
						latitude: res.latitude,
						longitude: res.longitude
					},
				})
			},
			fail(e) {
				console.log(e);
			}
		})
	},
	timeFormat(time) {
		var nowTime = new Date();
		var day = nowTime.getDate();
		var hours = parseInt(nowTime.getHours());
		var minutes = nowTime.getMinutes();
		// 开始分解付入的时间
		var timeday = time.substring(8, 10);
		var timehours = parseInt(time.substring(11, 13));
		var timeminutes = time.substring(14, 16);
		var d_day = Math.abs(day - timeday);
		var d_hours = hours - timehours;
		var d_minutes = Math.abs(minutes - timeminutes);
		if (d_day <= 1) {
			switch (d_day) {
				case 0:
					if (d_hours == 0 && d_minutes > 0) {
						return d_minutes + '分钟前';
					} else if (d_hours == 0 && d_minutes == 0) {
						return '1分钟前';
					} else {
						return d_hours + '小时前';
					}
					break;
				case 1:
					if (d_hours < 0) {
						return (24 + d_hours) + '小时前';
					} else {
						return d_day + '天前';
					}
					break;
			}
		} else if (d_day > 1 && d_day < 10) {
			return d_day + '天前';
		} else {
			return time;
		}
	},
	memberBuy(e) {
		console.log(e.currentTarget.dataset.id);
		console.log(e.currentTarget.dataset.price);
		if (this.data.memberData != null) {
			wx.showToast({
				title: '当前会员正在使用中',
				icon: 'none',
				duration: 1000
			})
			return false
		}
		let that = this
		wx.showModal({
			title: '提示',
			content: '是否要进行购买',
			success: (res) => {
				if (res.confirm) {
					wx.getStorage({
						key: 'userInfo',
						success: (res) => {
							http.get('member', {
								totalAmount: e.currentTarget.dataset.price,
								ruleId: e.currentTarget.dataset.id,
								userId: res.data.id
							}).then((r) => {
								wx.showLoading({
									title: '正在模拟支付',
								})
								setTimeout(() => {
									wx.showToast({
										title: '支付成功',
										icon: 'success',
										duration: 1000
									})
									that.home()
								}, 1000)
							})
						},
						fail: res => {
							wx.showToast({
								title: '请先进行登录',
								icon: 'none',
								duration: 2000
							})
						}
					})
				} else if (res.cancel) {
					console.log('用户点击取消')
				}
			}
		})
	},
	home() {
		http.get('queryCourseList').then((r) => {
			this.setData({
				orderList: r.data
			})
		})
	},

});