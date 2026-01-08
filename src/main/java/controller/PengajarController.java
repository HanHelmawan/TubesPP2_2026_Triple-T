/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.PengajarModel;
import config.DBConnections;
import util.PDFExport;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PengajarController {

    public void tambahPengajar(PengajarModel pengajar) {
        // Cari ID kosong (gap) atau ID berikutnya
        int newId = Integer.parseInt(getNextId());
        pengajar.setIdPengajar(String.valueOf(newId));

        String sql = "INSERT INTO pengajar (id_pengajar, nama_pengajar, spesialisasi, no_telepon, alamat) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newId);
            pstmt.setString(2, pengajar.getNama());
            pstmt.setString(3, pengajar.getSpesialisasi());
            pstmt.setString(4, pengajar.getNoTelepon());
            pstmt.setString(5, pengajar.getAlamat());
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data pengajar berhasil ditambahkan");
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
        try {
            List<PengajarModel> list = getAllPengajar();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada data pengajar untuk diekspor.");
                return;
            }
            String filename = "Laporan_Data_Pengajar_" + System.currentTimeMillis() + ".pdf";
            PDFExport.exportPengajarToPdf(list, filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal melakukan export PDF: " + e.getMessage());
        }
    }

    // Method untuk mendapatkan ID Kosong (Gap) berikutnya
    public String getNextId() {
        String sql = "SELECT id_pengajar FROM pengajar ORDER BY CAST(id_pengajar AS UNSIGNED) ASC";
        try (Connection conn = DBConnections.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            int expectedId = 1;
            while (rs.next()) {
                int currentId = rs.getInt("id_pengajar");
                if (currentId != expectedId) {
                    return String.valueOf(expectedId); // Menemukan celah
                }
                expectedId++;
            }
            return String.valueOf(expectedId); // Tidak ada celah
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "1"; // Default jika tabel kosong
    }

    public java.util.List<PengajarModel> searchPengajar(String keyword) {
        java.util.List<PengajarModel> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM pengajar WHERE nama_pengajar LIKE ? OR spesialisasi LIKE ?";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PengajarModel p = new PengajarModel(
                            rs.getString("nama_pengajar"),
                            rs.getString("spesialisasi"),
                            rs.getString("no_telepon"),
                            rs.getString("alamat"));
                    p.setIdPengajar(String.valueOf(rs.getInt("id_pengajar")));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
