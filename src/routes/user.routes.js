const { getUserBiodataHandler, checkEmailExistsHandler, updateUserBiodataHandler, getUserDetailByIdHandler } = require('../user/user.controller');

module.exports = [
    {
        method: 'POST',
        path: '/check-email',
        handler: checkEmailExistsHandler,
        options: {
            auth: false,
        },
    },
    {
        method: 'GET',
        path: '/biodata',
        handler: getUserBiodataHandler,
        options: {
            auth: 'jwt',
        },
    },
    {
        method: 'POST',
        path: '/biodata/update',
        handler: updateUserBiodataHandler,
        options: {
            auth: 'jwt',
            payload: {
                output: 'stream',
                parse: true,
                multipart: true,
                maxBytes: 2 * 1024 * 1024, 
            },
        },
    },
    {
        method: 'GET',
        path: '/user/{user_id}',
        handler: getUserDetailByIdHandler,
        options: {
            auth: 'jwt',
        },
    }
];