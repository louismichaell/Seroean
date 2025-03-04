const bcrypt = require('bcrypt');
const crypto = require('crypto');
const registerModule = require('./register.module');
const { getCurrentTime } = require('../utils/time');

const registerUser = async (userData) => {
    const { email, password, location, name } = userData;

    if (await registerModule.findUserByEmail(email)) {
        throw new Error('Email sudah digunakan, harap gunakan alamat email lain.');
    }
 
    const id = `user-${crypto.randomUUID()}`;
    const normalizedEmail = email.toLowerCase();
    const hashedPassword = await bcrypt.hash(password, 10);

    const newUser = {
        user_id: id,
        email: normalizedEmail,
        password: hashedPassword,
        location,
        name,
        profilePicture: process.env.PROFILE_PICTURE_DEFAULT,
        createdAt: getCurrentTime(),
    };

    await registerModule.addUser(newUser);
};

module.exports = {
    registerUser
};
