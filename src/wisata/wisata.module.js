const firestore = require('../server/firebase');
const wisataCollection = firestore.collection('wisata');

const storeWisata = async (wisataData) => {
    const wisataRef = wisataCollection.doc(wisataData.wisata_id);
    await wisataRef.set(wisataData);
};

const getAllWisata = async () => {
    const snapshot = await wisataCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        wisata_id: doc.id,
        ...doc.data()
    }));
};

const getWisataById = async (wisata_id) => {
    const doc = await wisataCollection.doc(wisata_id).get();
    return doc.exists ? { wisata_id: doc.id, ...doc.data() } : null;
};


module.exports = { storeWisata, getAllWisata, getWisataById };
