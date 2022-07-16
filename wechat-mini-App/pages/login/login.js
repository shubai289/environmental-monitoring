//index.js
//获取应用实例
const app = getApp()
 let phone=''
 let password=''
Page({
  data: {
    phone: '',
    password: '',
    clientHeight:'',
    isChecked:false,
    checkboxValue:1
  },
  check:function(e)
  {
   if(e.detail.value.includes('1')){
      this.setData({
        isChecked:true
      })
   }else{
     this.setData({
       isChecked:false
     })
   }
  },
  onLoad(){
    var that=this
    wx.getSystemInfo({ 
      success: function (res) { 
        console.log(res.windowHeight)
          that.setData({ 
              clientHeight:res.windowHeight
        }); 
      } 
    }) 
  },
  //协议
  goxieyi(){
   wx.navigateTo({
     url: '/pages/oppoint/oppoint',
   })
  },
  //获取输入款内容
  content(e){
    phone=e.detail.value
  },
  password(e){
    password=e.detail.value
  },
  onkeydown(e) {

  },
  //登录事件
  goadmin(e){
    let flag = false  //表示账户是否存在,false为初始值
    if(phone=='')
    {
      wx.showToast({
        icon:'none',
        title: '请输入手机号！',
      })
    }else if(password==''){
      wx.showToast({
        icon:'none',
        title: '请输入密码！',
      })
    }else if(this.data.isChecked==true){
      var postData = {
        "phone": phone,
        "password": password,
      }
      wx.request({
        method: 'POST',
        url: app.globalData.ServerApi+'/login',
        header: { 'content-type': 'application/JSON' },
        data: JSON.stringify(postData),
        fail: function(){
          wx.showToast({
          title: '提交失败！！！',
          icon: 'loading',
          duration: 1500
          })
        },
        success: function (res) {
          console.log(res)
          if(res.data == 'done'){
            wx.setStorageSync("sessionid", res.header["Set-Cookie"])
            wx.showToast({
            title: '登录成功！！！',//这里打印出登录成功
            icon: 'success',
            duration: 1000
            })
            wx.reLaunch({
              url: '../deviceList/deviceList',
            })
          }else if( res.data == 'pwdError' ){
            wx.showToast({
              title: '密码错误！',
              icon: 'error',
              duration: 1000
              })
          }else if( res.data == 'phoneError' ){
            wx.showToast({
              title: '手机号错误！',
              icon: 'error',
              duration: 1000
              })
          }else{
            wx.showToast({
              title: '请重试！',
              icon: 'loading',
              duration: 1000
              })
          }
        }
      })
    }else{
      wx.showToast({
        title: '请先勾选协议',
        icon: 'none',
        duration: 2000,
      })
    }
  },
})
 
