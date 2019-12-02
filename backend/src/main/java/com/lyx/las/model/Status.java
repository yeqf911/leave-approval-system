package com.lyx.las.model;

public enum Status {
    INSTRUCTOR_APPROVAL("辅导员审批"), // 辅导员
    ACADEMY_APPROVAL("学院审批"),    // 学院
    TEACHER_APPROVAL("任课教师审批");     // 任课教师

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}