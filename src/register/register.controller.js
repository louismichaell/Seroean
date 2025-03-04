const registerService = require('./register.service');
const Boom = require('@hapi/boom');

const registerHandler = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const allowedParams = ['name', 'email', 'password', 'location'];
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

        const { name, email, password, location } = request.payload;
        if (!name || !email || !password || !location) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (!emailRegex.test(email) || /\r|\n/.test(email)) {
            const boomError = Boom.badRequest('Format email tidak valid, harap masukkan alamat email yang benar.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        if (password.length < 8) {
            const boomError = Boom.badRequest('Kata sandi harus memiliki minimal 8 karakter.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const passwordRegex = /^(?=.*[A-Z])(?=.*[\d\W]).+$/;
        if (!passwordRegex.test(password)) {
            const boomError = Boom.badRequest('Kata sandi harus mengandung setidaknya satu huruf besar dan satu angka atau karakter spesial.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        await registerService.registerUser(request.payload);
        return h.response({
            status: 201,
            message: 'Pengguna berhasil didaftarkan.',
            error: false
        }).code(201);

    } catch (error) {
        if (error.message.includes('already exists')) {
            const boomError = Boom.conflict('Email sudah digunakan, harap gunakan alamat email lain.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const boomError = Boom.badRequest(error.message);
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true
        }).code(boomError.output.statusCode);
    }
};

module.exports = {
    registerHandler
};
