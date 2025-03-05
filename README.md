<p align="center">
  <img src="https://storage.googleapis.com/seroean-buckets/logo/Seroean.png" alt="SerabutInn logo" height="180" />
</p>

# WEB Server API Seroean
> PROXO-CORIS (Project Extraordinary) International IT Competition 2025 | Powered by CORIS - Mobile Apps Category

### Development Backend Endpoint
- **Seroean**: **https://seroean-652043579010.asia-southeast2.run.app**

# API Documentation

### User Register
- **URL**: `/register`
- **Method**: `POST`
- **Request Body**:
  - `name` as `string` -`Nama`
  - `email` as `string` - `Email`
  - `password` as `string` - `Password`
  - `location` as `string` - `Lokasi`
- **Response**:

```json
{
    "status": 201,
    "message": "Pengguna berhasil didaftarkan.",
    "error": false
}
```
### User Login

- **URL**: `/login`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` - `Email`
  - `password` as `string` - `Password`

```json
{
    "status": 200,
    "message": "Login berhasil.",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTVlNjA3YTAzLTIzNjEtNGIyMC1iZjMwLWQxOGM3ZDMwZDIyMCIsImlhdCI6MTc0MTExOTI4MiwiZXhwIjoxNzQzNzExMjgyfQ.ywW5qt8oEzTgnP67aKi9C98l5g1ELVJnoKjplFW5tcg",
        "user_id": "user-5e607a03-2361-4b20-bf30-d18c7d30d220",
        "name": "Bambank",
        "email": "bambank@gmail.com",
        "location": "Bangka Belitung",
        "createdAt": "2025-03-05 03:14:37"
    },
    "error": false
}
```
### Reset Password

- **URL**: `/auth/request-reset-password`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` - `Email`

```json
{
    "status": 200,
    "message": "Token reset password telah dikirim ke email.",
    "error": false
}
```
### OTP Request

- **URL**: `/otp/request`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` - `Email`

```json
{
    "status": 200,
    "message": "Token reset password telah dikirim ke email.",
    "error": false
}
```
### OTP Verify

- **URL**: `/otp/verify`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` - `Email`
  - `otp` as `string` - `Email`

```json
{
    "status": 200,
    "message": "Verifikasi OTP Berhasil.",
    "error": false
}
```