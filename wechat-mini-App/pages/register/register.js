//index.js
//获取应用实例
const app = getApp()
 let username=''
 let password=''
Page({
  data: {
    phone: "",
    password: '',
    clientHeight:'',
    isSubmit: false,
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
    username=e.detail.value
  },
  password(e){
    password=e.detail.value
  },
  //登录事件
  goadmin(){
    
  },

  formSubmit: function (e) {
    if(username=='')
    {
      wx.showToast({
        icon:'none',
        title: '手机号不能为空！',
      })
    }else if(password==''){
      wx.showToast({
        icon:'none',
        title: '密码不能为空！',
      })
    }else if(this.data.isChecked == true){
      wx.request({
        url: app.globalData.ServerApi+"/register",  // 在 url 中传递 String 
        data: e.detail.value,
        method: 'POST',
        header: {'Content-Type': 'application/x-www-form-urlencoded'},  
        success: function (res) {
          console.log(res.data)
          console.log(res.statusCode)
          if (res.data == "done") {
            wx.showToast({
              title: '提交成功！',//这里打印出登录成功
              icon: 'success',
              duration: 1000
              })
              wx.reLaunch({
                url: '../login/login',
              })
          } else if(res.data == "exist"){
            wx.showToast({
              title: '手机号已存在！',
              icon: 'error',
              duration: 1500
              })
          }
        }
      })
      console.log('form发生了submit事件，携带数据为：', e.detail.value);
    }else{
      wx.showToast({
        title: '请先勾选协议！',
        icon: 'none',
        duration: 2000,
      })
    }
    this.setData({
      isSubmit: true
    })
  },
  formReset: function () {
    console.log('form发生了reset事件')
  }
})
 
