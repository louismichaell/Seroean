const crypto = require('crypto');
const otpModule = require('../otp/otp.module');
const { sendOTPEmail } = require('../utils/email');
const Boom = require('@hapi/boom');

const generateOTP = () => {
    return crypto.randomInt(100000, 999999).toString();
};

const handleRequestOTP = async (email) => {
    if (!email) {
        throw Boom.badRequest('Email harus diisi.');
    }

    const user = await otpModule.findUserByEmail(email.toLowerCase());
    if (!user) {
        throw Boom.notFound('Email tidak terdaftar.');
    }

    const otp = generateOTP();
    const otpExpiresAt = Date.now() + 5 * 60 * 1000;

    await otpModule.storeOTP(user.user_id, otp, otpExpiresAt);
    await sendOTPEmail(user.email, otp);
};

const handleVerifyOTP = async (email, otp) => {
    if (!email || !otp) {
        throw Boom.badRequest('Email dan OTP harus diisi.');
    }

    const user = await otpModule.findUserByEmail(email.toLowerCase());
    if (!user) {
        throw Boom.notFound('Email tidak ditemukan.');
    }

    const storedOTP = await otpModule.findOTP(user.user_id);
    if (!storedOTP) {
        throw Boom.unauthorized('OTP tidak valid atau telah kadaluarsa.');
    }

    if (storedOTP.otp !== otp) {
        throw Boom.unauthorized('OTP yang dimasukkan salah.');
    }

    if (Date.now() > storedOTP.otpExpiresAt) {
        throw Boom.unauthorized('OTP telah kadaluarsa.');
    }

    await otpModule.deleteOTP(user.user_id);

    return { message: 'Verifikasi OTP Berhasil.' };
};

module.exports = { handleRequestOTP, handleVerifyOTP };
