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
    leaveSince: '2019-12-19',
    leaveUntil: '2019-12-20',
    courses: [],
    courseIndex: 0,
    reason: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getCourses();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () { },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.getCourses();
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

  // 获取课程列表
  getCourses: function () {
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

  bindTypeChange: function (e) {
    console.log("picker type 发生选择改变，携带值为", e.detail.value);

    this.setData({
      typeIndex: e.detail.value
    });
  },

  bindCourseChange: function (e) {
    this.setData({
      courseIndex: e.detail.value
    });
  },

  bindLeaveSinceChange: function (e) {
    this.setData({
      leaveSince: e.detail.value
    });
  },

  bindLeaveUntilChange: function (e) {
    this.setData({
      leaveUntil: e.detail.value
    });
  },

  bindChangeReason: function (e) {
    this.setData({
      reason: e.detail.value
    });
  },

  getDiffbetweenDate: function (d1, d2) {
    var dateStart = new Date(d1);
    var dateEnd = new Date(d2);
    var difValue = (dateEnd - dateStart) / (1000 * 60 * 60 * 24);
    return difValue;
  },

  makeForm: function () {
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
    return form;
  },

  // 发送创建请假条的请求
  submitUpdate: function () {
    var formData = this.makeForm();
    var url = "/leave_requests"
    app
      .post(url, formData)
      .then(res => {
        wx.navigateBack({
          delta: 1
        });
      })
      .catch(err => {
        wx.showToast({
          title: "添加请假条异常" + err.message,
          icon: "none",
          duration: 1000
        });
      });
  }
});
