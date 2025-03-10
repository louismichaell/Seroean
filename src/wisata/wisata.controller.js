const wisataService = require('../wisata/wisata.service');
const Boom = require('@hapi/boom');

const addWisata = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { nama, lokasi, provinsi, deskripsi, rating, lon, lat } = request.payload;
        const allowedParams = ['nama', 'lokasi', 'provinsi', 'deskripsi', 'rating', 'foto', 'image', 'image2', 'image3', 'lon', 'lat'];
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

        if (!nama || !lokasi || !provinsi || !deskripsi || rating === undefined) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
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

        const lonValue = lon ? parseFloat(lon) : null;
        const latValue = lat ? parseFloat(lat) : null;

        if ((lon && isNaN(lonValue)) || (lat && isNaN(latValue))) {
            const boomError = Boom.badRequest('Longitude dan Latitude harus berupa angka yang valid.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const uploadedImages = await wisataService.uploadWisataImages(request.payload);
        await wisataService.addWisata({ nama, lokasi, provinsi, deskripsi, rating: ratingValue, lon: lonValue, lat: latValue, ...uploadedImages });

        return h.response({
            status: 201,
            message: 'Wisata berhasil ditambahkan.',
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

const getAllWisata = async (request, h) => {
    try {
        const wisataList = await wisataService.getAllWisata();

        return h.response({
            status: 200,
            message: 'Data wisata berhasil diambil.',
            data: wisataList,
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

const getWisataById = async (request, h) => {
    try {
        const { wisata_id } = request.params;
        const wisata = await wisataService.getWisataById(wisata_id);

        if (!wisata) {
            const boomError = Boom.notFound('Wisata tidak ditemukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        return h.response({
            status: 200,
            message: 'Data wisata berhasil ditemukan.',
            data: wisata,
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

module.exports = { 
    addWisata,
    getAllWisata,
    getWisataById 
};
