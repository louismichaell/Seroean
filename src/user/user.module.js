const firestore = require('../server/firebase');
const userCollection = firestore.collection('users');

const findUserById = async (userId) => {
    const userDoc = await userCollection.doc(userId).get();
    if (!userDoc.exists) {
        return null;
    }
    return { user_id: userDoc.id, ...userDoc.data() };
};

const updateUserData = async (userId, biodata) => {
    const userRef = userCollection.doc(userId);
    const userSnapshot = await userRef.get();

    if (!userSnapshot.exists) {
        throw new Error('Pengguna tidak ditemukan.');
    }

    const existingUserData = userSnapshot.data();
    
    const updatedData = {
        email: existingUserData.email,
        name: biodata.name || existingUserData.name,
        location: biodata.location || existingUserData.location,
        profilePicture: biodata.profilePicture || existingUserData.profilePicture,
    };

    await userRef.update(updatedData);

    const updatedUser = await userRef.get();
    return updatedUser.data();
};

const updateUserOtpStatus = async (userId, isVerified) => {
    const userRef = userCollection.doc(userId);
    await userRef.update({ is_otp_verified: isVerified });
};

const findUserByEmail = async (email) => {
    const querySnapshot = await userCollection.where('email', '==', email).get();
    if (querySnapshot.empty) {
        return null;
    }
    const userDoc = querySnapshot.docs[0];
    return { user_id: userDoc.id, ...userDoc.data() };
};

module.exports = {
    findUserById,
    updateUserData,
    updateUserOtpStatus,
    findUserByEmail
};
