<template>
  <el-container class="home-container">
    <el-header>
      <div>
        <img src="../assets/leave.png" alt="" />
        <span>学生请假审批系统</span>
      </div>
      <div>
        <el-tag type="success">{{ userInfo.name }}</el-tag>
        <el-button @click="logout" type="primary" icon="el-icon-switch-button"
          >注销</el-button
        >
      </div>
    </el-header>
    <el-container>
      <!--侧边栏 -->
      <el-aside :width="width">
        <!-- 伸缩菜单栏按钮 -->
        <div class="toggle-button" @click="toggleCollapse">|||</div>

        <!-- 侧边栏菜单区 -->
        <el-menu
          class="menu-box"
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409EFF"
          unique-opened
          :collapse="isCollapse"
          :collapse-transition="false"
          router
        >
          <!-- 菜单1 -->
          <el-menu-item index="leaverequests">
            <i class="el-icon-edit-outline"></i>
            <span slot="title">请假申请列表</span>
          </el-menu-item>

          <!-- 菜单2 -->
          <el-menu-item index="courses">
            <i class="el-icon-notebook-2"></i>
            <span slot="title">我的课程列表</span>
          </el-menu-item>

          <!-- 菜单3 -->
          <el-menu-item index="teaches">
            <i class="el-icon-s-custom"></i>
            <span slot="title">任课教师列表</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
export default {
  data() {
    return {
      userInfo: {
        id: 0,
        name: "",
        role: "",
        username: ""
      },
      isCollapse: false,
      width: "200px"
    };
  },
  created() {
    this.getCurrentUser();
  },
  methods: {
    logout() {
      window.sessionStorage.removeItem("Access-Token");
      this.$router.push("/login");
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
    // leaverequests() {
    //   this.$router.push("/leaverequests");
    // },
    // 点击按钮折叠按钮
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.width = "64px";
      } else {
        this.width = "200px";
      }
    }
  }
};
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}

.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #eee;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 10px;
    }
  }
  .el-tag {
    margin-right: 10px;
  }

  .el-button {
    height: 32px;
    padding: 0px 10px;
  }
}

.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: 0;
  }

  > span {
    font-size: 20px;
  }
}

.el-main {
  background-color: #dddddd;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
