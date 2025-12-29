/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.JadwalModel;

import config.DBConnections; // Ganti import

import config.DBConnections;

import util.PDFExport;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JadwalController {

    public void tambahJadwal(JadwalModel jadwal) {

        String sql = "INSERT INTO jadwal (mata_pelajaran, hari, jam_mulai, jam_selesai, ruangan, id_pengajar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnections.configDB(); // <-- Ganti
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, jadwal.getMataPelajaran());
            pstmt.setString(2, jadwal.getHari());
            pstmt.setTime(3, Time.valueOf(jadwal.getJamMulai() + ":00"));
            pstmt.setTime(4, Time.valueOf(jadwal.getJamSelesai() + ":00"));
            pstmt.setString(5, jadwal.getRuangan());
            pstmt.setInt(6, jadwal.getIdPengajar());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    jadwal.setIdJadwal(rs.getInt(1));
                }
            }
            JOptionPane.showMessageDialog(null, "Data jadwal berhasil ditambahkan.");
        } catch (SQLException e) {
            e.printStackTrace();
            if (!e.getMessage().contains("Koneksi ke Database Gagal")) {
                JOptionPane.showMessageDialog(null, "Gagal menambahkan jadwal: " + e.getMessage());
            }

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

        String sql = "UPDATE jadwal SET mata_pelajaran = ?, hari = ?, jam_mulai = ?, jam_selesai = ?, ruangan = ?, id_pengajar = ? WHERE id_jadwal = ?";
        try (Connection conn = DBConnections.configDB(); // <-- Ganti
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jadwal.getMataPelajaran());
            pstmt.setString(2, jadwal.getHari());
            pstmt.setTime(3, Time.valueOf(jadwal.getJamMulai() + ":00"));
            pstmt.setTime(4, Time.valueOf(jadwal.getJamSelesai() + ":00"));
            pstmt.setString(5, jadwal.getRuangan());
            pstmt.setInt(6, jadwal.getIdPengajar());
            pstmt.setInt(7, jadwal.getIdJadwal());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data jadwal berhasil diupdate.");
        } catch (SQLException e) {
            e.printStackTrace();
            if (!e.getMessage().contains("Koneksi ke Database Gagal")) {
                JOptionPane.showMessageDialog(null, "Gagal mengupdate jadwal: " + e.getMessage());
            }
        }
    }

    public void deleteJadwal(int idJadwal) {
        String sql = "DELETE FROM jadwal WHERE id_jadwal = ?";
        try (Connection conn = DBConnections.configDB(); // <-- Ganti
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idJadwal);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data jadwal berhasil dihapus.");
            } else {
                JOptionPane.showMessageDialog(null, "Data jadwal dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (!e.getMessage().contains("Koneksi ke Database Gagal")) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus jadwal: " + e.getMessage());
            }

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

        try (Connection conn = DBConnections.configDB(); // <-- Ganti

        try (Connection conn = DBConnections.getConnection();

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new JadwalModel(

                    rs.getInt("id_jadwal"),
                    rs.getString("mata_pelajaran"),
                    rs.getString("hari"),
                    rs.getTime("jam_mulai").toString().substring(0, 5),
                    rs.getTime("jam_selesai").toString().substring(0, 5),
                    rs.getString("ruangan"),
                    rs.getInt("id_pengajar")

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

            if (!e.getMessage().contains("Koneksi ke Database Gagal")) {
                JOptionPane.showMessageDialog(null, "Gagal mengambil jadwal: " + e.getMessage());
            }

            JOptionPane.showMessageDialog(null, "Gagal mengambil jadwal: " + e.getMessage());

        }
        return list;
    }

    public Object[][] getTableData() {
        List<JadwalModel> list = getAllJadwal();

        Object[][] data = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            JadwalModel j = list.get(i);
            data[i][0] = j.getIdJadwal();
            data[i][1] = j.getMataPelajaran();
            data[i][2] = j.getHari();
            data[i][3] = j.getJamMulai();
            data[i][4] = j.getJamSelesai();
            data[i][5] = j.getRuangan();
            data[i][6] = j.getIdPengajar();

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