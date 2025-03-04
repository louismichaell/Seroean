const ulasanService = require('../ulasan/ulasan.service');
const Boom = require('@hapi/boom');

const addUlasan = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { userId } = request.auth.credentials;
        const { place_id, place_type, komentar, rating } = request.payload;
        const allowedParams = ['place_id', 'place_type', 'komentar', 'rating'];
        const payloadKeys = Object.keys(request.payload);

        const invalidParams = payloadKeys.filter(key => !allowedParams.includes(key));
        if (invalidParams.length > 0) {
            const boomError = Boom.badRequest(`Parameter / ${invalidParams.join(', ')} / tidak diizinkan.`);
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        if (!place_id || !place_type || !komentar || rating === undefined) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        if (!['wisata', 'kuliner'].includes(place_type)) {
            const boomError = Boom.badRequest("Tipe tempat harus 'wisata' atau 'kuliner'.");
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const ratingValue = parseFloat(rating);
        if (isNaN(ratingValue) || ratingValue < 0 || ratingValue > 5) {
            const boomError = Boom.badRequest('Rating harus berupa angka antara 0 hingga 5.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        await ulasanService.addUlasan({ place_id, place_type, user_id: userId, komentar, rating });

        return h.response({
            status: 201,
            message: 'Ulasan berhasil ditambahkan.',
            error: false
        }).code(201);

    } catch (error) {
        const boomError = Boom.badRequest(error.message);
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true
        }).code(boomError.output.statusCode);
    }
};

const getUlasanByWisata = async (request, h) => {
    try {
        const { wisata_id } = request.params;
        const ulasan = await ulasanService.getUlasanByPlace(wisata_id, 'wisata');

        return h.response({
            status: 200,
            message: 'Ulasan berhasil diambil.',
            data: ulasan,
            error: false
        }).code(200);
    } catch (error) {
        const boomError = Boom.badRequest(error.message);
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true
        }).code(boomError.output.statusCode);
    }
};

const getUlasanByKuliner = async (request, h) => {
    try {
        const { kuliner_id } = request.params;
        const ulasan = await ulasanService.getUlasanByPlace(kuliner_id, 'kuliner');

        return h.response({
            status: 200,
            message: 'Ulasan berhasil diambil.',
            data: ulasan,
            error: false
        }).code(200);
    } catch (error) {
        const boomError = Boom.badRequest(error.message);
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true
        }).code(boomError.output.statusCode);
    }
};

module.exports = { addUlasan, getUlasanByWisata, getUlasanByKuliner };
