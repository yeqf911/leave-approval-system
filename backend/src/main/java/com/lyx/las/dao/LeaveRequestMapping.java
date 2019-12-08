package com.lyx.las.dao;


import com.lyx.las.model.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface LeaveRequestMapping {

    List<LeaveRequest> getLeaveRequestByUserId(@Param("creatorId") int creatorId);

    void insertLeaveRequest(LeaveRequest leaveRequest) throws DataAccessException;

    LeaveRequest getLeaveRequestById(@Param("id") int id);

    void updateLeaveRequest(LeaveRequest leaveRequest) throws DataAccessException;

    void deleteLeaveRequest(@Param("id") int id);

    List<LeaveRequest> getAllLeaveRequests();

    List<LeaveRequest> getLeaveRequestsByAssignToId(@Param("assignToId") int assignToId);

}
