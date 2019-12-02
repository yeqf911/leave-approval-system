package com.lyx.las.model;

import java.util.Date;
import java.util.List;

public class LeaveRequest {
    private int id;
    private int creatorId;
    private Date submitDate;
    private Date leaveSince;
    private Date leaveUntil;
    private String period;
    private int leaveDays;
    private String type;
    private String reason;
    private Status status;
    private List<Course> associatedCourses;
}
