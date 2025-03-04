require('dotenv').config();
const Hapi = require('@hapi/hapi');
const Jwt = require('@hapi/jwt');
const validate = require('./validate');
const Boom = require('@hapi/boom');
const { getCurrentTime } = require('../utils/time');
const registerRoutes = require('../routes/register.routes');
const loginRoutes = require('../routes/login.routes');
const authRoutes = require('../routes/auth.routes');
const otpRoutes = require('../routes/otp.routes');
const wisataRoutes = require('../routes/wisata.routes');
const kulinerRoutes = require('../routes/kuliner.routes');
const budayaRoutes = require('../routes/budaya.routes');
const sejarahRoutes = require('../routes/sejarah.routes');
const pertanyaanRoutes = require('../routes/pertanyaan.routes');
const notifikasiRoutes = require('../routes/notifikasi.routes');
const ulasanRoutes = require('../routes/ulasan.routes');
const favoriteRoutes = require('../routes/favorite.routes');
const userRoutes = require('../routes/user.routes');

(async () => {
    const now = getCurrentTime();
    const server = Hapi.server({
        port: process.env.PORT,
        host: '0.0.0.0',
        routes: {
            cors: {
                origin: ['*'],
            },
        },
    });

    server.ext('onRequest', (request, h) => {
        request.url.pathname = request.url.pathname.replace(/\/+/g, '/');
        return h.continue;
    });

    await server.register(Jwt);


    server.auth.strategy('jwt', 'jwt', {
        keys: process.env.JWT_SECRET, 
        verify:{
            aud: false, 
            iss: false,
            sub: false
        },
        validate
    });


    server.auth.default('jwt');

    server.route(registerRoutes);
    server.route(loginRoutes);
    server.route(authRoutes);
    server.route(otpRoutes);
    server.route(wisataRoutes);
    server.route(kulinerRoutes);
    server.route(budayaRoutes);
    server.route(sejarahRoutes);
    server.route(pertanyaanRoutes);
    server.route(notifikasiRoutes);
    server.route(ulasanRoutes);
    server.route(favoriteRoutes);
    server.route(userRoutes);

    server.route({
        method: '*',
        path: '/{any*}',
        options: {
            auth: false
        },
        handler: (request, h) => {
            return 'Hello World! This is Web Server Seroean.';
        }
    });

    await server.start();
    console.log(`Server Time | ${now.toString()}`);
    console.log(`Server start on ${server.info.uri}`);

process.on('unhandledRejection', (err) => {
    console.log(err);
    process.exit(1);
});
})();