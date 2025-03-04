const crypto = require('crypto');
const pertanyaanModule = require('../pertanyaan/pertanyaan.module');
const { getCurrentTime } = require('../utils/time');

const getAllPertanyaan = async () => {
    return await pertanyaanModule.getAllPertanyaan();
};

const getPertanyaanById = async (pertanyaan_id) => {
    return await pertanyaanModule.getPertanyaanById(pertanyaan_id);
};

const addPertanyaan = async (pertanyaanData) => {
    const pertanyaan_id = `pertanyaan-${crypto.randomUUID()}`;

    const newPertanyaan = {
        pertanyaan_id,
        ...pertanyaanData,
        datetime: getCurrentTime()
    };

    await pertanyaanModule.storePertanyaan(newPertanyaan);
};

module.exports = { getAllPertanyaan, getPertanyaanById, addPertanyaan };
