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
    "message": "Berhasil mengirim kode OTP.",
    "error": false
}
```
### OTP Verify

- **URL**: `/otp/verify`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` - `Email`
  - `otp` as `string` - `Password`

```json
{
    "status": 200,
    "message": "Verifikasi OTP Berhasil.",
    "error": false
}
```
### Add Wisata
- **URL**: `/wisata/add`
- **Auth required**: `YES`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Method**: `POST`
- **Request Body**:
  - `nama` as `string` -`Nama Wisata`
  - `lokasi` as `string` - `Lokasi`
  - `provinsi` as `string` - `Provinsi`
  - `deskripsi` as `string` - `Deskripsi`
  - `rating` as `string` - `Rating`
  - `foto` as `file` - `File Foto Wisata Utama`
  - `image` as `file` - `File Foto Wisata`
  - `image2` as `file` - `File Foto Wisata 2`
  - `image3` as `file` - `File Foto Wisata 3`

- **Response**:

```json
{
    "status": 201,
    "message": "Wisata berhasil ditambahkan.",
    "error": false
}
```
### Get All Wisata

- **URL**: `/wisata`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data wisata berhasil diambil.",
    "data": [
        {
            "wisata_id": "wisata-de968d4f-8c61-468e-af3a-e96a4386f65f",
            "tipe": "wisata",
            "nama": "Alun-Alun Taman Merdeka",
            "lokasi": "33127, Batin Tikal, Kec. Taman Sari, Kota Pangkal Pinang, Kepulauan Bangka Belitung 33684.",
            "provinsi": "Bangka Belitung",
            "deskripsi": "Alun-Alun Taman Merdeka, yang terletak di pusat Kota Pangkalpinang, merupakan titik nol kilometer Pulau Bangka. Pada masa pemerintahan Belanda, alun-alun ini dikenal sebagai Alun-Alun Selatan. Setelah kemerdekaan Indonesia, namanya diubah menjadi Lapangan Merdeka, dan sejak tahun 2011, dikenal kembali sebagai Alun-Alun Taman Merdeka. Dikelilingi oleh empat jalan utama—Jalan Jenderal Sudirman, Jalan Merdeka, Jalan Rustam Effendi, dan Jalan Muhidin—alun-alun ini menjadi pusat berbagai aktivitas dan acara di kota. Fasilitas yang tersedia meliputi lapangan tenis, area bermain anak-anak, dan panggung hiburan rakyat. Pada sore hingga malam hari, area ini ramai dengan penjual makanan khas yang menawarkan berbagai kuliner lokal.\\n\\n \nAlun-Alun Taman Merdeka juga menjadi tempat favorit bagi keluarga untuk rekreasi. Selain area bermain yang melatih ketangkasan dan motorik anak, tersedia pula fasilitas seperti Wi-Fi gratis. Sebagai pusat keramaian, alun-alun ini selalu ramai dikunjungi oleh masyarakat dari berbagai kalangan, baik pagi, sore, maupun malam hari. Keberadaannya yang strategis menjadikannya ikon penting bagi Kota Pangkalpinang dan destinasi wajib bagi wisatawan yang berkunjung. \n",
            "rating": "4.4",
            "foto": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 08:29:53-Picture2.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 08:29:53-Picture1.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 08:29:53-Picture3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 08:29:54-Picture4.jpg"
        }
    ],
    "error": false
}
```
### Get Detail Wisata By Id

- **URL**: `/wisata/{wisata_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data wisata berhasil ditemukan.",
    "data": {
        "wisata_id": "wisata-3039324e-1dee-4e92-914b-fe171e59b8b6",
        "tipe": "wisata",
        "nama": "Pantai Tikus Emas",
        "lokasi": "Parit Padang, Sungai Liat, Kabupaten Bangka, Kepulauan Bangka Belitung",
        "provinsi": " Kepulauan Bangka Belitung",
        "deskripsi": "Pantai Tikus Emas terletak di Kecamatan Sungailiat, Kabupaten Bangka, dan dapat dicapai dalam waktu sekitar 45 menit dari Bandara Depati Amir melalui Jalan Lintas Timur. Pantai ini menawarkan pemandangan pasir putih yang luas dengan deretan pohon pinus yang rindang, menciptakan suasana teduh bagi pengunjung. \\n\\nIkon utama pantai ini adalah patung berbentuk tikus berwarna emas, yang menjadi spot foto favorit bagi wisatawan. Nama \"Tikus Emas\" konon berasal dari banyaknya \"jalan tikus\" atau jalur pintas yang digunakan oleh penambang ilegal di sekitar area tersebut. \\n\\nPantai Tikus Emas menyediakan berbagai fasilitas untuk kenyamanan pengunjung, termasuk mushola, toilet umum, restoran, area penyewaan pemanggang, tenda, dan mini zoo. Bagi yang mencari aktivitas, tersedia berbagai pilihan seperti paintball, sepeda gantung, berkuda, delman, banana boat, jet ski, offroad, dan fasilitas outbound seperti flying fox dan jaring laba-laba. Area di bawah pohon pinus sering dimanfaatkan sebagai tempat berkemah, terutama saat akhir pekan, menarik minat kaum muda dan keluarga untuk menikmati suasana alam yang asri. \\n\\n",
        "rating": "4.4",
        "foto": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 16:48:14-tikusemas1.jpg",
        "image": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 16:48:14-tikusemas2.jpg",
        "image2": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 16:48:14-tikusemas3.jpg",
        "image3": "https://storage.googleapis.com/seroean-buckets/wisata-2025-03-09 16:48:14-tikusemas4.jpg"
    },
    "error": false
}
```
### Add Kuliner
- **URL**: `/kuliner/add`
- **Auth required**: `YES`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Method**: `POST`
- **Request Body**:
  - `nama` as `string` -`Nama Kuliner`
  - `lokasi` as `string` - `Lokasi`
  - `provinsi` as `string` - `Provinsi`
  - `deskripsi` as `string` - `Deskripsi`
  - `rating` as `string` - `Rating`
  - `opsi` as `string` - `Opsi`
  - `telepon` as `string` - `Nomor Telepon`
  - `foto` as `file` - `File Foto Kuliner Utama`
  - `image` as `file` - `File Foto Kuliner`
  - `image2` as `file` - `File Foto Kuliner 2`
  - `image3` as `file` - `File Foto Kuliner 3`

- **Response**:

```json
{
    "status": 201,
    "message": "Kuliner berhasil ditambahkan.",
    "error": false
}
```
### Get All Kuliner

- **URL**: `/kuliner`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data kuliner berhasil diambil.",
    "data": [
        {
            "kuliner_id": "kuliner-ff93e017-b754-40d2-82df-fd5ecf6dd32a",
            "tipe": "kuliner",
            "nama": "Lempah Muara",
            "lokasi": "Jl. Alexander Raya Simpang empat, Bacang, Kec. Bukitintan, Kota Pangkal Pinang, Kepulauan Bangka Belitung 33684",
            "provinsi": "Kepulauan Bangka Belitung",
            "telepon": "081272273619",
            "opsi": "- Menyediakan tempat duduk di area terbuka\\n\\n- Ada menu anak",
            "deskripsi": "Restoran Seafood Mr. Adox adalah salah satu destinasi kuliner terkemuka di Kota Pangkalpinang, Kepulauan Bangka Belitung. Terletak di Jalan Alexander Raya, Simpang Empat, Bacang, Bukit Intan, restoran ini beroperasi setiap hari mulai pukul 10.00 hingga 22.00. \\n\\n\nDikenal sebagai salah satu restoran seafood terbesar di Bangka Belitung, Mr. Adox menjadi tempat favorit bagi berbagai kalangan, termasuk influencer dan artis. Menu andalannya mencakup berbagai hidangan laut segar, seperti kepiting saus Padang, ikan bakar, cumi goreng tepung, dan udang saus tiram. Selain itu, restoran ini juga menawarkan fasilitas untuk berbagai acara, seperti pertemuan, pernikahan, dan gathering, dengan kapasitas yang memadai. \\n\\n\nLokasinya yang strategis, sekitar 3,5 km dari Bandar Udara Depati Amir ke arah Sungailiat, memudahkan akses bagi pengunjung. Dengan reputasi yang baik dan pelayanan yang ramah, Restoran Seafood Mr. Adox menjadi pilihan utama bagi pecinta kuliner laut yang berkunjung ke Pangkalpinang.\n",
            "rating": 4.4,
            "foto": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 16:57:07-Picture13.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 16:57:08-Picture14.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 16:57:08-Picture15.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 16:57:08-Picture16.jpg"
        }
    ],
    "error": false
}
```
### Get Detail Kuliner By Id

