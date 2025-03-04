const sejarahService = require('../sejarah/sejarah.service');
const Boom = require('@hapi/boom');

const getAllSejarah = async (request, h) => {
    try {
        const sejarahList = await sejarahService.getAllSejarah();

        return h.response({
            status: 200,
            message: 'Data sejarah berhasil diambil.',
            data: sejarahList,
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

const getSejarahById = async (request, h) => {
    try {
        const { sejarah_id } = request.params;
        const sejarah = await sejarahService.getSejarahById(sejarah_id);

        if (!sejarah) {
            const boomError = Boom.notFound('Sejarah tidak ditemukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        return h.response({
            status: 200,
            message: 'Data sejarah berhasil ditemukan.',
            data: sejarah,
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

const addSejarah = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { nama, deskripsi } = request.payload;
        const allowedParams = ['nama', 'deskripsi', 'foto', 'image', 'image2', 'image3'];
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

        if (!nama || !deskripsi) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const uploadedImages = await sejarahService.uploadSejarahImages(request.payload);

        await sejarahService.addSejarah({ nama, deskripsi, ...uploadedImages });

        return h.response({
            status: 201,
            message: 'Sejarah berhasil ditambahkan.',
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

module.exports = { getAllSejarah, getSejarahById, addSejarah };
