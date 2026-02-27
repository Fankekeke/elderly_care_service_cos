const app = getApp();
let http = require('../../../utils/request')
Page({
    data: {
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        TabbarBot: app.globalData.tabbar_bottom,
        TabCur: 0, scrollLeft: 0,
        SortMenu: [{id: 0, name: "全部订单"}, {id: 1, name: "未使用"}, {id: 2, name: "已销票"}],
        userInfo: null,
        orderListCopy: [],
        orderList: [],
        myOrderList: [],
        show: false,
        value: 3,
        remarks: '',
        orderId: null,
        inputValue: '',
        scrollTop: 0,
        messages: [
            {
                id: 1,
                type: 'system', // system/user/ai
                content: '您好！我是AI助手...',
                timestamp: new Date()
            }
        ]
    },
    onLoad: function (options) {
        wx.getStorage({
            key: 'userInfo',
            success: (res) => {
                this.setData({userInfo: res.data})
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
    onInput(e) {
        this.setData({
            inputValue: e.detail.value
        });
    },
// 滚动到底部
    scrollToBottom() {
        this.setData({
            scrollTop: 999999
        });
    },

// 页面渲染完成后调用
    onReady() {
        this.scrollToBottom();
    },
    sendMessage() {
        const message = this.data.inputValue;
        if (!message.trim()) return;

        // 添加用户消息到列表
        const userMessage = {
            id: Date.now(),
            type: 'user',
            content: message,
            timestamp: new Date()
        };

        const newMessages = [...this.data.messages, userMessage];

        this.setData({
            messages: newMessages,
            inputValue: ''
        });

        // 构造AI请求参数
        let params = '根据景点四川青龙湖介绍，300字内' + message;

        // 调用AI接口
        http.post('aliTyqw', {content: params}).then((r) => {
            // 添加AI回复到消息列表
            const aiMessage = {
                id: Date.now() + 1,
                type: 'ai',
                content: r.msg,
                timestamp: new Date()
            };

            this.setData({
                messages: [...this.data.messages, aiMessage]
            });

            // 滚动到底部
            this.scrollToBottom();
        });
    },
    onClose: function () {
        this.setData({
            show: false
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
});
