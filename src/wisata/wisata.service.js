const crypto = require('crypto');
const wisataModule = require('../wisata/wisata.module');
const { uploadFileToCloudStorage } = require('../server/storage');
const { getCurrentTime } = require('../utils/time');

const addWisata = async (wisataData) => {
    const wisata_id = `wisata-${crypto.randomUUID()}`;

    const newWisata = {
        wisata_id,
        tipe: 'wisata',
        ...wisataData
    };

    await wisataModule.storeWisata(newWisata);
};

const uploadWisataImages = async (payload) => {
    const imageFields = ['foto', 'image', 'image2', 'image3'];
    const uploadedImages = {};

    for (const field of imageFields) {
        if (payload[field]) {
            const file = payload[field];

            if (Array.isArray(file)) {
                throw new Error(`Hanya boleh mengunggah 1 file untuk ${field}.`);
            }

            const fileName = `wisata-${getCurrentTime()}-${file.hapi.filename}`;
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

const getAllWisata = async () => {
    return await wisataModule.getAllWisata();
};

const getWisataById = async (wisata_id) => {
    return await wisataModule.getWisataById(wisata_id);
};

module.exports = { addWisata, uploadWisataImages, getAllWisata, getWisataById };
