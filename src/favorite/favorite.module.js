const firestore = require('../server/firebase');
const favoriteCollection = firestore.collection('favorite');

const storeFavorite = async (favoriteData) => {
    const favoriteRef = favoriteCollection.doc(favoriteData.favorite_id);
    await favoriteRef.set(favoriteData);
};

const isFavoriteExist = async (user_id, place_id) => {
    if (!user_id || !place_id) {
        return false;
    }

    const snapshot = await favoriteCollection
        .where('user_id', '==', user_id)
        .where('place_id', '==', place_id)
        .get();

    return !snapshot.empty;
};

const isFavoriteExistById = async (favorite_id) => {
    if (!favorite_id) {
        return false;
    }

    const snapshot = await favoriteCollection
        .where('favorite_id', '==', favorite_id)
        .get();

    return !snapshot.empty;
};

const deleteFavorite = async (favorite_id) => {
    if (!favorite_id) {
        throw new Error("favorite_id harus diisi.");
    }

    const snapshot = await favoriteCollection
        .where('favorite_id', '==', favorite_id)
        .get();

    if (!snapshot.empty) {
        const doc = snapshot.docs[0];
        await favoriteCollection.doc(doc.id).delete();
    } else {
        throw new Error("Favorit ini tidak ditemukan.");
    }
};

const getFavoritesByUser = async (user_id) => {
    const snapshot = await favoriteCollection.where('user_id', '==', user_id).get();
    if (snapshot.empty) return [];

    return snapshot.docs.map(doc => ({
        favorite_id: doc.id,
        ...doc.data()
    }));
};

module.exports = { storeFavorite, deleteFavorite, getFavoritesByUser, isFavoriteExist, isFavoriteExistById };