- **URL**: `/kuliner/{kuliner_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data kuliner berhasil ditemukan.",
    "data": {
        "kuliner_id": "kuliner-3e6967d3-52d4-46f5-82a8-b06a1b5ef940",
        "tipe": "kuliner",
        "nama": "Warung Cak Abdul",
        "lokasi": "Jl. S. Parman No.10, Sungailiat, Sungai Liat, Kabupaten Bangka, Kepulauan Bangka Belitung 33211",
        "provinsi": "Kepulauan Bangka Belitung",
        "telepon": 81297224467,
        "opsi": "Hanya tunai",
        "deskripsi": "Rumah Makan Cak Abdul adalah sebuah tempat makan yang terletak di Sungailiat, tepatnya di perempatan lampu merah KFC, berhadapan langsung dengan KFC. Tempat ini dikenal dengan menu andalannya, yaitu bakso dan soto ayam. Harga yang ditawarkan cukup terjangkau, dengan berbagai pilihan menu yang dapat dinikmati oleh berbagai kalangan.\\n\\nSuasana di Rumah Makan Cak Abdul sederhana namun nyaman, cocok untuk tempat bersantap bersama keluarga atau teman. Lokasinya yang strategis memudahkan pengunjung untuk menemukannya, terutama bagi mereka yang berada di sekitar area perempatan lampu merah KFC Sungailiat.Selain menu utama seperti bakso dan soto ayam, Rumah Makan Cak Abdul juga menyediakan berbagai pilihan minuman untuk menemani santap Anda. Dengan pelayanan yang ramah dan cepat, tempat ini menjadi salah satu pilihan favorit bagi masyarakat lokal maupun wisatawan yang berkunjung ke Sungailiat.\\n\\n",
        "rating": 4.8,
        "foto": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 17:43:54-Warung Cak Abdul2.jpg",
        "image": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 17:43:54-Warung Cak Abdul1.jpg",
        "image2": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 17:43:54-Warung Cak Abdul3.jpg",
        "image3": "https://storage.googleapis.com/seroean-buckets/kuliner-2025-03-09 17:43:54-Warung Cak Abdul4.jpg"
    },
    "error": false
}
```
### Add Budaya
- **URL**: `/budaya/add`
- **Auth required**: `YES`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Method**: `POST`
- **Request Body**:
  - `nama` as `string` -`Nama Budaya`
  - `deskripsi` as `string` - `Deskripsi`
  - `foto` as `file` - `File Foto Budaya Utama`
  - `image` as `file` - `File Foto Budaya`
  - `image2` as `file` - `File Foto Budaya 2`
  - `image3` as `file` - `File Foto Budaya 3`

- **Response**:

```json
{
    "status": 201,
    "message": "Budaya berhasil ditambahkan.",
    "error": false
}
```
### Get All Budaya

