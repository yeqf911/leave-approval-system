import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import "./plugins/element";
import axios from "axios";
import "./assets/css/global.css";

// axios.defaults.baseURL = "http://127.0.0.1:8080/api/";
Vue.config.productionTip = false;

// 请求拦截器,为所有请求添加 Access-Token 请求头用于接口鉴权
axios.interceptors.request.use(config => {
  config.headers["Access-Token"] = window.sessionStorage.getItem(
    "Access-Token"
  );
  return config;
});

Vue.prototype.$http = axios;

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
