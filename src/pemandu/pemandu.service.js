const pemanduModule = require('../pemandu/pemandu.module');
const { uploadFileToCloudStorage } = require('../server/storage');
const crypto = require('crypto');

const addPemandu = async (pemanduData) => {
    const pemandu_id = `pemandu-${crypto.randomUUID()}`;

    const newPemandu = {
        pemandu_id,
        ...pemanduData
    };

    await pemanduModule.storePemandu(newPemandu);
};

const getPemanduByWisataId = async (wisata_id) => {
    return await pemanduModule.getPemanduByWisataId(wisata_id);
};

const uploadPemanduImage = async (file, fileName, contentType) => {
    return await uploadFileToCloudStorage(file, fileName, contentType);
};

module.exports = { addPemandu, getPemanduByWisataId, uploadPemanduImage };