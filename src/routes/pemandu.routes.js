const { addPemanduHandler, getPemanduByWisataIdHandler } = require('../pemandu/pemandu.controller');

module.exports = [
    {
        method: 'POST',
        path: '/pemandu/add',
        handler: addPemanduHandler,
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
        path: '/pemandu/{wisata_id}',
        handler: getPemanduByWisataIdHandler,
        options: {
            auth: 'jwt',
        },
    }
];
