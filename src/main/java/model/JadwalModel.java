/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class JadwalModel {
    private String idJadwal;
    private String idPengajar;
    private String idMapel;
    private String hari;
    private String jamMulai;
    private String jamSelesai;

    public JadwalModel(String idJadwal, String idPengajar, String idMapel, String hari, String jamMulai, String jamSelesai) {
        this.idJadwal = idJadwal;
        this.idPengajar = idPengajar;
        this.idMapel = idMapel;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }
    
    public String getIdJadwal() { return idJadwal; }
    public void setIdJadwal(String idJadwal) { this.idJadwal = idJadwal; }
    public String getIdPengajar() { return idPengajar; }
    public void setIdPengajar(String idPengajar) { this.idPengajar = idPengajar; }
    public String getIdMapel() { return idMapel; }
    public void setIdMapel(String idMapel) { this.idMapel = idMapel; }
    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }
    public String getJamMulai() { return jamMulai; }
    public void setJamMulai(String jamMulai) { this.jamMulai = jamMulai; }
    public String getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(String jamSelesai) { this.jamSelesai = jamSelesai; }
}