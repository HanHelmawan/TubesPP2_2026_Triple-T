package controller;

import model.SiswaModel;
import util.PDFExport;

import javax.swing.*;

public class SiswaController {

    public SiswaController() {
    }

    public void tambahSiswa(SiswaModel siswa) {
        String sql = "INSERT INTO siswa (nama_siswa, asal_sekolah, kelas, alamat, no_telepon, status_aktif) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, siswa.getNama());
            pstmt.setString(2, siswa.getSekolah());
            pstmt.setString(3, siswa.getKelas());
            pstmt.setString(4, siswa.getAlamat());
            pstmt.setString(5, siswa.getTelepon());
            pstmt.setString(6, siswa.getStatus());
            pstmt.executeUpdate();
            
            // Membaca ID yang dihasilkan dari auto increment
            try (java.sql.ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    siswa.setIdSiswa(rs.getInt(1));
                }
            }
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan siswa: " + e.getMessage());
        }
    }

    public void updateSiswa(SiswaModel siswa) {
        String sql = "UPDATE siswa SET nama_siswa=?, asal_sekolah=?, kelas=?, alamat=?, no_telepon=?, status_aktif=? WHERE id_siswa=?";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, siswa.getNama());
            pstmt.setString(2, siswa.getSekolah());
            pstmt.setString(3, siswa.getKelas());
            pstmt.setString(4, siswa.getAlamat());
            pstmt.setString(5, siswa.getTelepon());
            pstmt.setString(6, siswa.getStatus());
            pstmt.setInt(7, siswa.getIdSiswa());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate siswa: " + e.getMessage());
        }
    }

    public void deleteSiswa(int id) {
        String sql = "DELETE FROM siswa WHERE id_siswa=?";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus siswa: " + e.getMessage());
        }
    }

    public java.util.List<SiswaModel> getAllSiswa() {
        java.util.List<SiswaModel> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM siswa";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new SiswaModel(
                        rs.getInt("id_siswa"),
                        rs.getString("nama_siswa"),
                        rs.getString("asal_sekolah"),
                        rs.getString("kelas"),
                        rs.getString("alamat"),
                        rs.getString("no_telepon"),
                        rs.getString("status_aktif") == null ? "Non-Aktif" : rs.getString("status_aktif")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data siswa: " + e.getMessage());
        }
        return list;
    }

    public Object[][] getTableData() {
        java.util.List<SiswaModel> list = getAllSiswa();
        Object[][] data = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            SiswaModel s = list.get(i);
            data[i][0] = s.getIdSiswa();
            data[i][1] = s.getNama();
            data[i][2] = s.getSekolah();
            data[i][3] = s.getKelas();
            data[i][4] = s.getTelepon();
            data[i][5] = s.getAlamat();
            data[i][6] = s.getStatus();
        }
        return data;
    }

    public void exportToPdf() {
        try {
            java.util.List<SiswaModel> list = getAllSiswa();
            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Tidak ada data siswa untuk diekspor.");
                return;
            }
            String filename = "Laporan_Data_Siswa_" + System.currentTimeMillis() + ".pdf";
            PDFExport.exportSiswaToPdf(list, filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal melakukan export PDF: " + e.getMessage());
        }
    }

    // Method untuk mendapatkan ID berikutnya yang akan di-generate
    public int getNextId() {
        String sql = "SELECT MAX(id_siswa) as max_id FROM siswa";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                return maxId + 1;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default jika tabel kosong
    }

}
