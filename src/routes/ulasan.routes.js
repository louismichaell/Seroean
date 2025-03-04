const { addUlasan, getUlasanByWisata, getUlasanByKuliner } = require('../ulasan/ulasan.controller');

module.exports = [
    {
        method: 'POST',
        path: '/ulasan/add',
        handler: addUlasan,
        options: { auth: 'jwt',}
    },
    {
        method: 'GET',
        path: '/ulasan/wisata/{wisata_id}',
        handler: getUlasanByWisata,
        options: { auth: 'jwt' }
    },
    {
        method: 'GET',
        path: '/ulasan/kuliner/{kuliner_id}',
        handler: getUlasanByKuliner,
        options: { auth: 'jwt', }
    }
];
