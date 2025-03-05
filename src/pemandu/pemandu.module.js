const firestore = require('../server/firebase');
const pemanduCollection = firestore.collection('pemandu');

const storePemandu = async (pemanduData) => {
    const pemanduRef = pemanduCollection.doc(pemanduData.pemandu_id);
    await pemanduRef.set(pemanduData);
};

const getPemanduByWisataId = async (wisata_id) => {
    const snapshot = await pemanduCollection.where('wisata_id', '==', wisata_id).get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        pemandu_id: doc.id,
        ...doc.data()
    }));
};

module.exports = { storePemandu, getPemanduByWisataId };
