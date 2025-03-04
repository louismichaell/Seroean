const crypto = require('crypto');
const sejarahModule = require('../sejarah/sejarah.module');
const { uploadFileToCloudStorage } = require('../server/storage');
const { getCurrentTime } = require('../utils/time');

const getAllSejarah = async () => {
    return await sejarahModule.getAllSejarah();
};

const getSejarahById = async (sejarah_id) => {
    return await sejarahModule.getSejarahById(sejarah_id);
};

const addSejarah = async (sejarahData) => {
    const sejarah_id = `sejarah-${crypto.randomUUID()}`;

    const newSejarah = {
        sejarah_id,
        tipe: 'sejarah',
        ...sejarahData
    };

    await sejarahModule.storeSejarah(newSejarah);
};

const uploadSejarahImages = async (payload) => {
    const imageFields = ['foto', 'image', 'image2', 'image3'];
    const uploadedImages = {};

    for (const field of imageFields) {
        if (payload[field]) {
            const file = payload[field];

            if (Array.isArray(file)) {
                throw new Error(`Hanya boleh mengunggah 1 file untuk ${field}.`);
            }
            const fileName = `sejarah-${getCurrentTime()}-${file.hapi.filename}`;

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

module.exports = { getAllSejarah, getSejarahById, addSejarah, uploadSejarahImages };