- **URL**: `/budaya`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data budaya berhasil diambil.",
    "data": [
        {
            "budaya_id": "budaya-3267b26b-e427-4c6e-a2bf-010162c14a11",
            "tipe": "budaya",
            "nama": "Perang Ketupat",
            "deskripsi": "Salah satu tradisi dari Babel yang tak kalah menarik dan menarik adalah perang ketupat atau Ruah Tempilang. Tradisi ini diselenggarakan setiap masuk Tahun Baru Islam atau 1 Muharram. Penduduk setempat akan berbondong-bondong menuju ke pantai.\\n\\nSaat meriam dinyalakan, para penduduk saling melempar ketupat ke setiap orang yang mereka temui. Kamu dapat melihat tradisi ini di desa-desa sekitar Pantai Tempilang, Bangka Barat.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:03-Perang Ketupat4.jpg"
        },
        {
            "budaya_id": "budaya-33a43d56-b47b-4450-b6de-8803b47b57bc",
            "tipe": "budaya",
            "nama": "Nujuh Jerami",
            "deskripsi": "Nujuh Jerami merupakan sebuah upacara peringatan yang diadakan oleh penduduk di daerah Lom, khususnya di Dusun Air Abik dan Dusun Pejam di Provinsi Kepulauan Bangka Belitung. Upacara ini biasanya dilakukan oleh masyarakat setempat pada bulan purnama, mengikuti kalender Cina atau setiap bulan April dalam kalender masehi.\\n\\nTradisi adat Bangka ini umumnya dilakukan oleh masyarakat yang tinggal di pemukiman di luar atau pedalaman hutan, sebagai ungkapan rasa syukur atas hasil panen. Berdasarkan cerita dari tokoh adat Bangka Belitung, tradisi ini telah dilakukan sejak zaman nenek moyang.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:12:09-Nujuh Jerami1.jpeg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:12:09-Nujuh Jerami2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:12:09-Nujuh Jerami3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:12:09-Nujuh Jerami4.jpg"
        },
        {
            "budaya_id": "budaya-65ec9332-a2ad-4199-809b-5e2ddc03dc1a",
            "tipe": "budaya",
            "nama": "Buang Jung",
            "deskripsi": "Upacara Buang Jung adalah tradisi tahunan yang diselenggarakan oleh Suku Sekak. Masyarakat setempat percaya, bahwa upacara Buang Jung ini dapat memberikan keselamatan kepada para nelayan saat mencari hasil tangkapan di laut.\\n\\nDalam rangkaian upacara ini, hasil bumi yang dimiliki oleh para nelayan, yang akan ditempatkan di atas perahu kecil, akan dihanyutkan ke laut sebagai ungkapan rasa syukur dari masyarakat setempat.Selama satu minggu sekitar bulan Juli hingga Agustus, masyarakat setempat akan dilarang untuk menangkap ikan, menebang dan membakar pohon, mengumpulkan kerang, serta melakukan aktivitas wisata seperti snorkeling dan diving. Jika kamu ingin menyaksikan upacara ini, maka kamu bisa mengunjungi Desa Kumbung yang terletak di Kecamatan Lempar Pongok, Kabupaten Bangka Selatan.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:16:20-Buang Jung1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:16:20-Buang Jung2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:16:20-Buang Jung3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:16:20-Buang Jung4.jpg"
        },
        {
            "budaya_id": "budaya-7a15d44e-1a82-4a3e-96a7-6e0911e4f98b",
            "tipe": "budaya",
            "nama": "Rebo Kasan",
            "deskripsi": "Rebo Kasan merupakan sebuah ritual yang berasal dari masyarakat Melayu pesisir pantai di Kabupaten Bangka. Upacara Rebo Kasan ini adalah hasil akulturasi dari nilai-nilai religius, legenda, dan mitos nenek moyang masyarakat Bangka.\\n\\nMasyarakat setempat sendiri meyakini bahwa pada akhir hari Rabu bulan Shafar, Tuhan akan menurunkan bencana dari terbit fajar hingga matahari terbenam, baik itu bencana besar maupun bencana kecil. Tujuan dari upacara adat Bangka Belitung ini adalah untuk menolak bala atau musibah, sekaligus sebagai harapan agar hasil tangkapan para nelayan melimpah.\\n\\nDalam pelaksanaannya, masyarakat setempat akan melakukan doa bersama dan melanjutkan upacara dengan pencabutan ketupat lepas sebagai tanda sudah dicabutnya bencana yang mungkin akan menimpa mereka. Upacara Rebo Kasan ini akan diakhiri dengan makan bersama di dalam masjid menggunakan nampan yang dibawa oleh masing-masing masyarakat setempat.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:23:18-Rebo Kasan1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:23:19-Rebo Kasan2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:23:19-Rebo Kasan3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:23:19-Rebo Kasan4.jpeg"
        },
        {
            "budaya_id": "budaya-9f089f52-18d6-4165-b839-a127f33431a6",
            "tipe": "budaya",
            "nama": "Cheng Beng",
            "deskripsi": "Salah satu tradisi yang mampu menarik banyak sekali wisatawan dari luar negeri ialah tradisi Cheng Beng. Tradisi ini juga dikenal dengan istilah sembahyang kubur. Tradisi yang diselenggarakan setiap 5 April ini mampu mendatangkan wisatawan keturunan Tiongha dari dalam maupun luar negeri.\\n\\nBiasanya, peziarah melaksanakan ritual sembahyang Cheng Beng di Kota Pangkalpinang yang terdapat makam tertua dan terbesar di Asia tenggara. Mereka datang ke makam tersebut dengan membawa sesaji seperti Sam-sang, Sam kuo, dan Chai coi.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:02:07-Cheng Beng4.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:02:08-Cheng Beng3.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:02:08-Cheng Beng2.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:02:08-Cheng Beng1.jpg"
        },
        {
            "budaya_id": "budaya-e7416b2f-015b-4373-ad0d-d99ed5d65026",
            "tipe": "budaya",
            "nama": "Nganggung",
            "deskripsi": "Nganggung adalah tradisi yang selalu hadir di kalangan masyarakat Kota Pangkalpinang, tradisi Nganggung adalah tradisi membawa makanan lengkap dari masing masing rumah penduduk ke tempat pertemuan besar. Nganggung juga sering disebut Sepintu Sedulang yang memiliki arti satu rumah (satu pintu) membawa sedulang (satu dulang), dulang adalah wadah seng atau kuningan yang digunakan untuk mengisi makanan lengkap seperti lauk pauk, kue dan juga buah buahan dan ditutup dengan tudung saji yang biasanya berwarna merah dan bermotif.\\n\\nBiasanya tradisi Nganggung ini dilakukan pada upacara keagamaan bagi umat Muslim seperti Maulid Nabi Muhammad SAW, Nisfu Sya’ban, Muharram serta Idul Fitri dan Idul Adha dan juga biasanya dilakukan di Masjid, Surau, atau di Langgar dengan rangkaian acara yang dimulai dengan pembacaan do’a dan ceramah agama dengan tema yang sesuai dengan pelaksanaan nganggung tersebut.\\n\\nTradisi nganggung merupakan wujud semangat gotong-royong antarwarga. Tradisi ini bertujuan untuk mempererat tali silaturahmi sesama warga, supaya tercipta kerukunan dan kedamaian.\\n\\nTradisi nganggung merupakan wujud semangat gotong-royong antarwarga. Tradisi ini bertujuan untuk mempererat tali silaturahmi sesama warga, supaya tercipta kerukunan dan kedamaian\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 19:36:06-Nganggung1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 19:36:06-Nganggung2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 19:36:06-Nganggung3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 19:36:06-Nganggung4.jpg"
        },
        {
            "budaya_id": "budaya-ef4a5c98-a900-4dcb-80e0-0a2aa816a71e",
            "tipe": "budaya",
            "nama": "Peh Cun",
            "deskripsi": "Tradisi Peh Cun ini diadakan untuk memperingati Dinasti Couw 340 sebelum masehi. Tradisi ini biasanya diselenggarakan di sepanjang pantai di Kepulauan Bangka Belitung. Terdapat 2 ritual dalam tradisi ini, yaitu membuang Nyuk Cun secara simbolis ke laut dan mandi air laut di terik matahari. \\n\\nTradisi ini mampu menjadi salah satu andalan pemerintah setempat untuk menarik para wisatawan, baik dari dalam negeri maupun luar negeri. Tradisi Peh Cun ini diperingati pada tanggal 5 bulan 5 pada kelender Tionghoa.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:06:00-Peh Cun2.jpeg",
            "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:06:00-Peh Cun1.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:06:00-Peh Cun4.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:06:00-Peh Cun3.jpeg"
        }
    ],
    "error": false
}
```
### Get Detail Budaya By Id

- **URL**: `/budaya/{budaya_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data budaya berhasil ditemukan.",
    "data": {
        "budaya_id": "budaya-3267b26b-e427-4c6e-a2bf-010162c14a11",
        "tipe": "budaya",
        "nama": "Perang Ketupat",
        "deskripsi": "Salah satu tradisi dari Babel yang tak kalah menarik dan menarik adalah perang ketupat atau Ruah Tempilang. Tradisi ini diselenggarakan setiap masuk Tahun Baru Islam atau 1 Muharram. Penduduk setempat akan berbondong-bondong menuju ke pantai.\\n\\nSaat meriam dinyalakan, para penduduk saling melempar ketupat ke setiap orang yang mereka temui. Kamu dapat melihat tradisi ini di desa-desa sekitar Pantai Tempilang, Bangka Barat.\\n\\n",
        "foto": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat1.jpg",
        "image": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat2.jpg",
        "image2": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:02-Perang Ketupat3.jpg",
        "image3": "https://storage.googleapis.com/seroean-buckets/budaya-2025-03-09 20:09:03-Perang Ketupat4.jpg"
    },
    "error": false
}
```
### Add Sejarah
- **URL**: `/sejarah/add`
- **Auth required**: `YES`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Method**: `POST`
- **Request Body**:
  - `nama` as `string` -`Nama Sejarah`
  - `deskripsi` as `string` - `Deskripsi`
  - `foto` as `file` - `File Foto Sejarah Utama`
  - `image` as `file` - `File Foto Sejarah`
  - `image2` as `file` - `File Foto Sejarah 2`
  - `image3` as `file` - `File Foto Sejarah 3`

- **Response**:

```json
{
    "status": 201,
    "message": "Sejarah berhasil ditambahkan.",
    "error": false
}
```
### Get All Sejarah

- **URL**: `/sejarah`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data sejarah berhasil diambil.",
    "data": [
        {
            "sejarah_id": "sejarah-06d23b72-ad4d-4ef7-bdae-cedcfafefd45",
            "tipe": "sejarah",
            "nama": "Pemakaman Kerkhof",
            "deskripsi": "Kompleks pemakaman Belanda (Kerkhof), terletak di jalan Hormen Maddati Kelurahan Melintang Kecamatan Rangkui. Disini terdapat 100 makam dengan nisan bertuliskan bahasa Indonesia, Jepang dan Belanda. Yang tertua dari tahun 1902 dan yang termuda sekitar tahun 1950-an. Kompleks pemakaman ini memiliki keunikan karena kompleks pemakaman umum orang Belanda, salah satu makam tertua adalah makam nyonya Irene Mathilda Ehrecron yang wafat tanggal 10 Maret 1928. Disini juga terdapat makam tentara Belanda korban Perang Dunia Kedua. \\n\\nSelain itu yang menarik pada pemakaman Kerkhof adalah sepuluh makam orang Jepang. Dari sepuluh makam tersebut delapan masih bisa dibaca dengan jelas, satu makam sebagian tulisannya berbahasa jepang dan sebagian lainnya berbahasa Cina sehingga belum dapat dibaca dan ada satu makam tidak terbaca lagi tulisannya. Seluruh makam orang Jepang yang dimakamkan di Kerkhof menghadap ke arah Barat Daya dan Timur Laut dan mayoritas dari mereka adalah perempuan serta umumnya berasal dari daerah selatan jepang yang tergolong miskin. Kenapa sampai ada orang Jepang yang dimakamkan di Kerkhof merupakan pertanyaan menarik. \\n\\nDalam catatan sejarah, banyak perempuan Jepang yang disebut karayukisan atau pekerja seks komersial masuk ke Hindia Belanda melalui Singapura dan menyebar ke Medan, Palembang, Batavia, Surabaya bahkan sampai kepulau Bangka. Kehadiran pelacur impor seperti karayukisan memang menjadi favorit para lelaki hidung belang yang dimasa itu disebut pria hidung putih. Di Hindia Belanda termasuk di Bangka pada tahun 1898 M, bangsa Jepang disetujui sama status hukumnya dengan orang kulit putih hal ini memberi pengaruh pada status gengsi dan mobilitas vertikal naik, dari orang Jepang yang semula sebagai warga kelas dua (bersama dengan warga keturunan Arab dan Cina serta orang Timur Asing) menjadi warga kelas satu bersama warga Eropa atau bangsa asing kulit putih dan tidak mengherankan ketika mereka meninggal dunia kuburan mereka pun disamakan dengan warga Eropa di Kerkhof.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)1.jpeg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:28-Pemakaman Belanda (Kerkhof)4.jpg"
        },
        {
            "sejarah_id": "sejarah-5206b550-6300-4e55-8021-0cc19fcf1644",
            "tipe": "sejarah",
            "nama": "Sejarah Bumi Serumpun Sebalai",
            "deskripsi": "Pelan tapi pasti, Bangka Belitung terus bersolek. Kecantikannya tak hanya dirasakan penduduk lokal. Secara nasional, termasuk dunia, juga turut merasakan pesonanya. Ini jelas kebanggaan, juga prestasi, mengingat Bangka Belitung merupakan provinsi baru, terbentuk pada tahun 2000. Provinsi Kepulauan Bangka Belitung terdiri dari dua pulau besar, yakni Pulau Bangka dan Pulau Belitung. Ada juga pulau-pulau kecil lainnya. Di zaman kerajaan, wilayah ini masuk dalam kekuasaan Sriwijaya, Majahapit, juga Mataram.Setelahnya, Bangka Belitung menjadi daerah jajahan Inggris. Pada 10 Desember 1816, dilaksanakan serah terima kepada pemerintah Belanda, berlangsung di Muntok.\\n\\nPada masa penjajahan Belanda, terjadi perlawanan, dilakukan oleh Depati Barin. Perlawanan kemudian dilanjutkan putranya, Depati Amir, hingga berakhir dengan pengasingan ke Kupang, Nusa Tenggara Timur. Selama masa penjajahan, banyak kekayaan di pulau ini dirampas. Kendati demikian, Bangka Belitung mampu bertahan, termasuk melakukan sejumlah perlawanan. Baru pada tahun 2000, Bumi Serumpun Sebalai resmi menjadi wilayah otonom.\\n\\nKetika itu, Pemerintah Republik Indonesia mengakui keberadaan Bangka Belitung sebagai provinsi, tak lagi menginduk bersama Sumatera Selatan. Penetapan ini dikukuhkan berdasarkan Undang-Undang Nomor 27 Tahun 2000.'\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:22:26-Sejarah Bumi Serumpun Sebalai1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:22:27-Sejarah Bumi Serumpun Sebalai2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:22:27-Sejarah Bumi Serumpun Sebalai3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:22:27-Sejarah Bumi Serumpun Sebalai4.jpg"
        },
        {
            "sejarah_id": "sejarah-694906b7-b386-4d9f-9f80-cca73b7749b3",
            "tipe": "sejarah",
            "nama": "Sejarah Rumah Sakit Bakti Timah",
            "deskripsi": "Pemerintah Hindia Belanda pada tahun 1920 M, masa residen Doornik. W (memerintah tahun 1918-1923 M), mulai membangun Hoofgebouw van Het Ziekenhuis van de Banka Tin Winning te Pangkalpinang (balai pengobatan utama atau rumah sakit utama bagi karyawan perusahaan Banka Tin Winning). Bangunan terletak pada sisi barat kantor pusat PT. Timah tbk. dan jalan Jendral Sudirman.\\n\\n Bentuk bangunan berdenah empat persegi panjang, berlantai dua. Bangunan berbentuk segi enam. Lantai bawah dilapisi tegel berwarna abu-abu dan lantai atas terbuat dari kayu. Atap terbuat dari seng, berbentuk atap limas, dinding dari bata, dilengkapi dengan pintu dan jendela kaca. Pada tahun 1953, seiring dinasionalisasinya perusahaan-perusahaan pertambangan Timah Belanda, BTW (Banka Tin Winning Bedrjff), GMB7. \\n\\nRumah Sakit Bakti Timah (Hoofgebouw van Het Ziekenhuis van de Banka Tin Winning te Pangkalpinang), (Gemenschaplijke Maatschappij Billiton) dan NV. Sitem (Singkep Tin Maatschappij) menjadi perusahaan milik negara, balai pengobatan utama atau rumah sakit utama bagi karyawan timah pada tahun 1969 dikelola oleh Unit Penambangan Timah Bangka (UPTB), sementara fungsinya disamping untuk pengobatan karyawan Unit Penambangan Timah Bangka (UPTB) juga melayani pengobatan bagi masyarakat pulau Bangka. Pada tanggal 2 Agustus 1976, Perusahaan Negara Tambang Timah diubah statusnya menjadi perseroan terbatas dengan nama PT. Timah tbk.\\n\\n Selanjutnya pada tahun 1990, BUMN PT. Timah mulai melakukan restrukturisasi, revitalisasi, reorganisasi pembentukan anak perusahan, optimalisasi bisnis, pengendalian cost secara ketat dan pengembangan visi serta budaya kerja secara efisien. Termasuk dalam program ini adalah pelepasan terhadap aset rumah sakit. Pengelolaan terhadap rumah sakit kemudian dilakukan secara swakelola pada tanggal 1 Februari 1993. Pada perkembangan selanjutnya sejak tanggal 1 April 1994, rumah sakit menjadi milik dan salah satu unit dari Yayasan Bakti Timah. Rumah Sakit Bakti Timah merupakan Cagar Budaya Kota Pangkalpinang (Peraturan Menteri Kebudayaan dan Pariwisata Nomor: PM.13/PW.007/MKP/2010, tanggal 8 Januari 2010) dan dilindungi Undang-undang Nomor 11 Tahun 2010 tentang cagar budaya.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:44:09-Sejarah Rumah Sakit Bakti Timah1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:44:09-Sejarah Rumah Sakit Bakti Timah2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:44:09-Sejarah Rumah Sakit Bakti Timah3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:44:09-Sejarah Rumah Sakit Bakti Timah4.jpg"
        },
        {
            "sejarah_id": "sejarah-a1c2bcbf-a4a6-42b6-982a-974fb85fc4b1",
            "tipe": "sejarah",
            "nama": "Kelenteng Kwan Tie Miaw",
            "deskripsi": "Kelenteng Kwan Tie Miaw merupakan salah satu kelenteng tertua yang ada di Pulau Bangka. Kelenteng ini terletak di Jalan Mayor Muhidin. Dulunya bernama Kelenteng Kwan Tie Bio. Kelenteng ini diperkirakan dibuat tahun 1841 M berdasarkan aksara Cina pada sebuah lonceng besi di kelenteng. Pembangunannya sendiri dilakukan secara gotong royong oleh berbagai kelompok Kongsi penambang timah yang ada di Pangkalpinang dan diresmikan pada tahun 1846. Pada kelenteng tertua di Pangkalpinang ini terdapat hiasan buah labu (Gourd) di puncak atap kelenteng dan adanya lambang Patkea (Pakua) yang melambangkan keberuntungan, rejeki atau kebahagiaan. Nama kelenteng sudah dua kali mengalami perubahan, pada masa Orde Baru kelenteng ini bernama Amal Bhakti. \\n\\nPada tahun 1986 bagian depan kelenteng terkena pelebaran jalan sehingga pekarangan depan, pintu serta tembok depan mundur beberapa meter, bagian altar Kuan Tie tetap utuh dan bagian depan dibagun menjadi 2 lantai. Pada tanggal 22 Februari 1998 terjadi kebakaran yang menghanguskan kelenteng ini kecuali pada bagian kiri bangunan. Sejak itu dilakukanlah pemugaran kembali dipimpin oleh Jamal seorang ahli dalam kelenteng dan pembuatan patung, rehabilitasi selesai seperti bentuk sekarang serta diresmikan pada tanggal 5 Agustus 1999 dengan nama Kelenteng Kwan Tie Miaw.\\n\\nKawasan kelenteng sekarang ditambah dengan lokasi Gang Singapur dan Pasar Mambo yang sedang dikondisikan sebagai salah satu objek dan daya tarik wisata Kota Pangkalpinang yaitu wisata budaya, wisata belanja dan wisata kuliner. Lokasi ini diupayakan menjadi China Town (untuk mengingatkan kepada wajah kota lama Pangkalpinang yang sangat dipengaruhi oleh rumah-rumah kelenteng Cina) dan dijadikan juga sebagai pusat upacara peringatan hari Raya Imlek, puncak hari raya Cap Go Meh, kegiatan sembahyang Rebut dan kegiatan Pot Ngin Bun. Kegiatan Pot Ngin Bun merupakan satu-satunya ritual yang ada di kelenteng Kwan Tie Miaw. Kegiatan ini dilakukan untuk menolak bala dan segala wabah penyakit yang mewabah di masyarakat seperti wabah penyakit beri-beri yang mewabah di Pulau Bangka sekitar Tahun 1850-1860.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:48:50-Kelenteng Kwan Tie Miaw.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:48:50-Kelenteng Kwan Tie Miaw2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:48:50-Kelenteng Kwan Tie Miaw3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:48:50-Kelenteng Kwan Tie Miaw4.jpg"
        },
        {
            "sejarah_id": "sejarah-d5068266-d8e0-4ada-b7f4-a0e38e3096f4",
            "tipe": "sejarah",
            "deskripsi": "Pesanggrahan Menumbing adalah tempat beberapa pemimpin Republik Indonesia diasingkan oleh Belanda setelah Agresi Militer II pada 19-20 Desember 1948. Mereka adalah Presiden Sukarno dan Wakil Presiden Mohammad Hatta, Mr. A. Gafar Pringgodigdo, Mr. Ass’at, Komodor Suryadarma, Mr. Ali Sastroamidjoyo, Mr. Mohammad Roem dan Haji Agus Salim.  Pada awalnya mereka semua akan ditempatkan di Pesanggrahan Menumbing, namun  karena alasan keamanan dan kondisi lingkungan yang  terlalu dingin khususnya bagi kesehatan Sukarno, Belanda  memindahkan Sukarno ke  Pasanggrahan Muntok atau dikenal juga sebagai Wisma Ranggam yang berjarak tempuh sekitar 16 km dari Menumbing. Bersama Sukarno, ikut serta H. Agus Salim, Mohammad Roem, dan Ali Sastroamidjojo. Tokoh-tokoh lainnya tetap berada di Menumbing.\\n\\nPesanggrahan Menumbing atau disebut juga Wisma Menumbing atau Istana Menumbing, namanya dilekatkan dengan nama bukit di mana pesanggrahan ini terletak di puncaknya, yaitu Bukit Menumbing. Bukit dengan ketinggian 445 meter dari permukaan laut ini menjulang di pinggiran kota tua Muntok yang kini menjadi ibukota Kabupaten Bangka Barat.\\n\\nSebagai nama bukit, Menumbing telah disebut dalam Suma Oriental karya Tome Pires yang ditulis pada 1512-1515, dengan sebutan “Monomby”. Menurut Mary F. Somers Heidhues dalam buku “Bangka Tin and Muntok Pepper” (1992), bagi pelaut di masa lalu, Monomby memiliki posisi penting karena menjadi patokan yang mudah terlihat bagi pelayaran di selat yang memisahkan muara Sungai Musi di pulau Sumatera dengan pantai paling barat pulau Bangka.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:35:55-Pesanggrahan Menumbing 1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:35:55-Pesanggrahan Menumbing 2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:35:55-Pesanggrahan Menumbing 3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 18:35:55-Pesanggrahan Menumbing 4.jpg",
            "nama": "Sejarah Pesanggrahan Menumbing "
        },
        {
            "sejarah_id": "sejarah-dedc43c4-1d56-4a0e-9411-6df4ebd99fd2",
            "tipe": "sejarah",
            "nama": "Sejarah Panti Wangka",
            "deskripsi": "Societeit Concordia (sekarang panti Wangka) adalah gedung pertemuan bagi orang-orang Belanda di Pangkalpinang, terletak dikawasan Civic Centre atau berada pada bagian dari wilayah kota yang secara spasial menjadi pusat berbagi macam kegiatan masyarakat penghuninya. Gedung Societeit Concordia didirikan pada masa Residen Bangka, A.J.N. Engelenberg (memerintah pada tahun 1913-1918 M), ketika ibukota keresidenan Bangka mulai dipindahkan dari Kota Muntok ke Kota Pangkalpinang pada tanggal 3 September 1913 M. Gedung Societeit Concordia secara geografis terletak disisi Utara Residen Straat (sekarang Jalan merdeka) dan berada pada sisi sebelah Barat Wilhelmina Park (sekarang Tamansari). \\n\\nSocieteit Concordia awalnya digunakan sebagai tempat berkumpulnya ambtenar-ambtenar goebernemen (pegawai-pegawai tinggi pemerintah) dan pejabat-pejabat perusahaan BTW (Banka Tin Winning), para perwira tinggi militer, para pengusaha dan orang-orang kaya Belanda untuk berkumpul bersama, berekreasi, makan-makan, mendengarkan musik dan hiburan serta kesenian. Societeit Concordia pernah dijadikan tempat Konferensi Pangkalpinang yang diikuti sejumlah 80 orang delegasi dari sekitar 15 daerah pendudukan Belanda (Kalimantan Barat, Kalimantan Selatan, Kalimantan Timur, Bangka Belitung, Riau, Sulawesi Selatan, Minahasa, Menado, Bali, Lombok, Timor, Sangihe Talaud, Maluku Utara, Maluku Selatan dan Papua.\\n\\n Gedung Societeit Concordia setelah tahun 1953 M, seiring dengan dinasionalisasinya perusahaan-perusahaan pertambangan Timah, BTW (Banka Tin Winning Bedrjff), GMB (Gemenschaplijke Maatschappij Billiton) dan NV. SITEM (Singkep Tin Maatschappij) oleh pemerintah Republik Indonesia menjadi perusahaan milik negara (Perusahaan Negara atau PN Timah), maka pengelolaannya kemudian berada di Unit Penambangan Timah Bangka (UPTB) dan namanya diganti Panti Wangka, sementara fungsinya tetap digunakan sebagai gedung pertemuan. \\n\\nPada saat awal Kepulauan Belitung ditetapkan sebagai Provinsi pada tahun 2000, gedung Panti Wangka dijadikan sebagai gedung sementara DPRD Provinsi Kepulauan Bangka Belitung. Terakhir gedung panti Wangka juga pernah dipakai sebagai kantor Pengadilan Negeri Pangkalpinang ketika kantor Pengadilan Negeri di Jalan Jendral Sudirman sedang direnovasi dan saat ini Panti Wangka dijadikan Kantor KONI Kepulauan Bangka Belitung. Karena bangunan ini merupakan bangunan bersejarah, baik dalam konteks sejarah lokal maupun nasional dan sangat penting bagi pengembangan ilmu pengetahuan, sejarah serta memiliki nilai sosial budaya bagi masyarakat, maka oleh Balai Pelestarian Peninggalan Purbakala dan Dinas Kebudayaan Pariwisata Pemuda dan Olahraga Kota Pangkalpinang, berdasarkan ketentuan-ketentuan dalam Undang-undang Nomor 11 Tahun 2010 tentang Cagar Budaya, diregistrasi sebagai bangunan Cagar Budaya dan harus dilindungi negara.\\n\\n",
            "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:54:11-Panti Wangka (Societeit Concordia)1.jpg",
            "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:54:11-Panti Wangka (Societeit Concordia)2.jpg",
            "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:54:12-Panti Wangka (Societeit Concordia)3.jpg",
            "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:54:12-Panti Wangka (Societeit Concordia)4.jpg"
        }
    ],
    "error": false
}
```
### Get Detail Sejarah By Id

