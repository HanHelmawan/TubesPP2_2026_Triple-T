/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.JadwalModel;
import config.DBConnections;
import util.PDFExport;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalController {

    public void tambahJadwal(JadwalModel jadwal) {
        String sql = "INSERT INTO jadwal (id_jadwal, id_pengajar, id_mapel, hari, jam_mulai, jam_selesai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnections.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jadwal.getIdJadwal());
            pstmt.setString(2, jadwal.getIdPengajar());
            pstmt.setString(3, jadwal.getIdMapel());
            pstmt.setString(4, jadwal.getHari());
            pstmt.setString(5, jadwal.getJamMulai());
            pstmt.setString(6, jadwal.getJamSelesai());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan jadwal: " + e.getMessage());
        }
    }

    public void updateJadwal(JadwalModel jadwal) {
        String sql = "UPDATE jadwal SET id_pengajar = ?, id_mapel = ?, hari = ?, jam_mulai = ?, jam_selesai = ? WHERE id_jadwal = ?";
        try (Connection conn = DBConnections.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jadwal.getIdPengajar());
            pstmt.setString(2, jadwal.getIdMapel());
            pstmt.setString(3, jadwal.getHari());
            pstmt.setString(4, jadwal.getJamMulai());
            pstmt.setString(5, jadwal.getJamSelesai());
            pstmt.setString(6, jadwal.getIdJadwal());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate jadwal: " + e.getMessage());
        }
    }

    public void deleteJadwal(String idJadwal) {
        String sql = "DELETE FROM jadwal WHERE id_jadwal = ?";
        try (Connection conn = DBConnections.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idJadwal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus jadwal: " + e.getMessage());
        }
    }

    public List<JadwalModel> getAllJadwal() {
        List<JadwalModel> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal";
        try (Connection conn = DBConnections.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new JadwalModel(
                    rs.getString("id_jadwal"),
                    rs.getString("id_pengajar"),
                    rs.getString("id_mapel"),
                    rs.getString("hari"),
                    rs.getString("jam_mulai"),
                    rs.getString("jam_selesai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil jadwal: " + e.getMessage());
        }
        return list;
    }

    public Object[][] getTableData() {
        List<JadwalModel> list = getAllJadwal();
        Object[][] data = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            JadwalModel j = list.get(i);
            data[i][0] = j.getIdJadwal();
            data[i][1] = j.getIdPengajar();
            data[i][2] = j.getIdMapel();
            data[i][3] = j.getHari();
            data[i][4] = j.getJamMulai();
            data[i][5] = j.getJamSelesai();
        }
        return data;
    }

    public void exportToPdf() {
        List<JadwalModel> list = getAllJadwal();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data jadwal untuk diekspor.");
        } else {
            // Gunakan nama file yang unik untuk jadwal
            PDFExport.exportJadwalToPdf(list, "Laporan_Jadwal_Kelas_" + System.currentTimeMillis() + ".pdf");
        }
    }
}