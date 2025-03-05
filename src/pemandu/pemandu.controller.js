const pemanduService = require('../pemandu/pemandu.service');
const Boom = require('@hapi/boom');
const { getCurrentTime } = require('../utils/time');

const addPemanduHandler = async (request, h) => {
    try {
        const { name, notelp, wisata_id } = request.payload;
        const allowedParams = ['name', 'notelp', 'wisata_id', 'profilePicture'];
        const payloadKeys = Object.keys(request.payload);
        const invalidParams = payloadKeys.filter(key => !allowedParams.includes(key));

        if (invalidParams.length > 0) {
            return Boom.badRequest(`Parameter / ${invalidParams.join(', ')} / tidak diizinkan.`);
        }

        if (!name || !notelp || !wisata_id) {
            return Boom.badRequest('Harap isi semua kolom yang diperlukan.');
        }

        const phoneRegex = /^(\+?\d{1,3})?\d{8,15}$/;
        if (!phoneRegex.test(notelp)) {
            const boomError = Boom.badRequest('Silahkan gunakan format +0123456789 atau 08123456789.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        let profilePicture = null;
        if (request.payload.profilePicture) {
            const file = request.payload.profilePicture;
            if (Array.isArray(file)) {
                return Boom.badRequest('Hanya boleh mengunggah 1 file gambar.');
            }

            if (!['image/jpeg', 'image/png'].includes(file.hapi.headers['content-type'])) {
                return Boom.unsupportedMediaType('Format gambar harus JPEG atau PNG.');
            }

            if (file.bytes > 2 * 1024 * 1024) {
                return Boom.payloadTooLarge('Ukuran gambar tidak boleh lebih dari 2MB.');
            }

            const fileName = `pemandu-${getCurrentTime()}-${file.hapi.filename}`;
            profilePicture = await pemanduService.uploadPemanduImage(file, fileName, file.hapi.headers['content-type']);
        }

        await pemanduService.addPemandu({ name, notelp, wisata_id, profilePicture });

        return h.response({
            status: 201,
            message: 'Pemandu berhasil ditambahkan.',
            error: false
        }).code(201);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

const getPemanduByWisataIdHandler = async (request, h) => {
    try {
        const { wisata_id } = request.params;
        const pemanduList = await pemanduService.getPemanduByWisataId(wisata_id);

        return h.response({
            status: 200,
            message: 'Daftar pemandu berhasil diambil.',
            data: pemanduList,
            error: false
        }).code(200);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

module.exports = { addPemanduHandler, getPemanduByWisataIdHandler };
