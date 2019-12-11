<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>请假申请列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="search" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button :disabled="this.userRole != 'Student'" type="primary" @click="showDialog">添加请假条</el-button>
        </el-col>
      </el-row>

      <el-table :data="leaveRequestsList" border stripe>
        <el-table-column type="index" align="center"></el-table-column>
        <el-table-column
          v-if="this.userRole != 'Student'"
          prop="creatorName"
          label="姓名"
          width="80"
          align="center"
        ></el-table-column>
        <el-table-column label="提交日期" width="100" align="center">
          <template slot-scope="scope">{{scope.row.submitDate.split('T')[0]}}</template>
        </el-table-column>
        <el-table-column prop="type" label="请假类型" width="80" align="center"></el-table-column>
        <el-table-column label="当前状态" width="140" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 'ASSIGNED_TO_INSTRUCTOR'">辅导员审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'ASSIGNED_TO_ACADEMY'">学院审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'ASSIGNED_TO_TEACHER'">任课教师审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'APPROVED'" type="success">审批通过</el-tag>
            <el-tag v-else-if="scope.row.status == 'REJECT'" type="danger">驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="courseName" label="请假课程" width="140" align="center"></el-table-column>
        <el-table-column label="开始日期" width="100" align="center">
          <template slot-scope="scope">{{scope.row.leaveSince.split('T')[0]}}</template>
        </el-table-column>
        <el-table-column label="结束日期" width="100" align="center">
          <template slot-scope="scope">{{scope.row.leaveUntil.split('T')[0]}}</template>
        </el-table-column>
        <el-table-column prop="leaveDays" label="请假天数" width="80" align="center"></el-table-column>
        <el-table-column prop="reason" label="请假理由" align="center"></el-table-column>
        <el-table-column label="操作" width="120" align="center" v-if="this.userRole == 'Student'">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="editLrRow(scope.row, scope.$index)"
              :disabled="scope.row.status != 'ASSIGNED_TO_INSTRUCTOR'"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="openDeleteDialog(scope.row.id, scope.$index)"
              :disabled="scope.row.status != 'ASSIGNED_TO_INSTRUCTOR'"
            ></el-button>
          </template>
        </el-table-column>

        <!-- 辅导员审批按钮 -->
        <el-table-column label="审批" width="100" align="center" v-if="this.userRole == 'Instructor'">
          <template slot-scope="scope">
            <el-button
              type="success"
              icon="el-icon-check"
              size="mini"
              @click="approval(scope.row.id, scope.$index)"
              :disabled="scope.row.status != 'ASSIGNED_TO_INSTRUCTOR'"
            ></el-button>
          </template>
        </el-table-column>

        <!-- 教务处审批按钮 -->
        <el-table-column label="审批" width="100" align="center" v-if="this.userRole == 'Admin'">
          <template slot-scope="scope">
            <el-button
              type="success"
              icon="el-icon-check"
              size="mini"
              @click="approval(scope.row.id, scope.$index)"
              :disabled="scope.row.status != 'ASSIGNED_TO_ACADEMY'"
            ></el-button>
          </template>
        </el-table-column>

        <!-- 任课教师审批按钮 -->
        <el-table-column label="审批" width="100" align="center" v-if="this.userRole == 'Teacher'">
          <template slot-scope="scope">
            <el-button
              type="success"
              icon="el-icon-check"
              size="mini"
              @click="approval(scope.row.id, scope.$index)"
              :disabled="scope.row.status != 'ASSIGNED_TO_TEACHER'"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加请假条对话框 -->
      <el-dialog title="添加请假条" :visible.sync="addDialogVisible" width="50%">
        <el-form ref="form" :model="leaveRequestform" label-width="80px">
          <el-form-item label="请假日期">
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="-"
              :picker-options="leaveSinceOption"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="请假类型">
            <el-select v-model="leaveRequestform.type" placeholder="请选择请假类型">
              <el-option label="病假" value="病假"></el-option>
              <el-option label="事假" value="事假"></el-option>
              <el-option label="婚假" value="婚假"></el-option>
              <el-option label="丧假" value="丧假"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请假课程">
            <el-select v-model="leaveRequestform.courseId" placeholder="请选择请假课程">
              <el-option
                v-for="course in courses"
                :key="course.id"
                :label="course.name"
                :value="course.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请假理由">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="leaveRequestform.reason"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addLeaveRequest">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 修改请假条对话框 -->
      <el-dialog title="修改请假条" :visible.sync="updateDialogVisible" width="50%">
        <el-form ref="form" :model="leaveRequestform" label-width="80px">
          <el-form-item label="请假日期">
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="-"
              :picker-options="leaveSinceOption"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="请假类型">
            <el-select v-model="leaveRequestform.type" placeholder="请选择请假类型">
              <el-option label="病假" value="病假"></el-option>
              <el-option label="事假" value="事假"></el-option>
              <el-option label="婚假" value="婚假"></el-option>
              <el-option label="丧假" value="丧假"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请假课程">
            <el-select v-model="leaveRequestform.courseId" placeholder="请选择请假课程">
              <el-option
                v-for="course in courses"
                :key="course.id"
                :label="course.name"
                :value="course.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="请假理由">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              v-model="leaveRequestform.reason"
              maxlength="50"
              show-word-limit
            ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="updateDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateLeaveRequest">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script lang="ts">
