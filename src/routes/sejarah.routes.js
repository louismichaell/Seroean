const { getAllSejarah, getSejarahById, addSejarah } = require('../sejarah/sejarah.controller');

module.exports = [
    {
        method: 'GET',
        path: '/sejarah',
        handler: getAllSejarah,
        options: { auth: 'jwt'  }
    },
    {
        method: 'GET',
        path: '/sejarah/{sejarah_id}',
        handler: getSejarahById,
        options: { auth: 'jwt' }
    },
    {
        method: 'POST',
        path: '/sejarah/add',
        handler: addSejarah,
        options: {
            auth: 'jwt' ,
            payload: {
                allow: ['multipart/form-data'],
                multipart: true,
                maxBytes: 2 * 1024 * 1024,
                output: 'stream'
            }
        }
    }
];
