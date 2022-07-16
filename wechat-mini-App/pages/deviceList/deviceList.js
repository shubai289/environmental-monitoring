// pages/deviceList1/deviceList.js
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    deviceList: [],
    productKey: '',
    isLoading: false,
    createtime: '',
    queryObj: {
      pagenum: 1,
      pagesize: 10
    },
    total: 0,
  },

  getDeviceList() {
    var that = this
    wx.request({
      url: app.globalData.ServerApi+'/DeviceList',
      data: {},
      method: 'GET',
      header: {
        'content-type': 'application/json',
        'cookie':wx.getStorageSync("sessionid")//读取cookie
      },
      success: function (res) {
        console.log(res.data)
        wx.setStorageSync('List', res.data)
        that.setData({
          deviceList: res.data
        })
      },
      complete: function () {
        wx.hideNavigationBarLoading()
        wx.hideLoading()
      }
    })
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {
    console.log('······下拉刷新······')
    this.getDeviceList(() => wx.stopPullDownRefresh())
  },
  toDetail: function (e) {
    console.log(e)
    wx.navigateTo({
      url: '../main/main',
    });
  },
  onReachBottom() {
    if (this.data.isLoading) return
    let pagenum = 'queryObj.pagenum'
    this.setData({
      pagenum: pagenum += 1,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getDeviceList()
    /*
        this.setData({
          timer: setInterval(this.getDeviceList, 3000),
        })
    */
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})