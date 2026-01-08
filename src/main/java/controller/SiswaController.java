package controller;

import model.SiswaModel;
import util.PDFExport;

import javax.swing.*;

public class SiswaController {

    public SiswaController() {
    }

    public void tambahSiswa(SiswaModel siswa) {
        // Cari ID kosong (gap) atau ID berikutnya
        int newId = getNextId();
        siswa.setIdSiswa(newId);

        String sql = "INSERT INTO siswa (id_siswa, nama_siswa, asal_sekolah, kelas, alamat, no_telepon, status_aktif) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newId);
            pstmt.setString(2, siswa.getNama());
            pstmt.setString(3, siswa.getSekolah());
            pstmt.setString(4, siswa.getKelas());
            pstmt.setString(5, siswa.getAlamat());
            pstmt.setString(6, siswa.getTelepon());
            pstmt.setString(7, siswa.getStatus());
            pstmt.executeUpdate();

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
    // Method untuk mendapatkan ID Kosong (Gap) berikutnya
    public int getNextId() {
        String sql = "SELECT id_siswa FROM siswa ORDER BY id_siswa ASC";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery(sql)) {
            int expectedId = 1;
            while (rs.next()) {
                int currentId = rs.getInt("id_siswa");
                if (currentId != expectedId) {
                    return expectedId; // Menemukan celah (misal ada 1, 3 -> return 2)
                }
                expectedId++;
            }
            return expectedId; // Tidak ada celah, return max + 1
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default jika tabel kosong
    }

    public java.util.List<SiswaModel> searchSiswa(String keyword) {
        java.util.List<SiswaModel> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM siswa WHERE nama_siswa LIKE ? OR asal_sekolah LIKE ?";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SiswaModel s = new SiswaModel(
                            rs.getString("nama_siswa"),
                            rs.getString("asal_sekolah"),
                            rs.getString("kelas"),
                            rs.getString("alamat"),
                            rs.getString("no_telepon"),
                            rs.getString("status_aktif") == null ? "Non-Aktif" : rs.getString("status_aktif"));
                    s.setIdSiswa(rs.getInt("id_siswa"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
