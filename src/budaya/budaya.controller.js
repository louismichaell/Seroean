const budayaService = require('../budaya/budaya.service');
const Boom = require('@hapi/boom');

const getAllBudaya = async (request, h) => {
    try {
        const budayaList = await budayaService.getAllBudaya();

        return h.response({
            status: 200,
            message: 'Data budaya berhasil diambil.',
            data: budayaList,
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

const getBudayaById = async (request, h) => {
    try {
        const { budaya_id } = request.params;
        const budaya = await budayaService.getBudayaById(budaya_id);

        if (!budaya) {
            const boomError = Boom.notFound('Budaya tidak ditemukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        return h.response({
            status: 200,
            message: 'Data budaya berhasil ditemukan.',
            data: budaya,
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

const addBudaya = async (request, h) => {
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

        const uploadedImages = await budayaService.uploadBudayaImages(request.payload);

        await budayaService.addBudaya({ nama, deskripsi, ...uploadedImages });

        return h.response({
            status: 201,
            message: 'Budaya berhasil ditambahkan.',
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

module.exports = { getAllBudaya, getBudayaById, addBudaya };
