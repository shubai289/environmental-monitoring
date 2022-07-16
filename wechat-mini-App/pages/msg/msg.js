// pages/mine/mine.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    currentIndex: 0,
    showModalStatus: false,
    showModal: false,
    message: [],
  },

  getMessageList() {
    wx.showLoading({
      title: '获取中...',
    })
    var that = this
    wx.request({
      url: app.globalData.ServerApi + '/getMessageList',
      data: {},
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync("sessionid") //读取cookie
      },
      success: function (res) {
        console.log(res.data)
        that.setData({
          message: res.data
        })
      },
      complete: function () {
        wx.hideNavigationBarLoading()
        wx.hideLoading()
      }
    })
  },
  //swiper切换时会调用
  pagechange: function (e) {
    if ("touch" === e.detail.source) {
      let currentPageIndex = this.data.currentIndex
      currentPageIndex = (currentPageIndex + 1) % 2
      this.setData({
        currentIndex: currentPageIndex
      })
    }
  },

  toDetail: function (e) {
    var that = this;
    console.log()
    wx.showModal({
      title: '提示',
      content: '确认消息已读？',
      confirmText: '已读',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.globalData.ServerApi + '/messageCheck',
            data: {
              id: e.currentTarget.dataset.src
            },
            method: 'POST',
            header: {
              'content-type': "application/x-www-form-urlencoded",
              'cookie': wx.getStorageSync("sessionid") //读取cookie
            },
            success(res) {
              if (res.data == 'done') {
                console.log('done')
                that.getMessageList()
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  toDelete: function (e) {
    var that = this;
    console.log()
    wx.showModal({
      title: '提示',
      content: '确认删除消息？',
      confirmText: '删除',
      confirmColor: '#f22039',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: 'http://127.0.0.1:8080/messageDel',
            data: {
              id: e.currentTarget.dataset.src
            },
            method: 'POST',
            header: {
              'content-type': "application/x-www-form-urlencoded",
              'cookie': wx.getStorageSync("sessionid") //读取cookie
            },
            success(res) {
              if (res.data == 'done') {
                console.log('done')
                that.getMessageList()
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },

  //用户点击tab时调用
  titleClick: function (e) {
    let currentPageIndex =
      this.setData({
        //拿到当前索引并动态改变
        currentIndex: e.currentTarget.dataset.idx
      })
  },
  onPullDownRefresh() {
    this.getMessageList(() => wx.stopPullDownRefresh());
  },
  onLoad: function (options) {
    this.getMessageList();
  }

})