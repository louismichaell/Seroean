const crypto = require('crypto');
const notifikasiModule = require('../notifikasi/notifikasi.module');
const { getCurrentTime } = require('../utils/time');

const getAllNotifikasi = async () => {
    return await notifikasiModule.getAllNotifikasi();
};

const addNotifikasi = async (notifikasiData) => {
    const notifikasi_id = `notifikasi-${crypto.randomUUID()}`;

    const newNotifikasi = {
        notifikasi_id,
        ...notifikasiData,
        datetime: getCurrentTime()
    };

    await notifikasiModule.storeNotifikasi(newNotifikasi);
};

module.exports = { getAllNotifikasi, addNotifikasi };
