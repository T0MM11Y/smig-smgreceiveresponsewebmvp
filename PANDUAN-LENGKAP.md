# 🚀 Panduan Lengkap Menyelesaikan Proyek SMIG-SMG ReceiveResponseWEB MVP

## 📋 RINGKASAN YANG HARUS DILAKUKAN

Ikuti langkah-langkah berikut secara berurutan untuk menyelesaikan proyek ini sampai berjalan dengan sempurna.

---

## FASE 1: PERSIAPAN ENVIRONMENT (15 menit)

### 1.1. Verifikasi Prerequisites

```powershell
# Cek Java version (harus 17+)
java -version

# Cek Maven version (harus 3.9+)
mvn -version

# Cek Git
git --version
```

**✅ CHECKLIST:**

- [ ] Java 17+ terinstall
- [ ] Maven 3.9+ terinstall
- [ ] Git terinstall
- [ ] Oracle Database running (XE/Free 21c+)

### 1.2. Setup Database Oracle

1. **Buka SQL\*Plus atau SQL Developer**
2. **Connect sebagai SYSTEM atau privileged user**
   sqlplus system/12345678@localhost:1521/XEPDB1
3. **Jalankan script database-setup.sql:**
   ```sql
   @database-setup.sql
   ```
4. **Verifikasi user berhasil dibuat:**
   ```sql
   SELECT username FROM dba_users WHERE username = 'SMG_APP';
   ```

### 1.3. Setup Environment Variables

**Jalankan script setup environment:**

```powershell
# Masuk ke direktori proyek
cd C:\Users\kiel\Documents\TWM\ResponseApiSMG\SMIG-SMGReceiveResponseWEBMVP

# Jalankan setup environment
.\setup-env.bat
```

**✅ CHECKLIST:**

- [ ] User `smg_app` berhasil dibuat di Oracle
- [ ] Environment variables berhasil di-set
- [ ] Restart command prompt/IDE

---

## FASE 2: BUILD DAN TESTING (10 menit)

### 2.1. Build Aplikasi

```powershell
# Masuk ke direktori proyek
cd C:\Users\kiel\Documents\TWM\ResponseApiSMG\SMIG-SMGReceiveResponseWEBMVP

# Clean build
mvn clean

# Run tests
mvn test

# Build tanpa test (jika test gagal)
mvn clean package -DskipTests
```

### 2.2. Verifikasi Build

**✅ CHECKLIST:**

- [ ] Build SUCCESS tanpa error
- [ ] File JAR terbentuk di folder `target/`
- [ ] Tests passing (minimal 80% coverage)

---

## FASE 3: MENJALANKAN APLIKASI (5 menit)

### 3.1. Jalankan Aplikasi

**Opsi 1 - Menggunakan script:**

```powershell
.\run.bat
```

**Opsi 2 - Manual:**

```powershell
mvn spring-boot:run
```

**Opsi 3 - Jalankan JAR:**

```powershell
java -jar target/smig-smg-receive-response-web-1.0.0-SNAPSHOT.jar
```

### 3.2. Verifikasi Aplikasi Berjalan

**Cek log startup untuk memastikan:**

- Database connection berhasil
- Flyway migration berhasil
- Server started on port 8080

**✅ CHECKLIST:**

- [ ] Aplikasi start tanpa error
- [ ] Database connection established
- [ ] Flyway migration executed successfully
- [ ] Server accessible di port 8080

---

## FASE 4: TESTING ENDPOINT (10 menit)

### 4.1. Akses Swagger UI

**Buka browser:** `http://localhost:8080/smg-api/swagger-ui.html`

### 4.2. Test Manual API

#### Test 1: Health Check

```bash
curl http://localhost:8080/smg-api/actuator/health
```

**Expected Response:** `{"status":"UP"}`

#### Test 2: POST API - Create Response

```bash
curl -X POST http://localhost:8080/smg-api/api/v1/responses \
  -H "Content-Type: application/json" \
  -d '{"content":"Hello SMIG MVP Test!"}'
```

**Expected Response:** HTTP 201 dengan data response message

#### Test 3: GET API - Get All Responses

```bash
curl http://localhost:8080/smg-api/api/v1/responses
```

**Expected Response:** HTTP 200 dengan array response messages

#### Test 4: GET API - Get Response by ID

```bash
curl http://localhost:8080/smg-api/api/v1/responses/1
```

**Expected Response:** HTTP 200 dengan data response message ID 1

### 4.3. Test Validation

