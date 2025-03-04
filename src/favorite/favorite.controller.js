const favoriteService = require('../favorite/favorite.service');
const Boom = require('@hapi/boom');

const addFavorite = async (request, h) => {
    try {
        const { userId } = request.auth.credentials;
        const { place_id, place_type, is_favorite } = request.payload;

        const allowedParams = ['place_id', 'place_type', 'is_favorite'];
        const payloadKeys = Object.keys(request.payload);
        const invalidParams = payloadKeys.filter(key => !allowedParams.includes(key));

        if (invalidParams.length > 0) {
            return Boom.badRequest(`Parameter / ${invalidParams.join(', ')} / tidak diizinkan.`);
        }

        if (!place_id || !place_type || is_favorite === undefined) {
            return Boom.badRequest("Harap isi semua kolom yang diperlukan.");
        }

        if (!['wisata', 'kuliner', 'budaya', 'sejarah'].includes(place_type)) {
            return Boom.badRequest("Tipe tempat harus 'wisata', 'kuliner', 'budaya', atau 'sejarah'.");
        }

        const isAlreadyFavorite = await favoriteService.checkFavoriteExists(userId, place_id);
        if (isAlreadyFavorite) {
            return Boom.conflict("Tempat ini sudah ada dalam daftar favorit.");
        }

        const data = await favoriteService.addFavorite(userId, place_id, place_type, is_favorite);

        return h.response({
            status: 201,
            message: `Berhasil menambahkan favorit.`,
            data,
            error: false
        }).code(201);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

const removeFavorite = async (request, h) => {
    try {
        const { userId } = request.auth.credentials;
        const { favorite_id } = request.payload;

        const allowedParams = ['favorite_id'];
        const payloadKeys = Object.keys(request.payload);
        const invalidParams = payloadKeys.filter(key => !allowedParams.includes(key));

        if (invalidParams.length > 0) {
            return Boom.badRequest(`Parameter / ${invalidParams.join(', ')} / tidak diizinkan.`);
        }

        if (!favorite_id) {
            return Boom.badRequest("Harap masukkan favorite_id yang ingin dihapus.");
        }
        const isFavoriteExist = await favoriteService.checkFavoriteExistsById(favorite_id);
        if (!isFavoriteExist) {
            return Boom.notFound("Favorit ini tidak ditemukan.");
        }

        await favoriteService.removeFavorite(favorite_id);

        return h.response({
            status: 200,
            message: 'Berhasil menghapus dari favorit.',
            error: false
        }).code(200);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

const getFavorites = async (request, h) => {
    try {
        const { userId } = request.auth.credentials;
        const favorites = await favoriteService.getFavorites(userId);

        return h.response({
            status: 200,
            message: 'Daftar favorit berhasil diambil.',
            data: favorites,
            error: false
        }).code(200);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

module.exports = { addFavorite, removeFavorite, getFavorites };
