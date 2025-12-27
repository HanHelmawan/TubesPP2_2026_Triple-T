# Sistem Administrasi Bimbingan Belajar (Bimbel) - Triple-T

Repository ini berisi source code Tugas Besar mata kuliah **Praktikum Pemrograman II**. Aplikasi ini dibangun menggunakan bahasa pemrograman Java dengan arsitektur **MVC (Model-View-Controller)** dan database **MySQL**.

## 📋 Deskripsi Singkat
Aplikasi ini dirancang untuk membantu proses administrasi operasional sebuah Tempat Bimbingan Belajar (Bimbel). Sistem ini memungkinkan admin untuk mengelola data siswa yang mendaftar, data pengajar, serta penjadwalan mata pelajaran. Selain itu, aplikasi ini memiliki fitur untuk mencetak laporan jadwal atau data siswa ke dalam format PDF.

## 🚀 Fitur Utama
Sesuai dengan spesifikasi tugas besar, aplikasi ini memiliki fitur:

1.  **Manajemen Data Siswa (CRUD)**
    * Input pendaftaran siswa baru.
    * Edit data profil siswa.
    * Hapus data siswa non-aktif.
    * Lihat daftar siswa.
2.  **Manajemen Data Pengajar (CRUD)**
    * Input data pengajar baru.
    * Update spesialisasi mata pelajaran pengajar.
    * Hapus data pengajar.
    * Lihat daftar pengajar.
3.  **Manajemen Jadwal & Kelas (CRUD)**
    * Input jadwal pelajaran baru.
    * Ubah waktu atau ruangan kelas.
    * Hapus jadwal.
    * Lihat jadwal aktif.
4.  **Fitur Tambahan**
    * **Export PDF:** Mencetak laporan data siswa/jadwal ke dokumen PDF.
    * **Validasi Input:** Memastikan data yang dimasukkan sesuai format (tidak boleh kosong, format angka, dll).

## 🛠️ Teknologi yang Digunakan
* **Bahasa:** Java 
* **GUI:** Java Swing
* **Arsitektur:** MVC (Model-View-Controller)
* **Database:** MySQL

## 👥 Anggota Tim & Pembagian Tugas
Kelompok kami terdiri dari 4 orang dengan pembagian tugas sebagai berikut agar semua anggota aktif berkontribusi di GitHub:

| Nama Anggota | NIM | Peran Utama | Rincian Tugas (Task Distribution) |
| :--- | :--- | :--- | :--- |
| **Raihan Azzani Helmawan** | 233040135 | **Team Leader** | • Setup: Siapin Repo, `.gitignore`, & Library project. <br>• Database: Bikin file `DBConnections.java`. <br>• UI Utama: Bikin `MainFrame.java` (Menu & Navigasi). <br>• Git Master: Review & Merge kode tim ke branch main. |
| **Danny Lukman** | 233040097 | **Programmer (Modul Siswa)** | • Model: `SiswaModel.java` (CRUD ke DB). <br>• View: Bikin Panel Form Input & Tabel Siswa. <br>• Controller: `SiswaController.java` (Logika tombol). <br>• Validasi: Pastikan input data siswa aman. |
| **Rama Sadea Putra** | 233040122 | **Programmer (Modul Pengajar)** | • Model: `PengajarModel.java` (CRUD ke DB). <br>• View: Bikin Panel Form Input & Tabel Pengajar. <br>• Controller: `PengajarController.java` (Logika tombol). <br>• Validasi: Pastikan input data pengajar aman. |
| **M. Farrel Octora** | 233040124 | **Programmer (Modul Jadwal & Laporan)** | • Model: `JadwalModel.java` (CRUD ke DB). <br>• View: Bikin Panel Form Input & Tabel Jadwal. <br>• Controller: `JadwalController.java`. <br>• Fitur PDF: Bikin class `PDFExport.java` buat cetak laporan. |


---
**Catatan:**
Project ini disusun untuk memenuhi syarat kelulusan Praktikum Pemrograman II Semester Ganjil 2025/2026.
