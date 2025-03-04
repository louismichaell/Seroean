const firestore = require('../server/firebase');
const kulinerCollection = firestore.collection('kuliner');

const storeKuliner = async (kulinerData) => {
    const kulinerRef = kulinerCollection.doc(kulinerData.kuliner_id);
    await kulinerRef.set(kulinerData);
};

const getAllKuliner = async () => {
    const snapshot = await kulinerCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        kuliner_id: doc.id,
        ...doc.data()
    }));
};

const getKulinerById = async (kuliner_id) => {
    const doc = await kulinerCollection.doc(kuliner_id).get();
    return doc.exists ? { kuliner_id: doc.id, ...doc.data() } : null;
};

module.exports = { storeKuliner, getAllKuliner, getKulinerById };
