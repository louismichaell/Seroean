const firestore = require('../server/firebase');
const ulasanCollection = firestore.collection('ulasan');

const storeUlasan = async (ulasanData) => {
    const ulasanRef = ulasanCollection.doc(ulasanData.ulasan_id);
    await ulasanRef.set(ulasanData);
};

const getUlasanByPlace = async (place_id, place_type) => {
    const snapshot = await ulasanCollection
        .where('place_id', '==', place_id)
        .where('place_type', '==', place_type)
        .orderBy('created_at', 'desc')
        .get();

    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        ulasan_id: doc.id,
        ...doc.data()
    }));
};

module.exports = { storeUlasan, getUlasanByPlace };
