package view.panels;

import controller.SiswaController;
import model.SiswaModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SiswaPanel extends JPanel {

    private JTextField txtNama = new JTextField(20);
    private JTextField txtSekolah = new JTextField(20);
    private JTextField txtKelas = new JTextField(20);
    private JTextField txtTelepon = new JTextField(20);
    private JTextField txtAlamat = new JTextField(20);
    private JComboBox<String> cbStatus = new JComboBox<>(new String[] { "Aktif", "Non-Aktif" });
    private JTextField txtIdSiswa = new JTextField(20); // Hidden or Read-Only for ID

    private JTable tableSiswa;
    private SiswaController controller;

    public SiswaPanel() {
        controller = new SiswaController();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID Siswa
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Siswa (Auto):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtIdSiswa, gbc);
        txtIdSiswa.setEditable(false);

        // Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNama, gbc);

        // Sekolah
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Sekolah:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtSekolah, gbc);

        // Kelas
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Kelas:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtKelas, gbc);

        // Telepon
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("No Telepon:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTelepon, gbc);

        // Alamat
        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(txtAlamat, gbc);

        // Status
        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(cbStatus, gbc);

        JPanel buttonPanel = new JPanel();

        // STANDARD BUTTONS: Simpan, Update, Hapus, Export PDF
        JButton btnSimpan = new JButton("Simpan"); // Renamed from Tambah
        btnSimpan.addActionListener(e -> tambahData());

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> updateData());

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> deleteData());

        JButton btnExport = new JButton("Export PDF");
        btnExport.addActionListener(e -> controller.exportToPdf());

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnExport);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Menampilkan ID berikutnya saat panel pertama kali dimuat
        txtIdSiswa.setText(String.valueOf(controller.getNextId()));

        String[] kolom = { "ID", "Nama", "Sekolah", "Kelas", "Telepon", "Alamat", "Status" };
        tableSiswa = new JTable(new DefaultTableModel(controller.getTableData(), kolom));
        JScrollPane scrollPane = new JScrollPane(tableSiswa);

        tableSiswa.getSelectionModel().addListSelectionListener(e -> {
            if (!tableSiswa.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = tableSiswa.getSelectedRow();
                txtIdSiswa.setText(tableSiswa.getValueAt(selectedRow, 0).toString());
                txtNama.setText(tableSiswa.getValueAt(selectedRow, 1).toString());
                txtSekolah.setText(tableSiswa.getValueAt(selectedRow, 2).toString());
                txtKelas.setText(tableSiswa.getValueAt(selectedRow, 3).toString());
                txtTelepon.setText(tableSiswa.getValueAt(selectedRow, 4).toString());
                txtAlamat.setText(tableSiswa.getValueAt(selectedRow, 5).toString());
                cbStatus.setSelectedItem(tableSiswa.getValueAt(selectedRow, 6).toString());
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private void tambahData() {
        if (validasiInput()) {
            SiswaModel siswa = new SiswaModel(
                    txtNama.getText(),
                    txtSekolah.getText(),
                    txtKelas.getText(),
                    txtAlamat.getText(),
                    txtTelepon.getText(),
                    cbStatus.getSelectedItem().toString());
            controller.tambahSiswa(siswa);
            // Update ID field dengan ID yang baru saja di-generate
            txtIdSiswa.setText(String.valueOf(siswa.getIdSiswa()));
            refreshTable();
            resetForm();
        }
    }

    private void updateData() {
        if (!txtIdSiswa.getText().isEmpty() && validasiInput()) {
            SiswaModel siswa = new SiswaModel(
                    Integer.parseInt(txtIdSiswa.getText()),
                    txtNama.getText(),
                    txtSekolah.getText(),
                    txtKelas.getText(),
                    txtAlamat.getText(),
                    txtTelepon.getText(),
                    cbStatus.getSelectedItem().toString());
            controller.updateSiswa(siswa);
            refreshTable();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate.");
        }
    }

    private void deleteData() {
        if (!txtIdSiswa.getText().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus data ini?", "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.deleteSiswa(Integer.parseInt(txtIdSiswa.getText()));
                refreshTable();
                resetForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus.");
        }
    }

    private void refreshTable() {
        String[] kolom = { "ID", "Nama", "Sekolah", "Kelas", "Telepon", "Alamat", "Status" };
        tableSiswa.setModel(new DefaultTableModel(controller.getTableData(), kolom));
    }

    private void resetForm() {
        // Menampilkan ID berikutnya yang akan di-generate
        txtIdSiswa.setText(String.valueOf(controller.getNextId()));
        txtNama.setText("");
        txtSekolah.setText("");
        txtKelas.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        cbStatus.setSelectedIndex(0);
        tableSiswa.clearSelection();
    }

    private boolean validasiInput() {
        if (txtNama.getText().isEmpty() || txtTelepon.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan Telepon wajib diisi!");
            return false;
        }
        if (!txtTelepon.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Telepon harus angka!");
            return false;
        }
        return true;
    }
}
