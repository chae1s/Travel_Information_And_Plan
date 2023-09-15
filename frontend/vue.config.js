module.exports = {
  lintOnSave: false,
  outputDir: "../src/main/resources/static",

  devServer: {
    proxy: {
      '/': {
        target: 'http://localhost:8080',
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
