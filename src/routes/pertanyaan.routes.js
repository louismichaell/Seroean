const { getAllPertanyaan, getPertanyaanById, addPertanyaan } = require('../pertanyaan/pertanyaan.controller');

module.exports = [
    {
        method: 'GET',
        path: '/pertanyaan',
        handler: getAllPertanyaan,
        options: { auth: 'jwt' }
    },
    {
        method: 'GET',
        path: '/pertanyaan/{pertanyaan_id}',
        handler: getPertanyaanById,
        options: { auth: 'jwt' }
    },
    {
        method: 'POST',
        path: '/pertanyaan/add',
        handler: addPertanyaan,
        options: { auth: 'jwt' }
    }
];