```bash
# Test dengan content kosong (should return 400)
curl -X POST http://localhost:8080/smg-api/api/v1/responses \
  -H "Content-Type: application/json" \
  -d '{"content":""}'
```

**Expected Response:** HTTP 400 dengan pesan validation error

**✅ CHECKLIST:**

- [ ] Swagger UI dapat diakses
- [ ] POST endpoint bekerja (return 201)
- [ ] GET all endpoint bekerja (return 200)
- [ ] GET by ID endpoint bekerja (return 200)
- [ ] Validation bekerja (return 400 untuk input invalid)
- [ ] Error handling bekerja (return 404 untuk ID tidak ada)

---

## FASE 5: VERIFIKASI DATABASE (5 menit)

### 5.1. Cek Database

**Connect ke Oracle sebagai smg_app:**

```sql
sqlplus smg_app/12345678@localhost:1521/XEPDB1
```

**Verifikasi tabel berhasil dibuat:**

```sql
SELECT table_name FROM user_tables;
-- Should show: RESPONSE_MESSAGE

SELECT sequence_name FROM user_sequences;
-- Should show: RESP_SEQ

DESC RESPONSE_MESSAGE;
-- Should show table structure
```

### 5.2. Cek Data

```sql
SELECT * FROM RESPONSE_MESSAGE ORDER BY CREATED_AT DESC;
-- Should show data yang di-insert via API
```

**✅ CHECKLIST:**

- [ ] Tabel `RESPONSE_MESSAGE` ada
- [ ] Sequence `RESP_SEQ` ada
- [ ] Data hasil POST API tersimpan di database
- [ ] Timestamps ter-generate otomatis

---

## FASE 6: FINAL VERIFICATION (5 menit)

### 6.1. Test Complete Flow

1. **POST** - Create new response message
2. **GET All** - Verify message appears in list
3. **GET by ID** - Verify specific message can be retrieved
4. **Check Database** - Verify data persisted correctly

### 6.2. Test Error Scenarios

1. **Invalid Input** - POST dengan content kosong
2. **Not Found** - GET dengan ID yang tidak ada
3. **Database Connection** - Matikan Oracle, restart app (should fail gracefully)

**✅ FINAL CHECKLIST:**

- [ ] ✅ Semua endpoints bekerja sesuai spesifikasi
- [ ] ✅ Data tersimpan dengan benar di Oracle
- [ ] ✅ Error handling berfungsi dengan baik
- [ ] ✅ Logging informatif dan terstruktur
- [ ] ✅ API documentation lengkap di Swagger
- [ ] ✅ Unit tests passing dengan coverage >80%

---

## 🎯 HASIL AKHIR YANG DIHARAPKAN

Setelah mengikuti semua langkah, Anda akan memiliki:

1. **✅ Working REST API** dengan 3 endpoints:

   - `POST /api/v1/responses` - Create response message
   - `GET /api/v1/responses` - Get all response messages
   - `GET /api/v1/responses/{id}` - Get response by ID

2. **✅ Oracle Database Integration** dengan:

   - User `smg_app` untuk aplikasi
   - Tabel `RESPONSE_MESSAGE` dengan proper schema
   - Flyway migration yang berjalan otomatis

3. **✅ Production-Ready Features**:

   - Global error handling
   - Input validation menggunakan @Valid
   - Structured logging dengan Logback
   - API documentation dengan Swagger
   - Unit & integration tests

4. **✅ Development Ready**:
   - Environment-specific configurations
   - Easy build dan run scripts
   - Comprehensive documentation

---

## 🐛 TROUBLESHOOTING

### Jika Build Gagal:

```powershell
# Clear Maven cache
mvn dependency:purge-local-repository

# Rebuild
mvn clean compile
```

### Jika Database Connection Gagal:

1. Cek Oracle service running
2. Cek environment variables
3. Test koneksi manual dengan SQL\*Plus

### Jika Port 8080 Digunakan:

```powershell
# Gunakan port lain
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### Jika Tests Gagal:

```powershell
# Skip tests untuk build
mvn clean package -DskipTests

# Run specific test
mvn test -Dtest=ResponseServiceTest
```

---

## 📞 SUPPORT

Jika ada masalah:

1. Cek log aplikasi untuk error details
2. Verifikasi semua prerequisites terinstall
3. Pastikan environment variables ter-set dengan benar
4. Cek koneksi database manual

**Selamat! Proyek SMIG-SMG ReceiveResponseWEB MVP siap untuk development dan deployment! 🎉**
