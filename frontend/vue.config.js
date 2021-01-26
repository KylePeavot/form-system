module.exports = {
  outputDir: "../backend/src/main/resources/static",
  chainWebpack: config => {
    config
    .plugin('html')
    .tap(args => {
      args[0].title = "WEFFS";
      return args;
    })
  }
}