const otpService = require('../otp/otp.service');
const Boom = require('@hapi/boom');

const requestOTP = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { email } = request.payload;
        const allowedParams = ['email'];
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

        if (!email) {
            const boomError = Boom.badRequest('Harap isi email.');
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

        await otpService.handleRequestOTP(email);
        return h.response({ status: 200, message: 'Berhasil mengirim kode OTP.', error: false }).code(200);
    } catch (error) {
        return h.response({
            status: error.output?.statusCode || 400,
            message: error.message,
            error: true
        }).code(error.output?.statusCode || 400);
    }
};

const verifyOTP = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        const boomError = Boom.badRequest('Data tidak boleh kosong.');
        return h.response({
            status: boomError.output.statusCode,
            message: boomError.message,
            error: true,
        }).code(boomError.output.statusCode);
    }

    try {
        const { email, otp } = request.payload;
        const allowedParams = ['email', 'otp'];
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

        if (!email || !otp) {
            const boomError = Boom.badRequest('Harap isi email dan OTP.');
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

        const response = await otpService.handleVerifyOTP(email, otp);
        return h.response({ status: 200, message: response.message, error: false }).code(200);
    } catch (error) {
        return h.response({
            status: error.output?.statusCode || 400,
            message: error.message,
            error: true
        }).code(error.output?.statusCode || 400);
    }
};

module.exports = { requestOTP, verifyOTP };