var leaveDays = 0;
export default {
  data() {
    return {
      leaveRequestsList: [],
      addDialogVisible: false,
      updateDialogVisible: false,
      userRole: "",
      dateRange: [],
      editRowIndex: 0,
      editRowId: 0,
      leaveRequestform: {
        leaveSince: "",
        leaveUntil: "",
        leaveDays: 0,
        type: "",
        reason: "",
        courseId: ""
      },
      search: "",
      courses: [],
      courseId: 0,
      courseName: "",
      leaveSinceOption: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
        onPick(dateRange) {
          var times = dateRange.maxDate - dateRange.minDate;
          var days = times / (1000 * 60 * 60 * 24);
          leaveDays = days;
        }
      }
    };
  },
  created() {
    this.getUserLeaveRequests();
    this.getCourses();
    this.userRole = window.sessionStorage.getItem("userRole");
  },

  methods: {
    getUserLeaveRequests() {
      var url = "/api/leave_requests";
      this.$http
        .get(url)
        .then(res => {
          this.leaveRequestsList = res.data;
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("获取请假条列表失败");
        });
    },

    getCourses() {
      this.$http
        .get("/api/courses")
        .then(res => {
          this.courses = res.data;
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("获取课程列表失败");
        });
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },

    showDialog() {
      this.addDialogVisible = true;
    },

    addLeaveRequest() {
      this.addDialogVisible = false;
      this.leaveRequestform.leaveSince = this.dateRange[0];
      this.leaveRequestform.leaveUntil = this.dateRange[1];
      this.leaveRequestform.leaveDays = leaveDays;
      console.log(this.leaveRequestform);

      this.$http
        .post("/api/leave_requests", this.leaveRequestform)
        .then(res => {
          this.leaveRequestsList.push(res.data);
          this.$message.success("添加请假条成功");
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("添加请假条失败");
        });
    },

    updateLeaveRequest() {
      this.updateDialogVisible = false;
      this.leaveRequestform.leaveSince = this.dateRange[0];
      this.leaveRequestform.leaveUntil = this.dateRange[1];
      this.leaveRequestform.leaveDays = leaveDays;
      console.log(this.leaveRequestform);

      this.$http
        .put("/api/leave_requests/" + this.editRowId, this.leaveRequestform)
        .then(res => {
          console.log(res.data);
          console.log(this.editRowIndex);

          this.leaveRequestsList.splice(this.editRowIndex, 1, res.data);
          this.$message.success("修改请假条成功");
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("修改请假条失败");
        });
    },

    approval(lrId, index) {
      console.log(lrId);
      var url = "/api/leave_requests/" + lrId + "/approval";
      console.log(url);

      this.$http
        .put(url)
        .then(res => {
          this.leaveRequestsList.splice(index, 1, res.data);
          this.$message.success("审批完成");
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("审批失败");
        });
    },
    editLrRow(row, index) {
      this.updateDialogVisible = true;
      this.dateRange[0] = row.leaveSince;
      this.dateRange[1] = row.leaveUntil;
      this.leaveRequestform.reason = row.reason;
      this.leaveRequestform.type = row.type;
      this.leaveRequestform.courseId = row.courseId;
      this.editRowIndex = index;
      this.editRowId = row.id;
    },
    openDeleteDialog(id, index) {
      this.$confirm("此操作将永久删除该请假条, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteLr(id, index);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },

    deleteLr(id, index) {
      var url = "/api/leave_requests/" + id;
      this.$http
        .delete(url)
        .then(res => {
          console.log(res.data);
          this.leaveRequestsList.splice(index, 1);
          this.$message.success("删除成功");
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("删除失败");
        });
    }
  }
};
</script>

<style lang="less" scoped>
</style>