- **URL**: `/sejarah/{sejarah_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data sejarah berhasil ditemukan.",
    "data": {
        "sejarah_id": "sejarah-06d23b72-ad4d-4ef7-bdae-cedcfafefd45",
        "tipe": "sejarah",
        "nama": "Pemakaman Kerkhof",
        "deskripsi": "Kompleks pemakaman Belanda (Kerkhof), terletak di jalan Hormen Maddati Kelurahan Melintang Kecamatan Rangkui. Disini terdapat 100 makam dengan nisan bertuliskan bahasa Indonesia, Jepang dan Belanda. Yang tertua dari tahun 1902 dan yang termuda sekitar tahun 1950-an. Kompleks pemakaman ini memiliki keunikan karena kompleks pemakaman umum orang Belanda, salah satu makam tertua adalah makam nyonya Irene Mathilda Ehrecron yang wafat tanggal 10 Maret 1928. Disini juga terdapat makam tentara Belanda korban Perang Dunia Kedua. \\n\\nSelain itu yang menarik pada pemakaman Kerkhof adalah sepuluh makam orang Jepang. Dari sepuluh makam tersebut delapan masih bisa dibaca dengan jelas, satu makam sebagian tulisannya berbahasa jepang dan sebagian lainnya berbahasa Cina sehingga belum dapat dibaca dan ada satu makam tidak terbaca lagi tulisannya. Seluruh makam orang Jepang yang dimakamkan di Kerkhof menghadap ke arah Barat Daya dan Timur Laut dan mayoritas dari mereka adalah perempuan serta umumnya berasal dari daerah selatan jepang yang tergolong miskin. Kenapa sampai ada orang Jepang yang dimakamkan di Kerkhof merupakan pertanyaan menarik. \\n\\nDalam catatan sejarah, banyak perempuan Jepang yang disebut karayukisan atau pekerja seks komersial masuk ke Hindia Belanda melalui Singapura dan menyebar ke Medan, Palembang, Batavia, Surabaya bahkan sampai kepulau Bangka. Kehadiran pelacur impor seperti karayukisan memang menjadi favorit para lelaki hidung belang yang dimasa itu disebut pria hidung putih. Di Hindia Belanda termasuk di Bangka pada tahun 1898 M, bangsa Jepang disetujui sama status hukumnya dengan orang kulit putih hal ini memberi pengaruh pada status gengsi dan mobilitas vertikal naik, dari orang Jepang yang semula sebagai warga kelas dua (bersama dengan warga keturunan Arab dan Cina serta orang Timur Asing) menjadi warga kelas satu bersama warga Eropa atau bangsa asing kulit putih dan tidak mengherankan ketika mereka meninggal dunia kuburan mereka pun disamakan dengan warga Eropa di Kerkhof.\\n\\n",
        "foto": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)1.jpeg",
        "image": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)2.jpg",
        "image2": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:27-Pemakaman Belanda (Kerkhof)3.jpg",
        "image3": "https://storage.googleapis.com/seroean-buckets/sejarah-2025-03-09 20:35:28-Pemakaman Belanda (Kerkhof)4.jpg"
    },
    "error": false
}
```
### Add Notifikasi
- **URL**: `/notifikasi/add`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Body**:
  - `title` as `string` -`Judul Notifikasi`
