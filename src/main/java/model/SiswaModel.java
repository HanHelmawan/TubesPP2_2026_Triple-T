package model;

public class SiswaModel {
    private int idSiswa;
    private String nama;
    private String sekolah;
    private String kelas;
    private String alamat;
    private String telepon;
    private String status;

    public SiswaModel(int idSiswa, String nama, String sekolah, String kelas, String alamat, String telepon,
            String status) {
        this.idSiswa = idSiswa;
        this.nama = nama;
        this.sekolah = sekolah;
        this.kelas = kelas;
        this.alamat = alamat;
        this.telepon = telepon;
        this.status = status;
    }

    public SiswaModel(String nama, String sekolah, String kelas, String alamat, String telepon, String status) {
        this.nama = nama;
        this.sekolah = sekolah;
        this.kelas = kelas;
        this.alamat = alamat;
        this.telepon = telepon;
        this.status = status;
    }

    // Getters and Setters
    public int getIdSiswa() {
        return idSiswa;
    }

    public void setIdSiswa(int idSiswa) {
        this.idSiswa = idSiswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
