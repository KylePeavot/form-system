const { config } = require('./wdio.shared.conf.ts')

exports.config = {
  /**
   * base config
   */
  ...config,
  /**
   * config for local testing
   */
  maxInstances: 1,
  services: ['chromedriver'],
  capabilities: [
    {
      browserName: 'chrome',
      acceptInsecureCerts: true,
      'goog:chromeOptions': {
        args: ["--headless", '--disable-gpu', '--disable-dev-shm-usage', '--no-sandbox']
      }
    },
  ]
}