- **Response**:

```json
{
    "status": 201,
    "message": "Notifikasi berhasil ditambahkan.",
    "error": false
}
```
### Get All Notifikasi

- **URL**: `/notifikasi`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data notifikasi berhasil diambil.",
    "data": [
        {
            "notifikasi_id": "notifikasi-66cd3706-861c-4ab7-b404-18a3618ef03a",
            "title": "Jelajahi Vihara Puri Tri Agung Sekarang",
            "datetime": "2025-03-10 12:56:43"
        }
    ],
    "error": false
}
```
### Add Pertanyaan

- **URL**: `/pertanyaan/add`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Body**:
  - `tipe` as `string` -`Tipe Pertanyaan`
  - `pertanyaan` as `string` -`Pertanyaan`
  - `jawaban` as `string` -`Jawaban Pertanyaan`
- **Response**:

```json
{
    "status": 201,
    "message": "Pertanyaan berhasil ditambahkan.",
    "error": false
}
```
### Get All Pertanyaan

- **URL**: `/pertanyaan`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data pertanyaan berhasil diambil.",
    "data": [
        {
            "pertanyaan_id": "pertanyaan-1245a5fa-2169-4832-ac23-4f234fd3ee29",
            "tipe": "Akun",
            "pertanyaan": "Bagaimana jika saya lupa kata sandi?",
            "jawaban": "Anda dapat mereset kata sandi melalui opsi Lupa Kata Sandi pada halaman login.",
            "datetime": "2025-03-10 03:28:45"
        },
        {
            "pertanyaan_id": "pertanyaan-526f7f64-3b60-49a0-a29d-6db7a10394eb",
            "tipe": "Penggunaan",
            "pertanyaan": "Bagaimana cara menambahkan favorit?",
            "jawaban": "Untuk menambahkan favorit, klik ikon hati pada gambar.",
            "datetime": "2025-03-10 03:25:12"
        },
        {
            "pertanyaan_id": "pertanyaan-77270141-4bf8-4817-b89e-b84038c0799f",
            "tipe": "Penggunaan",
            "pertanyaan": "Bagaimana cara menggunakan fitur pencarian?",
            "jawaban": "Gunakan kolom pencarian di bagian atas untuk menemukan konten yang Anda cari.",
            "datetime": "2025-03-10 03:32:27"
        },
        {
            "pertanyaan_id": "pertanyaan-a0d8cf14-ef15-4d72-8e18-8e775e9f89c2",
            "tipe": "Akun",
            "pertanyaan": "Bagaimana cara mendaftar di Seroean?",
            "jawaban": "Untuk mendaftar di Seroean, kamu cukup membuat akun dengan mengisi nama, email, dan kata sandi. Setelah itu, lakukan verifikasi email untuk mengaktifkan akunmu. Setelah pendaftaran selesai, kamu dapat menyesuaikan profil dan mulai menikmati fitur Seroean.",
            "datetime": "2025-03-10 03:29:28"
        },
        {
            "pertanyaan_id": "pertanyaan-b364490f-f23e-4af9-a03d-b01ca035a9f8",
            "tipe": "Penggunaan",
            "jawaban": "Untuk melihat disukai, buka menu pengguna dan pergi ke bagian disukai di aplikasi, di mana Anda dapat menemukan semua item yang telah Anda simpan.",
            "datetime": "2025-03-10 03:51:46",
            "pertanyaan": "Bagaimana cara melihat konten disukai?"
        },
        {
            "pertanyaan_id": "pertanyaan-e5b95202-d5ac-4beb-9470-40e593fc0ded",
            "tipe": "Penggunaan",
            "pertanyaan": "Bagaimana cara menggunakan fitur maps?",
            "jawaban": "Untuk menggunakan fitur maps, buka menu 'Peta' di aplikasi, lalu gunakan fungsi pencarian atau geser peta untuk menemukan lokasi yang Anda inginkan.",
            "datetime": "2025-03-10 03:30:18"
        }
    ],
    "error": false
}
```
### Get Detail Pertanyaan By Id

