package com.lyx.las.controller;

import com.lyx.las.dao.CourseMapper;
import com.lyx.las.errors.Error_400;
import com.lyx.las.errors.Error_403;
import com.lyx.las.errors.Error_404;
import com.lyx.las.helper.Utils;
import com.lyx.las.model.LeaveRequest;
import com.lyx.las.model.Status;
import com.lyx.las.model.User;
import com.lyx.las.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leave_requests")
@CrossOrigin(origins = "http://localhost:8080")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getUserLeaveRequests(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        List<LeaveRequest> leaveRequests;
        if (currentUser.getRole().equals(User.ROLE_STUDENT)) {
            leaveRequests = leaveRequestService.getUserLeaveRequests(currentUser.getId());
        } else if (currentUser.getRole().equals(User.ROLE_TEACHER)) {
            leaveRequests = leaveRequestService.getLeaveRequestsByAssignToId(currentUser.getId());
        } else {
            leaveRequests = leaveRequestService.getAllLeaveRequests();
        }

        return ResponseEntity.ok().body(leaveRequests);
    }

    @PostMapping
    public ResponseEntity<LeaveRequest> addLeaveRequests(@RequestBody LeaveRequest leaveRequest, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        leaveRequest.setCreatorId(currentUser.getId());
        leaveRequest.setCreatorName(currentUser.getName());
        leaveRequest.setStatus(Status.ASSIGNED_TO_INSTRUCTOR);
        leaveRequest.setSubmitDate(new Date());
        leaveRequest.setCourseName(courseMapper.getCourseNameById(leaveRequest.getCourseId()));
        leaveRequest.setAssignToId(courseMapper.getTeacherByCourseId(leaveRequest.getCourseId()));
        leaveRequestService.createLeaveRequest(leaveRequest);
        return new ResponseEntity<>(leaveRequest, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequest> getLeaveRequest(@PathVariable("id") int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (currentUser.getRole().equals(User.ROLE_STUDENT) && leaveRequest.getCreatorId() != currentUser.getId()) {
            throw new Error_404("leave request not found");
        }
        return ResponseEntity.ok().body(leaveRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequest> updateLeaveRequest(@PathVariable("id") int id,
                                                           @RequestBody Map<String, String> userInfo,
                                                           Authentication authentication) {
        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (leaveRequest == null) {
            throw new Error_404("leave request not found");
        }

        if (leaveRequest.getStatus() != Status.ASSIGNED_TO_INSTRUCTOR) {
            throw new Error_403("this leave request can not be updated");
        }

        User currentUser = (User) authentication.getPrincipal();
        if (leaveRequest.getCreatorId() != currentUser.getId()) {
            throw new Error_403("you can not access leave request which not belongs to you");
        }

        String leaveSince = userInfo.getOrDefault("leaveSince", Utils.date2String(leaveRequest.getLeaveSince()));
        String leaveUntil = userInfo.getOrDefault("leaveUntil", Utils.date2String(leaveRequest.getLeaveUntil()));
        String courseId = userInfo.getOrDefault("courseId", leaveRequest.getCourseId() + "");
        String leaveDays = userInfo.getOrDefault("leaveDays", leaveRequest.getLeaveDays() + "");
        String type = userInfo.getOrDefault("type", leaveRequest.getType());
        String reason = userInfo.getOrDefault("reason", leaveRequest.getReason());

        try {
            leaveRequest.setLeaveSince(Utils.string2Date(leaveSince));
            leaveRequest.setLeaveUntil(Utils.string2Date(leaveUntil));
        } catch (ParseException e) {
            throw new Error_400("invalid date format");
        }

        leaveRequest.setLeaveDays(Integer.parseInt(leaveDays));
        leaveRequest.setCourseId(Integer.parseInt(courseId));
        leaveRequest.setCourseName(courseMapper.getCourseNameById(leaveRequest.getCourseId()));
        leaveRequest.setType(type);
        leaveRequest.setReason(reason);

        leaveRequestService.updateLeaveRequest(leaveRequest);
        return ResponseEntity.ok().body(leaveRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLeaveRequest(@PathVariable("id") int id, Authentication authentication) {
        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (leaveRequest == null) {
            throw new Error_404("leave request not found");
        }

        User currentUser = (User) authentication.getPrincipal();

        if (leaveRequest.getCreatorId() != currentUser.getId()) {
            throw new Error_403("you can not access leave request which not belongs to you");
        }
        leaveRequestService.deleteLeaveRequest(id);

        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/all")
    @PreAuthorize("!hasRole('Student')")
    public ResponseEntity<List<LeaveRequest>> getAllLeaveRequests(Authentication authentication) {
        List<LeaveRequest> leaveRequestList = leaveRequestService.getAllLeaveRequests();
        return ResponseEntity.ok().body(leaveRequestList);
    }

    @GetMapping("/assigned")
    @PreAuthorize("hasRole('Admin') or hasRole('Teacher') or hasRole('Instructor')")
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsAssignedToMe(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        List<LeaveRequest> leaveRequestList = leaveRequestService.getLeaveRequestsByAssignToId(currentUser.getId());
        return ResponseEntity.ok().body(leaveRequestList);
    }

    @PutMapping("/{id}/approval")
    @PreAuthorize("hasRole('Admin') or hasRole('Teacher') or hasRole('Instructor')")
    public ResponseEntity<LeaveRequest> approvalLeaveRequest(@PathVariable("id") int id, Authentication authentication) {
        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (leaveRequest == null) {
            throw new Error_404("leave request not found");
        }
        User currentUser = (User) authentication.getPrincipal();

        switch (currentUser.getRole()) {
            // 辅导员审批通过交由学院审批
            case User.ROLE_INSTRUCTOR:
                if (leaveRequest.getStatus() == Status.ASSIGNED_TO_INSTRUCTOR) {
                    // id 为 1 的用户为学院管理员，代表学院角色
                    leaveRequest.setAssignToId(1);
                    leaveRequest.setStatus(Status.ASSIGNED_TO_ACADEMY);
                } else {
                    throw new Error_400("you cannot approval this leave request");
                }
                break;
            // 学院审批完成后，交由任课老师审批
            case User.ROLE_ADMIN:
                if (leaveRequest.getStatus() == Status.ASSIGNED_TO_ACADEMY) {
                    int teacherId = courseMapper.getTeacherByCourseId(leaveRequest.getCourseId());
                    leaveRequest.setAssignToId(teacherId);
                    leaveRequest.setStatus(Status.ASSIGNED_TO_TEACHER);
                } else {
                    throw new Error_400("you cannot approval this leave request");
                }
                break;
            // 老师审批完成后，请假条就审批通过
            case User.ROLE_TEACHER:
                if (leaveRequest.getStatus() == Status.ASSIGNED_TO_TEACHER) {
                    leaveRequest.setStatus(Status.APPROVED);
                } else {
                    throw new Error_400("you cannot approval this leave request");
                }
                break;
        }

        leaveRequestService.updateLeaveRequest(leaveRequest);
        return ResponseEntity.ok().body(leaveRequest);
    }
}
