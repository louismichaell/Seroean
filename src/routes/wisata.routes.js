const { addWisata, getAllWisata, getWisataById } = require('../wisata/wisata.controller');

module.exports = [
    {
        method: 'POST',
        path: '/wisata/add',
        handler: addWisata,
        options: {
            auth: 'jwt',
            payload: {
                allow: ['multipart/form-data'],
                multipart: true,
                maxBytes: 2 * 1024 * 1024,
                output: 'stream'
            }
        }
    },
    {
        method: 'GET',
        path: '/wisata',
        handler: getAllWisata,
        options: { auth: 'jwt' 
        }
    },
    {
        method: 'GET',
        path: '/wisata/{wisata_id}',
        handler: getWisataById,
        options: { auth: 'jwt' 
        }
    }
];
