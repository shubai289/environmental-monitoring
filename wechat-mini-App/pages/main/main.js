// pages/iot/dashboard.js
import * as echarts from '../../ec-canvas/echarts';

const app = getApp();
let chart = null;
function initChart(canvas, width, height) {
  chart = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(chart);

  var option = {
    backgroundColor: "#f8f8f8",
    color: ["#37A2DA", "#32C5E9", "#67E0E3"],
    series: [{
      name: '{name}',
      min: 0, // 最小值
      max: 100,
      splitNumber: 10,
      type: 'gauge',
      detail: {
        formatter: '{value}'
      },
      axisLine: {
        show: true,
        lineStyle: {
          width: 10,
          shadowBlur: 0,
          color: [
            [0.3, '#67e0e3'],
            [0.7, '#37a2da'],
            [1, '#fd666d']
          ]
        }
      },
      data: [{
        value: 0,
        name: '空气质量'
      }],
      splitLine: { // 分隔线
        show: true, // 默认显示，属性show控制显示与否
        length: 13, // 属性length控制线长
        lineStyle: { // 属性lineStyle（详见lineStyle）控制线条样式
          color: '#aaa',
          width: 2,
          type: 'solid'
        }
      },
      title: {
        show: true,
        offsetCenter: [0, 70], // x, y，单位px
        textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
          color: '#333',
          fontSize: 15
        }
      },
      pointer: {
        length: '90%',
        width: 6,
        color: 'auto'
      }
    }]
  };
  chart.setOption(option, true);
  return chart;
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    temperature: '',
    deviceName: '',
    humidity: '',
    l_status: '',
    pm25: '',
    pm10: '',
    Key: '',
    status:'',
    newName: '',
    ec: {
      onInit: initChart
    },
    timer: ''
  },

  getDeviceInfo: function () {

  },

  _bindblur: function (e) {
    wx.showLoading({
      title: '处理中...',
    })
    var that = this
    let length = e.detail.value.length
    if (length > 1 && length < 20) {
      if (e.detail.value == this.data.newName) {
        wx.request({
          url: app.globalData.ServerApi+"/changeDeviceName",
          method: 'POST',
          data: {
            topic: that.data.Key + "-control",
            payload: 'cdn' + that.data.newName,
          },
          header: {
            'content-type': "application/x-www-form-urlencoded",
            'cookie':wx.getStorageSync("sessionid")//读取cookie
          },
          success: function (res) {
            console.log(res.data)
            if (res.data == 'done') {
              wx.hideLoading()
              that.setData({
                newName: null
              })
              that.refreshDevice
            }
          }
        })
      }
    }
  },

  changeName: function (e) {
    this.setData({
      newName: e.detail.value
    })
  },

  refreshDevice: function () {
    var that = this;
    wx.request({
      url: app.globalData.ServerApi+"/Refresh",
      method: 'POST',
      data: {
        productKey: that.data.Key,
        topic: that.data.Key + "-report",
        payload: 'Refresh'
      },
      header: {
        'content-type': "application/x-www-form-urlencoded",
        'cookie':wx.getStorageSync("sessionid")//读取cookie
      },
      success: function (res) {
        that.setData({
          deviceName: res.data.deviceName,
          temperature: res.data.temperature,
          humidity: res.data.humidity,
          status: res.data.status,
          l_status: (res.data.ledStatus == 'on' ? '开' : '关'),
        })
        if (chart != null) {
          let option = chart.getOption();
          option.series[0].data[0].value = res.data.deviceData;
          chart.setOption(option);
        }
      }
    })
  },
  switchLight: function () {
    this.sendCommand();
  },

  sendCommand: function () {
    wx.showLoading({
      title: '处理中...',
    })
    var that = this;
    wx.request({
      url: app.globalData.ServerApi+'/Switch_lightStatus',
      method: 'POST',
      data: {
        topic: that.data.Key + "-control",
        payload: "LEDswitch"
      },
      header: {
        'content-type': "application/x-www-form-urlencoded",
        'cookie':wx.getStorageSync("sessionid")//读取cookie
      },
      success(res) {
        wx.hideLoading()
        if (res.data == 'done') {
          that.refreshDevice
        }
      }
    })
  },

  setDelay: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.refreshDevice()
    this.setData({
      timer: setInterval(this.refreshDevice, 1000),
    })
  },
  onHide: function () {
    clearInterval(this.data.timer)
    this.setData({
      timer: null
    })
  },
  onUnload: function () {
    clearInterval(this.data.timer)
    this.setData({
      timer: null
    })
  },
  onLoad: function (options) {
    var that = this
    console.log(options.key)
    that.setData({
      Key: options.key,
    })
  }

})