- **URL**: `/pertanyaan/{pertanyaan_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data pertanyaan berhasil ditemukan.",
    "data": {
        "pertanyaan_id": "pertanyaan-1245a5fa-2169-4832-ac23-4f234fd3ee29",
        "tipe": "Akun",
        "pertanyaan": "Bagaimana jika saya lupa kata sandi?",
        "jawaban": "Anda dapat mereset kata sandi melalui opsi Lupa Kata Sandi pada halaman login.",
        "datetime": "2025-03-10 03:28:45"
    },
    "error": false
}
```
### Add Ulasan

- **URL**: `/ulasan/add`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Body**:
  - `place_id` as `string` -`ID Wisata atau Kuliner`
  - `place_type` as `string` -`Tipe wisata atau kuliner`
  - `rating` as `string` -`Rating`
  - `komentar` as `string` -`Komentar`
- **Response**:

```json
{
    "status": 201,
    "message": "Ulasan berhasil ditambahkan.",
    "error": false
}
```
### Get Ulasan Wisata By Id

- **URL**: `/ulasan/wisata/{wisata_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Ulasan berhasil diambil.",
    "data": [
        {
            "ulasan_id": "ulasan-001db846-52cc-4345-92f4-f11b2846d38e",
            "place_id": "wisata-3039324e-1dee-4e92-914b-fe171e59b8b6",
            "place_type": "wisata",
            "user_id": "user-1d03f414-99ae-4893-9dae-6a423e8e0e26",
            "komentar": "Tempatnya Bagus",
            "rating": "4.5",
            "created_at": "2025-03-10 15:26:31"
        }
    ],
    "error": false
}
```

