const nodemailer = require('nodemailer');

const transporter = nodemailer.createTransport({
    host: "smtp.gmail.com",
    port: 587,
    secure: false,
    auth: {
        user: process.env.EMAIL_USER,
        pass: process.env.EMAIL_PASS
    }
});

const sendResetPasswordEmail = async (email, token) => {
    const resetLink = `https://seroean-652043579010.asia-southeast2.run.app/auth/reset-password?token=${token}`;
    const mailOptions = {
        from: `"noreply.seroean" <${process.env.EMAIL_USER}>`,
        to: email,
        subject: '[Seroean] Permintaan Perubahan Kata Sandi',
        text: ``,
        html: `
            <!DOCTYPE html>
            <html lang="id">
            
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Reset Password</title>
                <style>
                    body {
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background-color: #f4f4f4;
                        margin: 0;
                        padding: 0;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        height: 100vh;
                    }
            
                    .container {
                        max-width: 600px;
                        background-color: #ffffff;
                        border-radius: 8px;
                        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                        padding: 30px;
                    }
            
                    h2 {
                        color: #3498db;
                        margin-bottom: 20px;
                    }
            
                    p {
                        color: #555;
                        margin-bottom: 20px;
                        line-height: 1.5;
                        text-align: left;
                    }
            
                    .logo {
                        max-width: 300px;
                        height: auto;
                        margin-bottom: 20px;
                    }
            
                    .btn {
                        display: inline-block;
                        padding: 10px 20px;
                        background-color: #3498db;
                        color: #ffffff;
                        text-decoration: none;
                        border-radius: 5px;
                        margin-top: 20px;
                        margin-bottom: 20px;
                        transition: background-color 0.3s ease;
                    }
            
                    .btn:hover {
                        background-color: #2980b9;
                    }
            
                    .reset-info {
                        color: #999;
                        font-size: 0.8em;
                        text-align: left;
                        margin-top: 10px;
                    }
            
                    .closing-words {
                        color: #555;
                        font-size: 1em;
                        text-align: left;
                        margin-top: 20px;
                    }
                </style>
            </head>
            
            <body>
                <div class="container">
                    <img src="https://storage.googleapis.com/seroean-buckets/forgot-pass.png" class="logo">
                    <h2>Hi Seroean User,</h2>
                    <p>Kami mendapatkan permintaan untuk mereset kata sandi akun Seroean Anda. Jangan khawatir, kami di sini untuk membantu!</p>
                    <p>Untuk mereset kata sandi Anda, cukup klik tombol di bawah ini.</p>
                    <a href="${resetLink}" class="btn">Reset Kata Sandi</a>
                    <p>Jika tombol di atas tidak berfungsi, Anda juga dapat menyalin dan menempelkan tautan berikut ke browser Anda:</p>
                    <p class="reset-info"><a href="${resetLink}">${resetLink}</a></p>
                    <p class="closing-words">Jika Anda tidak melakukan permintaan ini, anggap saja email ini sebagai hal yang biasa dan aman.</p>
                    <p class="closing-words">Jangan ragu untuk menghubungi tim dukungan kami jika Anda memiliki pertanyaan atau mengalami masalah.</p>
                    <p class="closing-words">Terima kasih atas kepercayaan Anda!</p>
                    <p class="closing-words"><strong>Salam,</strong><br>Tim Seroean</p>
                </div>
            </body>
            </html>
        `
    };
    
    try {
        await transporter.sendMail(mailOptions);
        console.log("Berhasil Mengirim Email.");
    } catch (error) {
        console.error(error);
        throw new Error("Gagal mengirim email reset password.");
    }
};

const sendOTPEmail = async (email, otp) => {
    const mailOptions = {
        from: `"noreply.seroean" <${process.env.EMAIL_USER}>`,
        to: email,
        subject: 'Kode OTP Verifikasi Anda',
        text: `Kode OTP Anda adalah: ${otp}. Berlaku selama 5 menit.`,
        html: `  <!DOCTYPE html>
        <html lang="id">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Kode OTP</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 0;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                }
        
                .container {
                    max-width: 600px;
                    background-color: #ffffff;
                    border-radius: 8px;
                    box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
                    text-align: left;
                    padding: 30px;
                }
        
                .header {
                    text-align: center;
                    margin-bottom: 20px;
                }
        
                .header img {
                    max-width: 100%;
                    height: auto;
                }
        
                h2 {
                    color: #3498db;
                    margin-bottom: 10px;
                }
        
                p {
                    color: #555;
                    margin-bottom: 20px;
                    line-height: 1.5;
                }
        
                h1 {
                    color: #e74c3c;
                    font-size: 2em;
                    margin-top: 10px;
                    margin-bottom: 20px;
                }
        
                .btn {
                    display: inline-block;
                    padding: 10px 20px;
                    background-color: #3498db;
                    color: #ffffff;
                    text-decoration: none;
                    border-radius: 5px;
                    transition: background-color 0.3s ease;
                }
        
                .btn:hover {
                    background-color: #2980b9;
                }
        
                .expiry-info {
                    color: #999;
                    font-size: 0.8em;
                }
            </style>
        </head>
        
        <body>
            <div class="container">
                <div class="header">
                    <img src="https://i.imgur.com/AlSUkim.png" alt="Seroean Ilustrasi">
                </div>
                <h2>Hi Seroean User,</h2>
                <p>Terima kasih telah menggunakan layanan Seroean. Berikut adalah kode OTP untuk melakukan verifikasi email pada saat proses registrasi menggunakan email</p>
                <h1>${otp}</h1>
                <p class="expiry-info">Kode OTP ini berlaku selama 5 menit ke depan. Jika Anda tidak melakukan permintaan ini, Anda dapat mengabaikan email ini dengan aman. Jika ada pertanyaan atau masalah, jangan ragu untuk menghubungi tim dukungan kami. Terima kasih atas kepercayaan Anda,</p>
        <p><strong>Salam,</strong><br>Tim Seroean</p>
            </div>
        </body>
        </html>`
    };

    try {
        await transporter.sendMail(mailOptions);
        console.log("Berhasil Mengirim OTP.");
    } catch (error) {
        console.error(error);
        throw new Error("Gagal Mengirim OTP.");
    }
};

module.exports = { 
    sendResetPasswordEmail,
    sendOTPEmail
 };
