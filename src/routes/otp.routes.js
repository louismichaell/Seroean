const { requestOTP, verifyOTP } = require('../otp/otp.controller');

module.exports = [
    {
        method: 'POST',
        path: '/otp/request',
        handler: requestOTP,
        options: { 
            auth: false 
        }
    },
    {
        method: 'POST',
        path: '/otp/verify',
        handler: verifyOTP,
        options: { 
            auth: false 
        }
    }
];
