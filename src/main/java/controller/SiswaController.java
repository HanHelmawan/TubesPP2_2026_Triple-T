package controller;

import model.SiswaModel;
import view.panels.SiswaPanel;

import javax.swing.*;

public class SiswaController {

    private SiswaPanel view;
    // SiswaModel is now a POJO, we don't need it as an instance variable for logic,
    // but the constructor signature might need change or we just ignore the model
    // param.
    // However, existing MainFrame passes it. We'll keep the signature but use
    // internal logic.

    public SiswaController(SiswaPanel view, SiswaModel model) {
        this.view = view;
        // this.model = model; // No longer needed for logic

        view.btnTambah.addActionListener(e -> tambahData());
        view.btnReset.addActionListener(e -> resetForm());

        loadTable();
    }

    private void tambahData() {
        String nama = view.txtNama.getText();
        String telp = view.txtTelepon.getText();

        if (nama.isEmpty() || telp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nama & Telepon wajib diisi!");
            return;
        }

        if (!telp.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "Telepon harus angka!");
            return;
        }

        SiswaModel siswa = new SiswaModel(
                view.txtNama.getText(),
                view.txtSekolah.getText(),
                view.txtKelas.getText(),
                view.txtAlamat.getText(),
                view.txtTelepon.getText(),
                view.cbStatus.getSelectedItem().toString());

        insertSiswa(siswa);

        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        loadTable();
        resetForm();
    }

    // SQL Methods moved from Model to Controller
    public void insertSiswa(SiswaModel siswa) {
        String sql = "INSERT INTO siswa (nama_siswa, asal_sekolah, kelas, alamat, no_telepon, status_aktif) VALUES (?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = config.DBConnections.getConnection();
                java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, siswa.getNama());
            pstmt.setString(2, siswa.getSekolah());
            pstmt.setString(3, siswa.getKelas());
            pstmt.setString(4, siswa.getAlamat());
            pstmt.setString(5, siswa.getTelepon());
            pstmt.setString(6, siswa.getStatus());
            pstmt.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menambahkan siswa: " + e.getMessage());
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
                        rs.getString("status_aktif")));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data siswa: " + e.getMessage());
        }
        return list;
    }

    public javax.swing.table.DefaultTableModel getTableData() {
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nama");
        tableModel.addColumn("Sekolah");
        tableModel.addColumn("Kelas");
        tableModel.addColumn("Alamat");
        tableModel.addColumn("Telepon");
        tableModel.addColumn("Status");

        java.util.List<SiswaModel> list = getAllSiswa();
        for (SiswaModel s : list) {
            tableModel.addRow(new Object[] {
                    s.getIdSiswa(),
                    s.getNama(),
                    s.getSekolah(),
                    s.getKelas(),
                    s.getAlamat(),
                    s.getTelepon(),
                    s.getStatus()
            });
        }
        return tableModel;
    }

    private void loadTable() {
        view.setTable(getTableData());
    }

    private void resetForm() {
        view.txtNama.setText("");
        view.txtSekolah.setText("");
        view.txtKelas.setText("");
        view.txtTelepon.setText("");
        view.txtAlamat.setText("");
        view.cbStatus.setSelectedIndex(0);
    }

}
