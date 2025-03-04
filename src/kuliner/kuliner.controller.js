const kulinerService = require('../kuliner/kuliner.service');
const Boom = require('@hapi/boom');

const getAllKuliner = async (request, h) => {
    try {
        const kulinerList = await kulinerService.getAllKuliner();

        return h.response({
            status: 200,
            message: 'Data kuliner berhasil diambil.',
            data: kulinerList,
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

const getKulinerById = async (request, h) => {
    try {
        const { kuliner_id } = request.params;
        const kuliner = await kulinerService.getKulinerById(kuliner_id);

        if (!kuliner) {
            const boomError = Boom.notFound('Kuliner tidak ditemukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        return h.response({
            status: 200,
            message: 'Data kuliner berhasil ditemukan.',
            data: kuliner,
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

const addKuliner = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { nama, lokasi, provinsi, telepon, opsi, deskripsi, rating } = request.payload;
        const allowedParams = ['nama', 'lokasi', 'provinsi', 'telepon', 'opsi', 'deskripsi', 'rating', 'foto', 'image', 'image2', 'image3'];
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

        if (!nama || !lokasi || !provinsi || !telepon || !opsi || !deskripsi || rating === undefined) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const teleponValue = Number(telepon);
        if (isNaN(teleponValue)) {
            const boomError = Boom.badRequest('Nomor telepon harus berupa angka.');
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

        const uploadedImages = await kulinerService.uploadKulinerImages(request.payload);

        await kulinerService.addKuliner({ 
            nama, 
            lokasi, 
            provinsi, 
            telepon: teleponValue, 
            opsi, 
            deskripsi, 
            rating: ratingValue, 
            ...uploadedImages 
        });

        return h.response({
            status: 201,
            message: 'Kuliner berhasil ditambahkan.',
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


module.exports = { getAllKuliner, getKulinerById, addKuliner };
