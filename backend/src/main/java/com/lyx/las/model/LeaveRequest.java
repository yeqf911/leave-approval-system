package com.lyx.las.model;

import java.util.Date;

public class LeaveRequest {
    // 数据库中的id
    private int id;
    // 创建请假条的用户（学生）的id
    private int creatorId;
    // 创建请假条的学生姓名
    private String creatorName;
    // 指派给的审核人的id
    private int assignToId;
    // 该请假条请假的课程id
    private int courseId;
    // 该请假条请假的课程名称
    private String courseName;
    // 提交的日期
    private Date submitDate;
    // 请假开始日期
    private Date leaveSince;
    // 请假结束日期
    private Date leaveUntil;
    // 没什么用
    private String period;
    // 请假的天数
    private int leaveDays;
    // 请假的类型（病假，事假，婚假，丧假）
    private String type;
    // 请假的理由
    private String reason;
    // 该请假条所处的状态（例如现阶段轮到谁审核了）
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public int getAssignToId() {
        return assignToId;
    }

    public void setAssignToId(int assignToId) {
        this.assignToId = assignToId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public Date getLeaveSince() {
        return leaveSince;
    }

    public void setLeaveSince(Date leaveSince) {
        this.leaveSince = leaveSince;
    }

    public Date getLeaveUntil() {
        return leaveUntil;
    }

    public void setLeaveUntil(Date leaveUntil) {
        this.leaveUntil = leaveUntil;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
