package com.lyx.las.dao;


import com.lyx.las.model.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface LeaveRequestMapping {

    // 通过创建人的ID查找该用户所有的请假条
    List<LeaveRequest> getLeaveRequestByUserId(@Param("creatorId") int creatorId);

    // 插入一条请假条申请
    void insertLeaveRequest(LeaveRequest leaveRequest) throws DataAccessException;

    // 根据ID查找一条请假申请
    LeaveRequest getLeaveRequestById(@Param("id") int id);

    // 更新一条请假申请记录
    void updateLeaveRequest(LeaveRequest leaveRequest) throws DataAccessException;

    // 删除一条请假申请
    void deleteLeaveRequest(@Param("id") int id);

    // 获取数据库中所有的请假申请
    List<LeaveRequest> getAllLeaveRequests();

    // 根据指派人（审核人）ID查找请假申请
    List<LeaveRequest> getLeaveRequestsByAssignToId(@Param("assignToId") int assignToId);

}
