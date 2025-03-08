const jwt = require('jsonwebtoken');
const loginModule = require('./login.module');
const bcrypt = require('bcrypt');
const utils = require('../utils/utils');
const { JWT_SECRET, JWT_EXPIRES_IN } = process.env;

const loginUser = async ({ email, password }) => {
    const normalizedEmail = email.toLowerCase();
    const user = await loginModule.findUserByEmail(normalizedEmail);
    
    if (!user) {
        throw new Error('Pengguna tidak ditemukan, silakan periksa email Anda atau daftar terlebih dahulu.');
    }

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
        throw new Error('Kata sandi salah, Silahkan periksa kembali dan coba lagi.');
    }

    const token = jwt.sign(
        { userId: user.user_id }, 
        JWT_SECRET, 
        { 
            algorithm: 'HS256',
            expiresIn: JWT_EXPIRES_IN 
        }
    );

    const responseData = {
        token,
        user_id: user.user_id,
        name: user.name,
        email: user.email,
        location: user.location,
        createdAt: user.createdAt
    };

    const responseLogin = utils.removeNullProperties(responseData);
    return responseLogin;
};

module.exports = { loginUser };
