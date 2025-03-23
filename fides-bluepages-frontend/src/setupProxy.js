const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    const options = {
        target: 'http://localhost:8089',
        changeOrigin: true,

        onProxyReq(proxyReq, req, res) {
        }
    };

    app.use(
        '/api',
        createProxyMiddleware(options)
    );
};
