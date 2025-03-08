const userModule = require('../user/user.module');

const getUserBiodata = async (userId) => {
    const user = await userModule.findUserById(userId);
    if (!user) {
        throw new Error('Pengguna tidak ditemukan, Silahkan coba lagi.');
    }

    return {
        user_id: user.user_id,
        name: user.name,
        email: user.email,
        username: user.username,
        phone: user.phone,
        location: user.location,
        role_id: user.role_id,
        profilePicture: user.profilePicture,
        createdAt: user.createdAt
    };
};

const updateUserBiodata = async (userId, biodata) => {
    return await userModule.updateUserData(userId, biodata);
};

const getUserById = async (userId) => {
    const user = await userModule.findUserById(userId);
    if (!user) {
        throw new Error('Pengguna tidak ditemukan.');
    }

    return {
        user_id: user.user_id,
        name: user.name,
        email: user.email,
        location: user.location,
        profilePicture: user.profilePicture,
        createdAt: user.createdAt
    };
};

const findUserByEmail = async (email) => {
    return await userModule.findUserByEmail(email);
};

module.exports = {
    getUserBiodata,
    updateUserBiodata,
    getUserById,
    findUserByEmail
};