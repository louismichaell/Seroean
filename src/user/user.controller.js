const userService = require('../user/user.service');
const Boom = require('@hapi/boom');
const { uploadFileToCloudStorage } = require('../server/storage');

const getUserBiodataHandler = async (request, h) => {
    try {
        const { userId } = request.auth.credentials;
        const biodata = await userService.getUserBiodata(userId);

        return h.response({
            status: 200,
            message: 'Data pengguna berhasil diambil.',
            data: biodata,
            error: false,
        }).code(200);
    } catch (error) {
        return Boom.notFound(error.message);
    }
};

const updateUserBiodataHandler = async (request, h) => {
    if (!request.payload || Object.keys(request.payload).length === 0) {
        return Boom.badRequest('Data tidak boleh kosong.');
    }

    try {
        const { userId } = request.auth.credentials;
        const { name, location } = request.payload;
        
        const allowedParams = ['name', 'location', 'image'];
        const payloadKeys = Object.keys(request.payload);
        const invalidParams = payloadKeys.filter(key => !allowedParams.includes(key));

        if (invalidParams.length > 0) {
            return Boom.badRequest(`Parameter / ${invalidParams.join(', ')} / tidak diizinkan.`);
        }

        const existingUserData = await userService.getUserBiodata(userId);
        if (!existingUserData) {
            return Boom.notFound('Pengguna tidak ditemukan.');
        }

        let profilePicture = existingUserData.profilePicture;
        if (request.payload.image) {
            const file = request.payload.image;
            if (Array.isArray(file)) {
                return Boom.badRequest('Hanya boleh mengunggah 1 file gambar.');
            }

            if (!['image/jpeg', 'image/png'].includes(file.hapi.headers['content-type'])) {
                return Boom.unsupportedMediaType('Format gambar harus JPEG atau PNG.');
            }

            if (file.bytes > 2 * 1024 * 1024) {
                return Boom.payloadTooLarge('Ukuran gambar tidak boleh lebih dari 2MB.');
            }

            const fileName = `${userId}-${Date.now()}-${file.hapi.filename}`;
            profilePicture = await uploadFileToCloudStorage(file, fileName, file.hapi.headers['content-type']);
        }

        const updatedData = {
            name: name || existingUserData.name,
            location: location || existingUserData.location,
            profilePicture
        };

        await userService.updateUserBiodata(userId, updatedData);

        return h.response({
            status: 200,
            message: 'Data pengguna berhasil diperbarui.',
            data: {
                user_id: userId,
                email: existingUserData.email,
                name: updatedData.name,
                location: updatedData.location,
                profilePicture: updatedData.profilePicture
            },
            error: false,
        }).code(200);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

const getUserDetailByIdHandler = async (request, h) => {
    try {
        const { user_id } = request.params;
        const user = await userService.getUserById(user_id);

        if (!user) {
            return Boom.notFound('Pengguna tidak ditemukan.');
        }

        return h.response({
            status: 200,
            message: 'Detail pengguna berhasil diambil.',
            data: {
                user_id: user.user_id,
                name: user.name,
                email: user.email,
                location: user.location,
                profilePicture: user.profilePicture,
                createdAt: user.createdAt
            },
            error: false,
        }).code(200);
    } catch (error) {
        return Boom.badRequest(error.message);
    }
};

module.exports = {
    getUserBiodataHandler,
    updateUserBiodataHandler,
    getUserDetailByIdHandler
};