### Get Ulasan Kuliner By Id

- **URL**: `/ulasan/kuliner/{kuliner_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Ulasan berhasil diambil.",
    "data": [
        {
            "ulasan_id": "ulasan-8b19b611-7bc9-42fe-a192-3b9b0c2b93a0",
            "place_id": "kuliner-ff93e017-b754-40d2-82df-fd5ecf6dd32a",
            "place_type": "kuliner",
            "user_id": "user-1d03f414-99ae-4893-9dae-6a423e8e0e26",
            "komentar": "Tempatnya Bagus",
            "rating": "4.5",
            "created_at": "2025-03-10 15:34:12"
        }
    ],
    "error": false
}
```
### Add Pemandu

- **URL**: `/pemandu/add`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Request Body**:
  - `name` as `string` -`Nama Pemandu`
  - `notelp` as `string` -`No Telepon`
  - `profilePicture` as `string` -`File Foto Picture`
  - `wisata_id` as `file` -`ID Wisata`
- **Response**:

```json
{
    "status": 201,
    "message": "Pemandu berhasil ditambahkan.",
    "error": false
}
```
### Get Pemandu Wisata By Id

- **URL**: `/pemandu/{wisata_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Daftar pemandu berhasil diambil.",
    "data": [
        {
            "pemandu_id": "pemandu-42c3a8a3-de76-4789-8ebc-4e04111b33e9",
            "name": "Ahon",
            "notelp": "082189009800",
            "wisata_id": "wisata-3039324e-1dee-4e92-914b-fe171e59b8b6",
            "profilePicture": null
        }
    ],
    "error": false
}
```
### Add Favorite

