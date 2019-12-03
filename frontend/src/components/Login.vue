<template>
  <div class="login_conponent">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="avatar" />
      </div>

      <el-form
        :model="form"
        :rules="rules"
        label-width="0px"
        class="login_form"
        ref="form"
      >
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            prefix-icon="el-icon-lock"
            type="password"
          ></el-input>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item class="btns">
          <el-button @click="login" type="primary">登录</el-button>
          <el-button @click="reset" type="info">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: "admin",
        password: "admin"
      },
      rules: {
        username: [
          { required: true, message: "请输入用户名" },
          { min: 3, max: 32, message: "长度在 3 到 32 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码" },
          { min: 3, max: 11, message: "长度在 3 到 11 个字符", trigger: "blur" }
        ]
      }
    };
  },
  // 方法
  methods: {
    // 登录函数处理逻辑
    login() {
      this.$refs.form.validate(async valid => {
        if (!valid) {
          return;
        }

        // 请求登录接口
        this.$http
          .post("/api/login", this.form)
          .then(res => {
            this.$message.success("登陆成功");
            // 登录成功将token保存在本地
            console.log(res);

            window.sessionStorage.setItem(
              "Access-Token",
              res.data.access_token
            );
            // 跳转到 home 页面
            this.$router.push("/home");
          })
          .catch(error => {
            if (!error.response || error.response.status >= 500) {
              return this.$message.error("登录异常");
            }
            if (error.response.status === 401) {
              return this.$message.error("密码错误");
            }
            if (error.response.status === 404) {
              return this.$message.error("用户不存在");
            }
          });
      });
    },

    reset() {
      this.$refs.form.resetFields();
    }
  }
};
</script>

<style lang="less" scoped>
.login_conponent {
  background-color: #2b4b6b;
  height: 100%;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
      transform: translate();
    }
  }
}

.login_form {
  position: absolute;
  bottom: 20%;
  width: 100%;
  padding: 0 40px;
  box-sizing: border-box;

  .btns {
    position: absolute;
    left: 50%;
    transform: translate(-50%, 0);
  }
}
</style>
