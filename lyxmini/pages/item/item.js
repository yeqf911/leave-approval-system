// pages/item/item.js
const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    leaveRequest: null,
    updateBtnHidden: true,
    instructor: "",
    academy: "",
    teacher: "",
    instructorStatus: "",
    academyStatus: "",
    teacherStatus: "",
    reject: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    const url = "/leave_requests/" + options.id;
    app
      .get(url)
      .then(res => {
        res.submitDate = res.submitDate.split("T")[0];
        res.leaveSince = res.leaveSince.split("T")[0];
        res.leaveUntil = res.leaveUntil.split("T")[0];

        if (res.status == "ASSIGNED_TO_INSTRUCTOR") {
          this.setData({
            instructor: "进行中",
            academy: "未开始",
            teacher: "未开始",
            instructorStatus: "process",
            academyStatus: "",
            teacherStatus: ""
          });
        } else if (res.status == "ASSIGNED_TO_ACADEMY") {
          this.setData({
            instructor: "已完成",
            academy: "进行中",
            teacher: "未开始",
            instructorStatus: "finish",
            academyStatus: "process",
            teacherStatus: ""
          });
        } else if (res.status == "ASSIGNED_TO_TEACHER") {
          this.setData({
            instructor: "已完成",
            academy: "已完成",
            teacher: "进行中",
            instructorStatus: "finish",
            academyStatus: "finish",
            teacherStatus: ""
          });
        } else if (res.status == "APPROVED") {
          this.setData({
            instructor: "已完成",
            academy: "已完成",
            teacher: "已完成",
            instructorStatus: "finish",
            academyStatus: "finish",
            teacherStatus: "finish"
          });
        } else {
          this.setData({
            instructor: "驳回",
            academy: "驳回",
            teacher: "驳回",
            instructorStatus: "",
            academyStatus: "",
            teacherStatus: ""
          });
        }

        this.setData({
          leaveRequest: res,
          updateBtnHidden: res.status != "ASSIGNED_TO_INSTRUCTOR"
        });
        console.log(res);
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
