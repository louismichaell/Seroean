const { getAllKuliner, getKulinerById, addKuliner } = require('../kuliner/kuliner.controller');

module.exports = [
    {
        method: 'GET',
        path: '/kuliner',
        handler: getAllKuliner,
        options: { auth: 'jwt'  }
    },
    {
        method: 'GET',
        path: '/kuliner/{kuliner_id}',
        handler: getKulinerById,
        options: { auth: 'jwt'  }
    },
    {
        method: 'POST',
        path: '/kuliner/add',
        handler: addKuliner,
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
