<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>请假申请列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" class="input-with-select">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button :disabled="this.userRole != 'Student'" type="primary" @click="showDialog">添加请假条</el-button>
        </el-col>
      </el-row>

      <el-table :data="leaveRequestsList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="creatorName" label="姓名"></el-table-column>
        <el-table-column prop="type" label="请假类型"></el-table-column>
        <el-table-column label="当前状态" width="140" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == 'ASSIGNED_TO_INSTRUCTOR'">辅导员审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'ASSIGNED_TO_ACADEMY'">学院审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'ASSIGNED_TO_TEACHER'">任课教师审批中</el-tag>
            <el-tag v-else-if="scope.row.status == 'APPROVED'" type="success">审批通过</el-tag>
            <el-tag v-else-if="scope.row.status == 'REJECT'" type="danger">驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="courseName" label="请假课程"></el-table-column>
        <el-table-column prop="leaveSince" label="开始日期"></el-table-column>
        <el-table-column prop="leaveUntil" label="结束日期"></el-table-column>
        <el-table-column prop="leaveDays" label="请假天数"></el-table-column>
        <el-table-column prop="reason" label="请假理由"></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              :disabled="scope.row.status != 'ASSIGNED_TO_INSTRUCTOR'"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="scope.row.status != 'ASSIGNED_TO_INSTRUCTOR'"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加请假条对话框 -->
      <el-dialog title="添加请假条" :visible.sync="dialogVisible" width="50%">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="请假日期">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-date-picker
                  type="date"
                  placeholder="开始日期"
                  v-model="leaveRequestform.leaveSince"
                  style="width: 100%;"
                ></el-date-picker>
              </el-col>
              <el-col :span="8">
                <el-date-picker
                  type="date"
                  placeholder="结束日期"
                  v-model="leaveRequestform.leaveUntil"
                  style="width: 100%;"
                ></el-date-picker>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addLeaveRequest">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
  <!-- <el-card class="box-card">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>活动管理</el-breadcrumb-item>
      <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>
    <div slot="header" class="clearfix"></div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="creatorName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="type" label="请假类型" width="120"></el-table-column>
      <el-table-column prop="status" label="当前状态" width="200"></el-table-column>
      <el-table-column prop="courseName" label="请假课程" width="120"></el-table-column>
      <el-table-column prop="leaveSince" label="开始日期" width="120"></el-table-column>
      <el-table-column prop="leaveUntil" label="结束日期" width="120"></el-table-column>
      <el-table-column prop="leaveDays" label="请假天数" width="120"></el-table-column>
      <el-table-column prop="reason" label="请假理由" width="200"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>-->
</template>

<script lang="ts">
export default {
  data() {
    return {
      leaveRequestsList: [],
      userInfo: {
        id: 0,
        name: "",
        role: "",
        username: ""
      },
      dialogVisible: false,
      userRole: window.sessionStorage.getItem("userRole"),
      leaveRequestform: {
        name: "",
        region: "",
        leaveSince: "",
        leaveUntil: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      }
    };
  },
  created() {
    this.getUserLeaveRequests();
    this.getCurrentUser();
  },

  methods: {
    getUserLeaveRequests() {
      var url = "/api/leave_requests";
      if (this.userRole != "Student") {
        url = "/api/leave_requests/all";
      }
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
    getCurrentUser() {
      this.$http
        .get("/api/users/self")
        .then(res => {
          this.userInfo = res.data;
        })
        .catch(err => {
          console.log(err.response);
          this.$message.error("获取当前用户失败，请重新登录");
          window.sessionStorage.removeItem("Access-Token");
          return this.$router.push("/login");
        });
    },
    handleEdit(index, row) {
      console.log(index, row);
    },
    handleDelete(index, row) {
      console.log(index, row);
    },

    showDialog() {
      this.dialogVisible = true;
    },

    addLeaveRequest() {
      this.dialogVisible = false;
    }
  }
};
</script>

<style lang="less" scoped>
</style>
