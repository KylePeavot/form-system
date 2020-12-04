const {config} = require('./wdio.shared.conf.ts')

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
                args: ['--no-sandbox', '--disable-dev-shm-usage', "--headless", '--disable-gpu']
            }
        },
    ]
}
