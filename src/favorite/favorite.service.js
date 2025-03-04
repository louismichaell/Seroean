const crypto = require('crypto');
const favoriteModule = require('../favorite/favorite.module');

const addFavorite = async (user_id, place_id, place_type, is_favorite) => {
    if (!user_id || !place_id || !place_type || is_favorite === undefined) {
        throw new Error("Data tidak lengkap, harap periksa kembali.");
    }

    const favorite_id = `fav-${crypto.randomUUID()}`;

    const newFavorite = {
        favorite_id,
        user_id,
        place_id,
        place_type,
        is_favorite
    };

    await favoriteModule.storeFavorite(newFavorite);
    return newFavorite;
};

const checkFavoriteExists = async (user_id, place_id) => {
    return await favoriteModule.isFavoriteExist(user_id, place_id);
};

const checkFavoriteExistsById = async (favorite_id) => {
    return await favoriteModule.isFavoriteExistById(favorite_id);
};

const removeFavorite = async (favorite_id) => {
    await favoriteModule.deleteFavorite(favorite_id);
};

const getFavorites = async (user_id) => {
    return await favoriteModule.getFavoritesByUser(user_id);
};

module.exports = { addFavorite, removeFavorite, getFavorites, checkFavoriteExists, checkFavoriteExistsById };
