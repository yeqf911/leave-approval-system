// pages/form/form.js
const app = getApp();

const typeTips = {
  病假: 0,
  事假: 1,
  婚假: 2,
  丧假: 3
};

const courseTips = {
  高等数学: 0,
  线性代数: 1,
  计算机网络: 2,
  操作系统: 3,
  编译原理: 4
};

Page({
  /**
   * 页面的初始数据
   */
  data: {
    leaveRequest: null,
    types: ["病假", "事假", "婚假", "丧假"],
    typeIndex: 0,
    leaveSince: null,
    leaveUntil: null,
    courses: [],
    courseIndex: 0,
    reason: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var leaveRequest = JSON.parse(options.leaveRequest);
    this.setData({
      leaveRequest: leaveRequest
    });
    this.getCourses();

    this.setData({
      typeIndex: typeTips[this.data.leaveRequest.type],
      coursesIndex: courseTips[this.data.leaveRequest.courseName],
      leaveSince: this.data.leaveRequest.leaveSince,
      leaveUntil: this.data.leaveRequest.leaveUntil,
      reason: this.data.leaveRequest.reason
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
  onShareAppMessage: function() {},

  getCourses: function() {
    app
      .get("/courses")
      .then(res => {
        var courses = [];
        res.forEach(element => {
          courses.push(element.name);
        });
        this.setData({
          courses: courses
        });
      })
      .catch(err => {
        wx.showToast({
          title: "获取课程列表异常",
          icon: "none",
          duration: 1000
        });
      });
  },

  bindTypeChange: function(e) {
    console.log("picker type 发生选择改变，携带值为", e.detail.value);

    this.setData({
      typeIndex: e.detail.value
    });
  },

  bindCourseChange: function(e) {
    this.setData({
      courseIndex: e.detail.value
    });
  },

  bindLeaveSinceChange: function(e) {
    this.setData({
      leaveSince: e.detail.value
    });
  },

  bindLeaveUntilChange: function(e) {
    this.setData({
      leaveUntil: e.detail.value
    });
  },

  bindChangeReason: function(e) {
    this.setData({
      reason: e.detail.value
    });
  },

  getDiffbetweenDate: function(d1, d2) {
    var dateStart = new Date(d1);
    var dateEnd = new Date(d2);
    var difValue = (dateEnd - dateStart) / (1000 * 60 * 60 * 24);
    return difValue;
  },

  makeForm: function() {
    var form = {
      type: this.data.types[this.data.typeIndex],
      reason: this.data.reason,
      courseId: parseInt(this.data.courseIndex) + 1,
      leaveSince: this.data.leaveSince,
      leaveUntil: this.data.leaveUntil,
      leaveDays: this.getDiffbetweenDate(
        this.data.leaveSince,
        this.data.leaveUntil
      )
    };
    // this.data.leaveRequest.type = this.data.types[this.data.typeIndex];
    // this.data.leaveRequest.reason = this.data.reason;
    // this.data.leaveRequest.courseId = parseInt(this.data.courseIndex) + 1;
    // this.data.leaveRequest.leaveSince = this.data.leaveSince;
    // this.data.leaveRequest.leaveUntil = this.data.leaveUntil;
    // this.data.leaveRequest.courseName = this.data.courses[
    //   this.data.courseIndex
    // ];
    // this.data.leaveRequest.leaveDays = this.getDiffbetweenDate(
    //   this.data.leaveSince,
    //   this.data.leaveUntil
    // );
    return form;
  },

  submitUpdate: function() {
    var formData = this.makeForm();
    var url = "/leave_requests/" + this.data.leaveRequest.id;
    app
      .put(url, formData)
      .then(res => {
        wx.navigateBack({
          delta: 1
        });
      })
      .catch(err => {
        wx.showToast({
          title: "更新请假条异常" + err.message,
          icon: "none",
          duration: 1000
        });
      });
  }
});
