const { requestResetPassword, resetPasswordForm, processResetPassword } = require('../auth/auth.controller');

module.exports = [
    {
        method: 'POST',
        path: '/auth/request-reset-password',
        handler: requestResetPassword,
        options: {
            auth: false
        }
    },
    {
        method: 'GET',
        path: '/auth/reset-password',
        handler: resetPasswordForm,
        options: {
            auth: false
        }
    },
    {
        method: 'POST',
        path: '/auth/reset-password',
        handler: processResetPassword,
        options: {
            auth: false
        }
    }
];
