const crypto = require('crypto');
const ulasanModule = require('../ulasan/ulasan.module');
const { getCurrentTime } = require('../utils/time');

const addUlasan = async (ulasanData) => {
    const ulasan_id = `ulasan-${crypto.randomUUID()}`;

    const newUlasan = {
        ulasan_id,
        ...ulasanData,
        created_at: getCurrentTime()
    };

    await ulasanModule.storeUlasan(newUlasan);
};

const getUlasanByPlace = async (place_id, place_type) => {
    return await ulasanModule.getUlasanByPlace(place_id, place_type);
};

module.exports = { addUlasan, getUlasanByPlace };
