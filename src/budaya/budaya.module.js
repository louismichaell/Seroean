const firestore = require('../server/firebase');
const budayaCollection = firestore.collection('budaya');

const storeBudaya = async (budayaData) => {
    const budayaRef = budayaCollection.doc(budayaData.budaya_id);
    await budayaRef.set(budayaData);
};

const getAllBudaya = async () => {
    const snapshot = await budayaCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        budaya_id: doc.id,
        ...doc.data()
    }));
};

const getBudayaById = async (budaya_id) => {
    const doc = await budayaCollection.doc(budaya_id).get();
    return doc.exists ? { budaya_id: doc.id, ...doc.data() } : null;
};

module.exports = { storeBudaya, getAllBudaya, getBudayaById };
