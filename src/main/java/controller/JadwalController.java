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
        String sql = "INSERT INTO jadwal (mata_pelajaran, hari, jam_mulai, jam_selesai, ruangan, id_pengajar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnections.getConnection(); // Use getConnection()
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, jadwal.getMataPelajaran());
            pstmt.setString(2, jadwal.getHari());
            pstmt.setString(3, jadwal.getJamMulai()); // Assuming String format HH:mm
            pstmt.setString(4, jadwal.getJamSelesai());
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
        }
    }

    public void updateJadwal(JadwalModel jadwal) {
        String sql = "UPDATE jadwal SET mata_pelajaran = ?, hari = ?, jam_mulai = ?, jam_selesai = ?, ruangan = ?, id_pengajar = ? WHERE id_jadwal = ?";
        try (Connection conn = DBConnections.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jadwal.getMataPelajaran());
            pstmt.setString(2, jadwal.getHari());
            pstmt.setString(3, jadwal.getJamMulai());
            pstmt.setString(4, jadwal.getJamSelesai());
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
        try (Connection conn = DBConnections.getConnection();
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
        }
    }

    public List<JadwalModel> getAllJadwal() {
        List<JadwalModel> list = new ArrayList<>();
        String sql = "SELECT * FROM jadwal";

        try (Connection conn = DBConnections.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Adjust per database schema
                list.add(new JadwalModel(
                        rs.getInt("id_jadwal"),
                        rs.getString("mata_pelajaran"),
                        rs.getString("hari"),
                        rs.getString("jam_mulai"), // Assuming stored as String/Time converted to String
                        rs.getString("jam_selesai"),
                        rs.getString("ruangan"),
                        rs.getInt("id_pengajar")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (!e.getMessage().contains("Koneksi ke Database Gagal")) {
                JOptionPane.showMessageDialog(null, "Gagal mengambil jadwal: " + e.getMessage());
            }
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
        }
        return data;
    }

    public void exportToPdf() {
        try {
            List<JadwalModel> list = getAllJadwal();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada data jadwal untuk diekspor.");
                return;
            }
            String filename = "Laporan_Jadwal_" + System.currentTimeMillis() + ".pdf";
            PDFExport.exportJadwalToPdf(list, filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal melakukan export PDF: " + e.getMessage());
        }
    }

    // Method untuk mendapatkan ID berikutnya yang akan di-generate
    public int getNextId() {
        String sql = "SELECT MAX(id_jadwal) as max_id FROM jadwal";
        try (Connection conn = DBConnections.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default jika tabel kosong
    }
}