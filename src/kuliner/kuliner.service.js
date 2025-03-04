const crypto = require('crypto');
const kulinerModule = require('../kuliner/kuliner.module');
const { uploadFileToCloudStorage } = require('../server/storage');
const { getCurrentTime } = require('../utils/time');

const addKuliner = async (kulinerData) => {
    const kuliner_id = `kuliner-${crypto.randomUUID()}`;

    const newKuliner = {
        kuliner_id,
        tipe: 'kuliner',
        ...kulinerData
    };

    await kulinerModule.storeKuliner(newKuliner);
};

const uploadKulinerImages = async (payload) => {
    const imageFields = ['foto', 'image', 'image2', 'image3'];
    const uploadedImages = {};

    for (const field of imageFields) {
        if (payload[field]) {
            const file = payload[field];

            if (Array.isArray(file)) {
                throw new Error(`Hanya boleh mengunggah 1 file untuk ${field}.`);
            }

            const fileName = `kuliner-${getCurrentTime()}-${file.hapi.filename}`;

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

const getAllKuliner = async () => {
    return await kulinerModule.getAllKuliner();
};

const getKulinerById = async (kuliner_id) => {
    return await kulinerModule.getKulinerById(kuliner_id);
};

module.exports = { addKuliner, uploadKulinerImages, getAllKuliner, getKulinerById };
