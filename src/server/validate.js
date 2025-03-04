const jwt = require('jsonwebtoken');
const Boom = require('@hapi/boom');

const validate = async (artifacts, request, h) => {
    try {
        console.log(artifacts);

        if (!process.env.JWT_SECRET) {
            console.error("JWT_SECRET tidak ditemukan dalam environment variables.");
            return { isValid: false, credentials: null };
        }

        const decoded = artifacts.decoded.payload;

        if (!decoded) {
            console.warn("Payload token tidak ditemukan.");
            return { isValid: false, credentials: null };
        }

        if (!decoded.userId) {
            console.warn("User ID tidak ditemukan dalam token.");
            return { isValid: false, credentials: null };
        }

        return { 
            isValid: true, 
            credentials: {
                userId: decoded.userId
            } 
        };

    } catch (error) {
        console.error(error);
        return { isValid: false, credentials: null };
    }
};

module.exports = validate;
