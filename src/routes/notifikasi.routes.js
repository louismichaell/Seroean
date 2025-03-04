const { getAllNotifikasi, addNotifikasi } = require('../notifikasi/notifikasi.controller');

module.exports = [
    {
        method: 'GET',
        path: '/notifikasi',
        handler: getAllNotifikasi,
        options: { auth: 'jwt' }
    },
    {
        method: 'POST',
        path: '/notifikasi/add',
        handler: addNotifikasi,
        options: { auth: 'jwt' }
    }
];
