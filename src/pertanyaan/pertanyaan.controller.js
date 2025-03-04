const pertanyaanService = require('../pertanyaan/pertanyaan.service');
const Boom = require('@hapi/boom');

const getAllPertanyaan = async (request, h) => {
    try {
        const pertanyaanList = await pertanyaanService.getAllPertanyaan();

        return h.response({
            status: 200,
            message: 'Data pertanyaan berhasil diambil.',
            data: pertanyaanList,
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

const getPertanyaanById = async (request, h) => {
    try {
        const { pertanyaan_id } = request.params;
        const pertanyaan = await pertanyaanService.getPertanyaanById(pertanyaan_id);

        if (!pertanyaan) {
            const boomError = Boom.notFound('Pertanyaan tidak ditemukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        return h.response({
            status: 200,
            message: 'Data pertanyaan berhasil ditemukan.',
            data: pertanyaan,
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

const addPertanyaan = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { tipe, pertanyaan, jawaban } = request.payload;
        const allowedParams = ['tipe', 'pertanyaan', 'jawaban'];
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

        if (!tipe || !pertanyaan || !jawaban) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        await pertanyaanService.addPertanyaan({ tipe, pertanyaan, jawaban });

        return h.response({
            status: 201,
            message: 'Pertanyaan berhasil ditambahkan.',
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

module.exports = { getAllPertanyaan, getPertanyaanById, addPertanyaan };
