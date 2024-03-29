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
    reject: false,
    hiddenUpdate: false,
    hiddenApproval: true,
    status: 'ASSIGNED_TO_INSTRUCTOR'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      lrId: options.id
    });
    this.loadLeaveRequest();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.loadLeaveRequest();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () { },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () { },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () { },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () { },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { },

  updateLr: function () {
    var lr = JSON.stringify(this.data.leaveRequest);
    wx.navigateTo({
      url: "/pages/form/form?leaveRequest=" + lr
    });
  },

  // 审批一个请假条
  approvalLr: function () {
    var url = "/leave_requests/" + this.data.leaveRequest.id + "/approval"
    app
      .put(url)
      .then(res => {
        this.setData({
          leaveRequest: res
        });
        wx.showToast({
          title: "审批通过",
          icon: "success",
          duration: 1000
        });
      })
      .catch(err => {
        wx.showToast({
          title: "审批异常",
          icon: "none",
          duration: 2000
        });
      });
    this.loadLeaveRequest();
    this.onChangeStatus();
  },

  // 对于修改按钮和审批按钮是否可点击的配置
  onChangeStatus: function () {
    if (app.globalData.userRole == "Student") {
      this.setData({
        hiddenUpdate: false,
        hiddenApproval: true
      })
    } else if ((app.globalData.userRole == "Instructor" && this.data.status == "ASSIGNED_TO_INSTRUCTOR") ||
      (app.globalData.userRole == "Admin" && this.data.status == "ASSIGNED_TO_ACADEMY") ||
      (app.globalData.userRole == "Teacher" && this.data.status == "ASSIGNED_TO_TEACHER")) {
      this.setData({
        hiddenUpdate: true,
        hiddenApproval: false
      })
    } else {
      this.setData({
        hiddenUpdate: true,
        hiddenApproval: true
      })
    }
  },

  // 加载数据
  loadLeaveRequest: function () {
    const url = "/leave_requests/" + this.data.lrId;
    app
      .get(url)
      .then(res => {
        res.submitDate = res.submitDate.split("T")[0];
        res.leaveSince = res.leaveSince.split("T")[0];
        res.leaveUntil = res.leaveUntil.split("T")[0];

        // 这下面一串 if else 是控制 步骤条显示的数据的
        if (res.status == "ASSIGNED_TO_INSTRUCTOR") {
          this.setData({
            instructor: "进行中",
            academy: "未开始",
            teacher: "未开始",
            instructorStatus: "process",
            academyStatus: "",
            teacherStatus: "",
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

        // 给请假条对象赋值
        this.setData({
          leaveRequest: res,
          updateBtnHidden: res.status != "ASSIGNED_TO_INSTRUCTOR",
          status: res.status
        });

        this.onChangeStatus();
      })
      .catch(err => {
        wx.showToast({
          title: "获取请假条异常",
          icon: "none",
          duration: 1000
        });
      });
  }
});
