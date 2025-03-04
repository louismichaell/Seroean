const { addFavorite, removeFavorite, getFavorites } = require('../favorite/favorite.controller');

module.exports = [
    {
        method: 'POST',
        path: '/favorite/add',
        handler: addFavorite,
        options: { auth: 'jwt' }
    },
    {
        method: 'DELETE',
        path: '/favorite/remove',
        handler: removeFavorite,
        options: { auth: 'jwt' }
    },
    {
        method: 'GET',
        path: '/favorite',
        handler: getFavorites,
        options: { auth: 'jwt' }
    }
];
