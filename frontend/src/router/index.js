import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../components/Login";
import Home from "../components/Home";
import Welcome from "../components/Welcome";
import Leaverequests from "../components/LeaveRequests";
import Courses from "../components/Courses";
import Teachers from "../components/Teachers";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    name: "login",
    component: Login
  },
  {
    path: "/home",
    name: "home",
    component: Home,
    redirect: "/welcome",
    children: [
      { path: "/welcome", component: Welcome },
      { path: "/leaverequests", component: Leaverequests },
      { path: "/courses", component: Courses },
      { path: "/teaches", component: Teachers }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

// 设置路有导航守卫
router.beforeEach((to, from, next) => {
  // 查询本地浏览器存储的token值
  const accessToken = window.sessionStorage.getItem("Access-Token");
  // 如果访问的是登录页面，检测当前是否登录过了，如果是则跳转到home页面
  if (to.path === "/login") {
    if (!accessToken) {
      return next();
    }
    return next("/home");
  }

  // 如果浏览器没有token值，说明还没有登录或者已经退出登录，页面跳转到登录页面
  if (!accessToken) {
    return next("/login");
  }
  next();
});

export default router;
