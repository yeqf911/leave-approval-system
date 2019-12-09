<template>
  <el-card class="box-card">
    <div slot="header" class="clearfix"></div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="creatorName" label="姓名" width="120"></el-table-column>
      <el-table-column prop="type" label="请假类型" width="120"></el-table-column>
      <el-table-column prop="status" label="当前状态" width="200"></el-table-column>
      <el-table-column prop="courseName" label="请假课程" width="120"></el-table-column>
      <el-table-column prop="leaveSince" label="开始日期" width="120"></el-table-column>
      <el-table-column prop="leaveUntil" label="结束日期" width="120"></el-table-column>
      <el-table-column prop="leaveDays" label="请假天数" width="120"></el-table-column>
      <el-table-column prop="reason" label="请假理由"></el-table-column>
    </el-table>
  </el-card>
</template>

<script lang="ts">
export default {
  data() {
    return {
      tableData: [],
      userInfo: {
        id: 0,
        name: "",
        role: "",
        username: ""
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
      this.$http
        .get(url)
        .then(res => {
          this.tableData = res.data;
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
    }
  }
};
</script>

<style lang="less" scoped></style>
