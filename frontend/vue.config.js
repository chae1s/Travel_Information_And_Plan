module.exports = {
  lintOnSave: false,
  outputDir: "../src/main/resources/static",

  devServer: {
    proxy: {
      '/': {
        target: 'http://ec2-3-35-51-90.ap-northeast-2.compute.amazonaws.com:8080/',
        changeOrigin: true,
        ws: false
      }
    }
  },

  pluginOptions: {
    vuetify: {
			// https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
		}
  }
}
