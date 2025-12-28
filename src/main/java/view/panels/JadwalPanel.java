package view.panels;

import controller.JadwalController;
import model.JadwalModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JadwalPanel extends JFrame {
    // --> Perbesar ukuran JTextField agar sejajar
    private JTextField tfIdJadwal = new JTextField(15);
    private JTextField tfIdPengajar = new JTextField(15);
    private JTextField tfIdMapel = new JTextField(15);
    private JTextField tfHari = new JTextField(15);
    private JTextField tfJamMulai = new JTextField(15); // Untuk format jam
    private JTextField tfJamSelesai = new JTextField(15); // Untuk format jam
    // <--
    private JTable tableJadwal;
    private JadwalController controller;

    public JadwalPanel() {
        controller = new JadwalController();

        setTitle("Manajemen Jadwal Kelas");
        setSize(950, 700); // Sesuaikan ukuran window
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Jadwal:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIdJadwal, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("ID Pengajar:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIdPengajar, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("ID Mapel:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIdMapel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Hari:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfHari, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Jam Mulai (HH:mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfJamMulai, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        inputPanel.add(new JLabel("Jam Selesai (HH:mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfJamSelesai, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validasiInput()) {
                    JadwalModel jadwal = new JadwalModel(
                        tfIdJadwal.getText(),
                        tfIdPengajar.getText(),
                        tfIdMapel.getText(),
                        tfHari.getText(),
                        tfJamMulai.getText(),
                        tfJamSelesai.getText()
                    );
                    controller.tambahJadwal(jadwal);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
                }
            }
        });

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            if (!tfIdJadwal.getText().isEmpty()) {
                if (validasiInput()) {
                    JadwalModel jadwal = new JadwalModel(
                        tfIdJadwal.getText(),
                        tfIdPengajar.getText(),
                        tfIdMapel.getText(),
                        tfHari.getText(),
                        tfJamMulai.getText(),
                        tfJamSelesai.getText()
                    );
                    controller.updateJadwal(jadwal);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Data berhasil diupdate.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan diupdate dari tabel.");
            }
        });

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> {
            String id = tfIdJadwal.getText();
            if (!id.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data dengan ID: " + id + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteJadwal(id);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan dihapus dari tabel.");
            }
        });

        JButton btnExport = new JButton("Export PDF");
        btnExport.addActionListener(e -> controller.exportToPdf());

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnExport);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] kolom = {"ID Jadwal", "ID Pengajar", "ID Mapel", "Hari", "Jam Mulai", "Jam Selesai"};
        tableJadwal = new JTable(controller.getTableData(), kolom);
        JScrollPane scrollPane = new JScrollPane(tableJadwal);

        tableJadwal.getSelectionModel().addListSelectionListener(e -> {
            if (!tableJadwal.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = tableJadwal.getSelectedRow();
                tfIdJadwal.setText(tableJadwal.getValueAt(selectedRow, 0).toString());
                tfIdPengajar.setText(tableJadwal.getValueAt(selectedRow, 1).toString());
                tfIdMapel.setText(tableJadwal.getValueAt(selectedRow, 2).toString());
                tfHari.setText(tableJadwal.getValueAt(selectedRow, 3).toString());
                tfJamMulai.setText(tableJadwal.getValueAt(selectedRow, 4).toString());
                tfJamSelesai.setText(tableJadwal.getValueAt(selectedRow, 5).toString());
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private boolean validasiInput() {
        String id = tfIdJadwal.getText().trim();
        String idPengajar = tfIdPengajar.getText().trim();
        String idMapel = tfIdMapel.getText().trim();
        String hari = tfHari.getText().trim();
        String jamMulai = tfJamMulai.getText().trim();
        String jamSelesai = tfJamSelesai.getText().trim();

        if (id.isEmpty() || idPengajar.isEmpty() || idMapel.isEmpty() || hari.isEmpty() || jamMulai.isEmpty() || jamSelesai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.");
            return false;
        }

        // Validasi format jam (sederhana)
        if (!jamMulai.matches("\\d{2}:\\d{2}") || !jamSelesai.matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Format jam harus HH:mm (contoh: 08:00).");
            return false;
        }

        // Validasi logika jam (jam selesai harus setelah jam mulai)
        if (jamMulai.compareTo(jamSelesai) >= 0) {
            JOptionPane.showMessageDialog(this, "Jam selesai harus setelah jam mulai.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        tfIdJadwal.setText("");
        tfIdPengajar.setText("");
        tfIdMapel.setText("");
        tfHari.setText("");
        tfJamMulai.setText("");
        tfJamSelesai.setText("");
    }

    private void refreshTable() {
        tableJadwal.setModel(new DefaultTableModel(
            controller.getTableData(),
            new String[]{"ID Jadwal", "ID Pengajar", "ID Mapel", "Hari", "Jam Mulai", "Jam Selesai"}
        ));
    }
}