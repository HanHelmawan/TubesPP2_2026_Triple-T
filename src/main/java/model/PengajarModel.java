/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PengajarModel {
    private String idPengajar;
    private String nama;
    private String spesialisasi;
    private String noTelepon;
    private String alamat;

    public PengajarModel(String idPengajar, String nama, String spesialisasi, String noTelepon, String alamat) {
        this.idPengajar = idPengajar;
        this.nama = nama;
        this.spesialisasi = spesialisasi;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    // Constructor without ID (for new entries if auto-generated, though we use
    // String ID currently)
    public PengajarModel(String nama, String spesialisasi, String noTelepon, String alamat) {
        this.nama = nama;
        this.spesialisasi = spesialisasi;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
    }

    // Getters and Setters
    public String getIdPengajar() {
        return idPengajar;
    }

    public void setIdPengajar(String idPengajar) {
        this.idPengajar = idPengajar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    // Legacy support for 'bidang' if needed, or we can just remove it.
    // Since we are refactoring, better to remove 'bidang' to force compile errors
    // where it's used.
}