- **URL**: `/favorite/add`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Body**:
  - `place_id` as `string` -`ID Wisata atau Kuliner`
  - `place_type` as `string` -`Tipe wisata atau kuliner atau budaya atau sejarah`
  - `is_favorite` as `boolean` -`True atau False`
- **Response**:

```json
{
    "status": 201,
    "message": "Berhasil menambahkan favorit.",
    "data": {
        "favorite_id": "fav-3188e153-d8c1-4786-85ec-b7eb50d39fd5",
        "user_id": "user-d262bb45-e1e0-41fe-afb0-d72dee9d7557",
        "place_id": "wisata-3039324e-1dee-4e92-914b-fe171e59b8b6",
        "place_type": "wisata",
        "is_favorite": "false"
    },
    "error": false
}
```
### Delete Favorite

- **URL**: `/favorite/remove`
- **Auth required**: `YES`
- **Method**: `DELETE`
- **Request Body**:
  - `favorite_id` as `string` -`ID Favorite`

- **Response**:

```json
{
    "status": 200,
    "message": "Berhasil menghapus dari favorit.",
    "error": false
}
```
### Get All Favorite

- **URL**: `/favorite`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Daftar favorit berhasil diambil.",
    "data": [
        {
            "favorite_id": "fav-1e4e78fb-a537-496d-80df-7b5b4edd0b3d",
            "user_id": "user-d262bb45-e1e0-41fe-afb0-d72dee9d7557",
            "place_id": "wisata-3039324e-1dee-4e92-914b-fe171e59b8b6",
            "place_type": "wisata",
            "is_favorite": "true"
        }
    ],
    "error": false
}
```
### Get Biodata User

- **URL**: `/biodata`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Data pengguna berhasil diambil.",
    "data": {
        "user_id": "user-1d03f414-99ae-4893-9dae-6a423e8e0e26",
        "name": "Ferdian",
        "email": "indonesiab379@gmail.com",
        "location": "Kebintik Dalam",
        "profilePicture": "https://storage.googleapis.com/seroean-buckets/user-1d03f414-99ae-4893-9dae-6a423e8e0e26-1741548881162-1741548880652_ccb96a75-eb74-4021-b5a1-68a6d082ea9d_profile.jpg",
        "createdAt": "2025-03-09 03:34:02"
    },
    "error": false
}
```
### Update Biodata User

- **URL**: `/biodata/update`
- **Method**: `POST`
- **Auth required**: `YES`
- **Request Header**:
  - `Content-Type` : `multipart/form-data`
- **Request Body (Optional)**:
  - `name` as `string` - `Nama`
  - `phone` as `string` - `Nomor Telepon`
  - `location` as `string` - `Lokasi`
  - `image` as `file` - `File Photo Profile`
- **Response**:

```json
{
    "status": 200,
    "message": "Data pengguna berhasil diperbarui.",
    "data": {
        "user_id": "user-1d03f414-99ae-4893-9dae-6a423e8e0e26",
        "email": "indonesiab379@gmail.com",
        "name": "Ferdian",
        "location": "Kebintik",
        "profilePicture": "https://storage.googleapis.com/seroean-buckets/user-1d03f414-99ae-4893-9dae-6a423e8e0e26-1741473094525-1733991495269.jpeg"
    },
    "error": false
}
```
### Get Detail User By Id

- **URL**: `/user/{user_id}`
- **Method**: `GET`
- **Auth required**: `YES`
- **Response**:

```json
{
    "status": 200,
    "message": "Detail pengguna berhasil diambil.",
    "data": {
        "user_id": "user-1d03f414-99ae-4893-9dae-6a423e8e0e26",
        "name": "Ferdian",
        "email": "indonesiab379@gmail.com",
        "location": "Kebintik Dalam",
        "profilePicture": "https://storage.googleapis.com/seroean-buckets/user-1d03f414-99ae-4893-9dae-6a423e8e0e26-1741548881162-1741548880652_ccb96a75-eb74-4021-b5a1-68a6d082ea9d_profile.jpg",
        "createdAt": "2025-03-09 03:34:02"
    },
    "error": false
}
```
### Check Email

- **URL**: `/check-email`
- **Auth required**: `YES`
- **Method**: `POST`
- **Request Body**:
  - `email` as `string` -`Email`
- **Response**:

```json
{
    "status": 200,
    "message": "Email sudah terdaftar.",
    "exists": true,
    "isVerified": true,
    "error": false
}
```

## Contributors
- Louis Michael
- Ferdian
- Ilham