// pages/login/login.js
//获取应用实例
const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    username: "",
    password: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {},

  usernameInput: function(e) {
    this.setData({
      username: e.detail.value
    });
  },

  // 获取输入密码
  passwordInput: function(e) {
    this.setData({
      password: e.detail.value
    });
  },

  // 登录
  login: function() {
    if (this.data.username.length == 0 || this.data.password.length == 0) {
      wx.showToast({
        title: "用户名和密码不能为空",
        icon: "none",
        duration: 1000
      });
    } else {
      // 这里请求登录接口
      app
        .post("/login", {
          username: this.data.username,
          password: this.data.password
        })
        .then(res => {
          console.log(res);
          wx.hideLoading();
          wx.showToast({
            title: "登录成功",
            icon: "success",
            duration: 2000
          });
          wx.setStorageSync("Access-Token", res.access_token);
          wx.navigateTo({
            url: "../index/index"
          });
        })
        .catch(res => {
          console.log(res);
          wx.hideLoading();
          wx.showToast({
            title: "登录异常",
            icon: "none",
            duration: 1000
          });
        });
      wx.showLoading({
        title: "登录中"
      });
    }
  }
});
