const firestore = require('../server/firebase');
const userCollection = firestore.collection('users');

const findUserByEmail = async (email) => {
    const snapshot = await userCollection.where('email', '==', email).limit(1).get();
    if (snapshot.empty) return null;
    return { user_id: snapshot.docs[0].id, ...snapshot.docs[0].data() };
};

const findUserById = async (userId) => {
    const userRef = userCollection.doc(userId);
    const doc = await userRef.get();
    return doc.exists ? { user_id: doc.id, ...doc.data() } : null;
};

const updatePassword = async (userId, hashedPassword) => {
    await userCollection.doc(userId).update({ password: hashedPassword });
};

const storeResetToken = async (userId, token) => {
    await userCollection.doc(userId).update({ resetToken: token });
};

const findResetToken = async (token) => {
    const snapshot = await userCollection.where('resetToken', '==', token).limit(1).get();
    return snapshot.empty ? null : snapshot.docs[0].data();
};

const deleteResetToken = async (userId) => {
    await userCollection.doc(userId).update({ resetToken: null });
};

module.exports = {
    findUserByEmail,
    findUserById,
    updatePassword,
    storeResetToken,
    findResetToken,
    deleteResetToken
};
