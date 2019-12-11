module.exports = {
  devServer: {
    open: true,
    host: "localhost",
    port: 8080,
    https: false,
    proxy: {
      "/api": {
        target: "http://127.0.0.1:9090",
        ws: true,
        changeOrigin: true,
        pathRewrite: {
          "^/api": "" //请求的时候使用这个api就可以
        }
      }
    }
  },

  outputDir: "target/dist",
  assetsDir: "static"
};
