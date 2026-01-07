/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PengajarModel;
import config.DBConnections;
// import util.PDFExport; // Pastikan import ini ada
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PengajarController {

    public void tambahPengajar(PengajarModel pengajar) {
        // id_pengajar is AUTO_INCREMENT, so we don't insert it.
        String sql = "INSERT INTO pengajar (nama_pengajar, spesialisasi, no_telepon, alamat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pengajar.getNama());
            pstmt.setString(2, pengajar.getSpesialisasi());
            pstmt.setString(3, pengajar.getNoTelepon());
            pstmt.setString(4, pengajar.getAlamat());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan pengajar: " + e.getMessage());
        }
    }

    public void updatePengajar(PengajarModel pengajar) {
        String sql = "UPDATE pengajar SET nama_pengajar = ?, spesialisasi = ?, no_telepon = ?, alamat = ? WHERE id_pengajar = ?";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pengajar.getNama());
            pstmt.setString(2, pengajar.getSpesialisasi());
            pstmt.setString(3, pengajar.getNoTelepon());
            pstmt.setString(4, pengajar.getAlamat());
            pstmt.setString(5, pengajar.getIdPengajar());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate pengajar: " + e.getMessage());
        }
    }

    public void deletePengajar(String id) {
        String sql = "DELETE FROM pengajar WHERE id_pengajar = ?";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus pengajar: " + e.getMessage());
        }
    }

    public List<PengajarModel> getAllPengajar() {
        List<PengajarModel> list = new ArrayList<>();
        String sql = "SELECT * FROM pengajar";
        try (Connection conn = DBConnections.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PengajarModel(
                        rs.getString("id_pengajar"),
                        rs.getString("nama_pengajar"),
                        rs.getString("spesialisasi"),
                        rs.getString("no_telepon"),
                        rs.getString("alamat")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil pengajar: " + e.getMessage());
        }
        return list;
    }

    public Object[][] getTableData() {
        List<PengajarModel> list = getAllPengajar();
        Object[][] data = new Object[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            PengajarModel p = list.get(i);
            data[i][0] = p.getIdPengajar();
            data[i][1] = p.getNama();
            data[i][2] = p.getSpesialisasi();
            data[i][3] = p.getNoTelepon();
            data[i][4] = p.getAlamat();
        }
        return data;
    }

    public void exportToPdf() {
        List<PengajarModel> list = getAllPengajar();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data pengajar untuk diekspor.");
        } else {
            // PDFExport.exportPengajarToPdf(list, "Laporan_Data_Pengajar_" +
            // System.currentTimeMillis() + ".pdf");
            JOptionPane.showMessageDialog(null, "Fitur eksport sedang diperbaiki.");
        }
    }

}
