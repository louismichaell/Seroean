const firestore = require('../server/firebase');
const sejarahCollection = firestore.collection('sejarah');

const storeSejarah = async (sejarahData) => {
    const sejarahRef = sejarahCollection.doc(sejarahData.sejarah_id);
    await sejarahRef.set(sejarahData);
};

const getAllSejarah = async () => {
    const snapshot = await sejarahCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        sejarah_id: doc.id,
        ...doc.data()
    }));
};

const getSejarahById = async (sejarah_id) => {
    const doc = await sejarahCollection.doc(sejarah_id).get();
    return doc.exists ? { sejarah_id: doc.id, ...doc.data() } : null;
};

module.exports = { storeSejarah, getAllSejarah, getSejarahById };
