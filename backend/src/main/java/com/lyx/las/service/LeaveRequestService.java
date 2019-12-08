package com.lyx.las.service;


import com.lyx.las.dao.LeaveRequestMapping;
import com.lyx.las.model.LeaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestMapping leaveRequestMapping;

    public List<LeaveRequest> getUserLeaveRequests(int id) {
        List<LeaveRequest> leaveRequestList = leaveRequestMapping.getLeaveRequestByUserId(id);
        return leaveRequestList;
    }

    public void createLeaveRequest(LeaveRequest leaveRequest) {
//        leaveRequest.setStatus(Status.ASSIGNED_TO_ACADEMY);
        leaveRequestMapping.insertLeaveRequest(leaveRequest);
    }

    public void updateLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestMapping.updateLeaveRequest(leaveRequest);
    }

    public LeaveRequest getLeaveRequestById(int id) {
        return leaveRequestMapping.getLeaveRequestById(id);
    }

    public void deleteLeaveRequest(int id) {
        leaveRequestMapping.deleteLeaveRequest(id);
    }

    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestMapping.getAllLeaveRequests();
    }

    public List<LeaveRequest> getLeaveRequestsByAssignToId(int assignToId) {
        return leaveRequestMapping.getLeaveRequestsByAssignToId(assignToId);
    }
}
