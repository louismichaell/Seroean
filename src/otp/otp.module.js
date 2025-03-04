const firestore = require('../server/firebase');
const userCollection = firestore.collection('users');

const findUserByEmail = async (email) => {
    const snapshot = await userCollection.where('email', '==', email).limit(1).get();
    if (snapshot.empty) return null;
    return { user_id: snapshot.docs[0].id, ...snapshot.docs[0].data() };
};

const storeOTP = async (userId, otp, otpExpiresAt) => {
    await userCollection.doc(userId).update({ otp, otpExpiresAt });
};

const findOTP = async (userId) => {
    const userRef = userCollection.doc(userId);
    const doc = await userRef.get();
    return doc.exists ? doc.data() : null;
};

const deleteOTP = async (userId) => {
    await userCollection.doc(userId).update({ otp: null, otpExpiresAt: null });
};

module.exports = {
    findUserByEmail,
    storeOTP,
    findOTP,
    deleteOTP
};
