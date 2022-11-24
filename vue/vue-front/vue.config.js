const { defineConfig } = require('@vue/cli-service')
module.exports = {
  outputDir: "../../src/main/resources/static",
  devServer: {
    proxy: {
      '/api':{
        target: "http://localhost:8081",
        changeOrigin: true
      }
    }
  },
  chainWebpack: config => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule.use("vue-svg-loader").loader("vue-svg-loader");
  }
};