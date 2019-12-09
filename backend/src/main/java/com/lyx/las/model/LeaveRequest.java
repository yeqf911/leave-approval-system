package com.lyx.las.model;

import java.util.Date;

public class LeaveRequest {
    private int id;
    private int creatorId;
    private String creatorName;
    private int assignToId;
    private int courseId;
    private String courseName;
    private Date submitDate;
    private Date leaveSince;
    private Date leaveUntil;
    private String period;
    private int leaveDays;
    private String type;
    private String reason;
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
