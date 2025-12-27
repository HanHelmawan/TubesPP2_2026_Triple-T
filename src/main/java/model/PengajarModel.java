/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class PengajarModel {
    private String idPengajar;
    private String nama;
    private String bidang;

    public PengajarModel(String idPengajar, String nama, String bidang) {
        this.idPengajar = idPengajar;
        this.nama = nama;
        this.bidang = bidang;
    }

    // Getters and Setters
    public String getIdPengajar() { return idPengajar; }
    public void setIdPengajar(String idPengajar) { this.idPengajar = idPengajar; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getBidang() { return bidang; }
    public void setBidang(String bidang) { this.bidang = bidang; }
}