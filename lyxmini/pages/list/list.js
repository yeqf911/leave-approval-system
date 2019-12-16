// pages/list/list.js
const app = getApp();

const statusTips = {
  APPROVED: "审批通过",
  ASSIGNED_TO_INSTRUCTOR: "辅导员审批中",
  ASSIGNED_TO_ACADEMY: "学院审批中",
  ASSIGNED_TO_TEACHER: "任课教师审批中",
  REJECT: "驳回"
};

Page({
  /**
   * 页面的初始数据
   */
  data: {
    listData: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    app
      .get("/leave_requests", {})
      .then(res => {
        // 对请求的数据做预处理
        res.forEach(lr => {
          lr.submitDate = lr.submitDate.split("T")[0];
          lr.leaveSince = lr.leaveSince.split("T")[0];
          lr.leaveUntil = lr.leaveUntil.split("T")[0];
          if (lr.status == "APPROVED") {
            lr.statusColor = "green";
          } else if (lr.status == "REJECT") {
            lr.statusColor = "red";
          } else {
            lr.statusColor = "blue";
          }
          lr.status = statusTips[lr.status];
        });

        this.setData({
          listData: res,
          type: options.type,
          loading: false
        });
      })
      .catch(err => {
        wx.showToast({
          title: "获取请假条异常",
          icon: "none",
          duration: 1000
        });
      });
  },

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
  onShareAppMessage: function() {}
});