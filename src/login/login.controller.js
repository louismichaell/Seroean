const loginService = require('../login/login.service');
const userModule = require('../user/user.module');
const Boom = require('@hapi/boom');

const loginHandler = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { email, password } = request.payload;
        const allowedParams = ['email', 'password'];
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

        if (!email || !password) {
            const boomError = Boom.badRequest('Harap isi semua kolom yang diperlukan.');
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

        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (!emailRegex.test(email) || /\r|\n/.test(email)) {
            const boomError = Boom.badRequest('Format email tidak valid, harap masukkan alamat email yang benar.');
            return h.response({
                status: boomError.output.statusCode,
                message: boomError.message,
                error: true
            }).code(boomError.output.statusCode);
        }

        const user = await userModule.findUserByEmail(email);
        if (!user) {
            return Boom.notFound('User tidak ditemukan.');
        }

        if (!user.is_otp_verified) {
            return Boom.unauthorized('Akun belum diverifikasi, Silahkan masukkan OTP terlebih dahulu.');
        }

        const response = await loginService.loginUser(request.payload);
        return h.response({
            status: 200,
            message: 'Login berhasil.',
            data: response,
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

module.exports = { loginHandler };
