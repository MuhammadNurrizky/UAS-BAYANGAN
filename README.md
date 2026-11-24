# Board Game Buddy - Spring AI

Aplikasi Spring Boot sederhana untuk Chapter 1 dan Chapter 2
"Spring AI in Action".

## Pengaturan Nararouter

- Java 21
- Spring Boot 4.1.3
- Spring AI 2.0.1
- Nararouter API dengan model `gpt-6-luna`
- WireMock untuk pengujian deterministik

Isi `.env` dengan API key dan base URL dari Nararouter:

```dotenv
NARAROUTER_API_KEY=isi_api_key_anda
NARAROUTER_BASE_URL=isi_base_url_dari_nararouter
NARAROUTER_MODEL=gpt-6-luna
```

`.env` tidak dimasukkan Git. Base URL mengikuti alamat API-compatible yang diberikan Nararouter.

## Menjalankan aplikasi

```powershell
.\run.ps1
```

## Menjalankan pengujian

```powershell
.\gradlew.bat test
```

## Contoh endpoint Chapter 1

```powershell
curl.exe -X POST http://localhost:8080/ask `
  -H "Content-Type: application/json" `
  -d '{\"question\":\"Game apa yang cocok untuk keluarga dengan 4 pemain?\"}'
```

## Contoh endpoint Chapter 2

```powershell
curl.exe -X POST http://localhost:8080/evaluate `
  -H "Content-Type: application/json" `
  -d '{\"question\":\"Apa board game untuk 4 pemain?\",\"answer\":\"Catan cocok untuk 3-4 pemain.\"}'
```

Endpoint evaluasi menggunakan `RelevancyEvaluator` dan
`FactCheckingEvaluator` milik Spring AI. Kedua hasil digabung menjadi status lulus
dan skor rata-rata.
