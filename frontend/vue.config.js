module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8090',
        ws: true,
        changeOrigin: true
      }
    }
  },

  outputDir: 'target/dist',
  assetsDir: 'static'
};