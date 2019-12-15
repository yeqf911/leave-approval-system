//app.js
App({
  onLaunch: function() {
    // 展示本地存储能力
    // 设置服务器地址
    var logs = wx.getStorageSync("logs") || [];
    logs.unshift(Date.now());
    wx.setStorageSync("logs", logs);

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    });
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting["scope.userInfo"]) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo;

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res);
              }
            }
          });
        }
      }
    });
  },
  globalData: {
    userInfo: null,
    endPoint: "http://127.0.0.1:9090",
    myUserInfo: null
  },

  post: function(url, data) {
    var promise = new Promise((resolve, reject) => {
      var that = this;
      var postData = data;
      wx.request({
        url: this.globalData.endPoint + url,
        data: postData,
        method: "POST",
        header: {
          "Content-Type": "application/json",
          "Access-Token": wx.getStorageSync("Access-Token") || ""
        },
        success: function(res) {
          if (res.statusCode >= 200 && res.statusCode < 400) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(err) {
          reject("网络错误");
        }
      });
    });
    return promise;
  },

  get: function(url, data) {
    var promise = new Promise((resolve, reject) => {
      var that = this;
      var postData = data;
      wx.request({
        url: this.globalData.endPoint + url,
        data: postData,
        method: "GET",
        header: {
          "Content-Type": "application/json",
          "Access-Token": wx.getStorageSync("Access-Token") || ""
        },
        success: function(res) {
          if (res.statusCode >= 200 && res.statusCode < 400) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(err) {
          reject("网络错误");
        }
      });
    });
    return promise;
  },

  put: function(url, data) {
    var promise = new Promise((resolve, reject) => {
      var that = this;
      var postData = data;
      wx.request({
        url: this.globalData.endPoint + url,
        data: postData,
        method: "PUT",
        header: {
          "Content-Type": "application/json",
          "Access-Token": wx.getStorageSync("Access-Token") || ""
        },
        success: function(res) {
          if (res.statusCode >= 200 && res.statusCode < 400) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(err) {
          reject("网络错误");
        }
      });
    });
    return promise;
  },

  delete: function(url, data) {
    var promise = new Promise((resolve, reject) => {
      var that = this;
      var postData = data;
      wx.request({
        url: this.globalData.endPoint + url,
        data: postData,
        method: "DELETE",
        header: {
          "Content-Type": "application/json",
          "Access-Token": wx.getStorageSync("Access-Token") || ""
        },
        success: function(res) {
          if (res.statusCode >= 200 && res.statusCode < 400) {
            resolve(res.data);
          } else {
            reject(res.data);
          }
        },
        fail: function(err) {
          reject("网络错误");
        }
      });
    });
    return promise;
  }
});
