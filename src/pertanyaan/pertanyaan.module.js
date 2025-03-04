const firestore = require('../server/firebase');
const pertanyaanCollection = firestore.collection('pertanyaan');

const storePertanyaan = async (pertanyaanData) => {
    const pertanyaanRef = pertanyaanCollection.doc(pertanyaanData.pertanyaan_id);
    await pertanyaanRef.set(pertanyaanData);
};

const getAllPertanyaan = async () => {
    const snapshot = await pertanyaanCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        pertanyaan_id: doc.id,
        ...doc.data()
    }));
};

const getPertanyaanById = async (pertanyaan_id) => {
    const doc = await pertanyaanCollection.doc(pertanyaan_id).get();
    return doc.exists ? { pertanyaan_id: doc.id, ...doc.data() } : null;
};

module.exports = { storePertanyaan, getAllPertanyaan, getPertanyaanById };
