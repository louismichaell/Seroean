const firestore = require('../server/firebase');
const userCollection = firestore.collection('users');

const addUser = async (userData) => {
    const userRef = userCollection.doc(userData.user_id);
    await userRef.set(userData);
    return { id: userRef.id, ...userData };
};

const findUserByEmail = async (email) => {
    const snapshot = await userCollection.where('email', '==', email).get();
    return snapshot.empty ? null : { id: snapshot.docs[0].id, ...snapshot.docs[0].data() };
};

module.exports = {
    addUser,
    findUserByEmail
};
