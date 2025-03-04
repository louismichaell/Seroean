const jwt = require('jsonwebtoken');
const bcrypt = require('bcrypt');
const userModule = require('../auth/auth.module');
const { sendResetPasswordEmail } = require('../utils/email');
const Boom = require('@hapi/boom');
const { JWT_SECRET, RESET_PASSWORD_EXPIRY } = process.env;

const handleRequestResetPassword = async (email) => {
    if (!email) {
        throw Boom.badRequest('Email harus diisi.');
    }

    const user = await userModule.findUserByEmail(email.toLowerCase());
    if (!user) {
        throw Boom.notFound('Email tidak terdaftar.');
    }

    const resetToken = jwt.sign(
        { userId: user.user_id },
        JWT_SECRET,
        { expiresIn: RESET_PASSWORD_EXPIRY || '30m' }
    );

    await userModule.storeResetToken(user.user_id, resetToken);
    await sendResetPasswordEmail(user.email, resetToken);
};

const handleResetPassword = async (token, newPassword) => {
    if (!token || !newPassword) {
        throw Boom.badRequest('Token dan kata sandi baru harus diisi.');
    }

    if (newPassword.length < 8) {
        throw Boom.badRequest('Kata sandi harus memiliki minimal 8 karakter.');
    }

    const existingToken = await userModule.findResetToken(token);
    if (!existingToken) {
        throw Boom.unauthorized('Token tidak valid atau sudah digunakan.');
    }

    let decoded;
    try {
        decoded = jwt.verify(token, JWT_SECRET);
    } catch (error) {
        throw Boom.unauthorized('Token reset password telah kadaluarsa atau tidak valid.');
    }

    const userId = decoded.userId;
    const user = await userModule.findUserById(userId);
    if (!user) {
        throw Boom.notFound('Pengguna tidak ditemukan.');
    }

    const hashedPassword = await bcrypt.hash(newPassword, 10);
    await userModule.updatePassword(userId, hashedPassword);
    await userModule.deleteResetToken(userId);

    return { message: "Kata sandi berhasil diperbarui." };
};

module.exports = { handleRequestResetPassword, handleResetPassword };