const firestore = require('../server/firebase');
const notifikasiCollection = firestore.collection('notifikasi');

const storeNotifikasi = async (notifikasiData) => {
    const notifikasiRef = notifikasiCollection.doc(notifikasiData.notifikasi_id);
    await notifikasiRef.set(notifikasiData);
};

const getAllNotifikasi = async () => {
    const snapshot = await notifikasiCollection.get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        notifikasi_id: doc.id,
        ...doc.data()
    }));
};

module.exports = { storeNotifikasi, getAllNotifikasi };
