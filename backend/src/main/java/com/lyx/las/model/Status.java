package com.lyx.las.model;

public enum Status {
    ASSIGNED_TO_INSTRUCTOR("辅导员审批"), // 辅导员
    ASSIGNED_TO_ACADEMY("学院审批"),    // 学院
    ASSIGNED_TO_TEACHER("任课教师审批"),     // 任课教师
    APPROVED("审批通过");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}