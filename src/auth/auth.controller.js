const authService = require('../auth/auth.service');
const Boom = require('@hapi/boom');

const requestResetPassword = async (request, h) => {
    try {
        const { email } = request.payload;
        await authService.handleRequestResetPassword(email);
        return h.response({
            status: 200,
            message: 'Token reset password telah dikirim ke email.',
            error: false
        }).code(200);
    } catch (error) {
        return h.response({
            status: error.output?.statusCode || 400,
            message: error.message,
            error: true
        }).code(error.output?.statusCode || 400);
    }
};

const resetPasswordForm = async (request, h) => {
    const { token } = request.query;
    if (!token) {
        return h.response("Token tidak valid!").code(400);
    }

    return `
        <!DOCTYPE html>
        <html lang="id">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Reset Password</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background-color: #f1f1f1;
                    margin: 0;
                    padding: 0;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    height: 100vh;
                }
                .container {
                    background-color: white;
                    padding: 3rem;
                    border-radius: 12px;
                    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    max-width: 420px;
                    transition: all 0.3s ease-in-out;
                }
                .container:hover {
                    box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
                }
                h2 {
                    color: #333;
                    text-align: center;
                    margin-bottom: 1.5rem;
                    font-size: 1.8rem;
                    letter-spacing: 0.5px;
                }
                label {
                    font-size: 1.1rem;
                    margin-bottom: 8px;
                    color: #555;
                    display: block;
                }
                input[type="password"] {
                    width: 100%;
                    padding: 12px;
                    margin: 8px 0 16px;
                    border: 1px solid #ddd;
                    border-radius: 8px;
                    font-size: 1.1rem;
                    outline: none;
                    transition: border-color 0.3s ease;
                }
                input[type="password"]:focus {
                    border-color: #007BFF;
                }
                button {
                    width: 50%;
                    padding: 12px;
                    background-color: #007BFF;
                    color: white;
                    border: none;
                    border-radius: 8px;
                    font-size: 1.2rem;
                    cursor: pointer;
                    transition: background-color 0.3s ease;
                    display: block;
                    margin: 0 auto;
                }
                button:hover {
                    background-color: #0056b3;
                }
                .message {
                    color: #ff4d4d;
                    font-size: 1rem;
                    margin-bottom: 16px;
                    text-align: center;
                }
                .footer {
                    text-align: center;
                    font-size: 0.9rem;
                    margin-top: 20px;
                    color: #777;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Reset Password</h2>
                <form action="/auth/reset-password" method="POST">
                    <input type="hidden" name="token" value="${token}" />
                    <label for="newPassword">Password Baru</label>
                    <input type="password" id="newPassword" name="newPassword" required placeholder="Masukkan password baru" />
                    <button type="submit">Reset Password</button>
                </form>
            </div>
        </body>
        </html>
    `;
};


const processResetPassword = async (request, h) => {
    try {
        const { token, newPassword } = request.payload;
        await authService.handleResetPassword(token, newPassword);
        return h.response("Kata sandi berhasil diperbarui! Silakan login kembali.");
    } catch (error) {
        return h.response(error.message).code(400);
    }
};

module.exports = { requestResetPassword, resetPasswordForm, processResetPassword };
