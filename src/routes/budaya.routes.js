const { getAllBudaya, getBudayaById, addBudaya } = require('../budaya/budaya.controller');

module.exports = [
    {
        method: 'GET',
        path: '/budaya',
        handler: getAllBudaya,
        options: { auth: 'jwt' }
    },
    {
        method: 'GET',
        path: '/budaya/{budaya_id}',
        handler: getBudayaById,
        options: { auth: 'jwt' }
    },
    {
        method: 'POST',
        path: '/budaya/add',
        handler: addBudaya,
        options: {
            auth: 'jwt',
            payload: {
                allow: ['multipart/form-data'],
                multipart: true,
                maxBytes: 2 * 1024 * 1024,
                output: 'stream'
            }
        }
    }
];
