const crypto = require('crypto');
const budayaModule = require('../budaya/budaya.module');
const { uploadFileToCloudStorage } = require('../server/storage');
const { getCurrentTime } = require('../utils/time');

const getAllBudaya = async () => {
    return await budayaModule.getAllBudaya();
};

const getBudayaById = async (budaya_id) => {
    return await budayaModule.getBudayaById(budaya_id);
};

const addBudaya = async (budayaData) => {
    const budaya_id = `budaya-${crypto.randomUUID()}`;

    const newBudaya = {
        budaya_id,
        tipe: 'budaya',
        ...budayaData
    };

    await budayaModule.storeBudaya(newBudaya);
};

const uploadBudayaImages = async (payload) => {
    const imageFields = ['foto', 'image', 'image2', 'image3'];
    const uploadedImages = {};

    for (const field of imageFields) {
        if (payload[field]) {
            const file = payload[field];
            if (Array.isArray(file)) {
                throw new Error(`Hanya boleh mengunggah 1 file untuk ${field}.`);
            }
            const fileName = `budaya-${getCurrentTime()}-${file.hapi.filename}`;
            if (!['image/jpeg', 'image/png'].includes(file.hapi.headers['content-type'])) {
                throw new Error(`File pada ${field} harus berformat JPEG atau PNG.`);
            }
            if (file.bytes > 2 * 1024 * 1024) {
                throw new Error(`Ukuran file pada ${field} tidak boleh lebih dari 2MB.`);
            }
            uploadedImages[field] = await uploadFileToCloudStorage(file, fileName, file.hapi.headers['content-type']);
        }
    }

    return uploadedImages;
};

module.exports = { getAllBudaya, getBudayaById, addBudaya, uploadBudayaImages };
