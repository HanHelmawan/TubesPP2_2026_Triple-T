package view.panels;

import controller.JadwalController;
import model.JadwalModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JadwalPanel extends JPanel {
    // Field input sesuai model dan database
    private JTextField tfIdJadwal = new JTextField(20);
    private JTextField tfMataPelajaran = new JTextField(20);
    private JTextField tfHari = new JTextField(20);
    private JTextField tfJamMulai = new JTextField(20);
    private JTextField tfJamSelesai = new JTextField(20);
    private JTextField tfRuangan = new JTextField(20);
    private JTextField tfIdPengajar = new JTextField(20);
    private JTable tableJadwal;
    private JadwalController controller;

    public JadwalPanel() {
        controller = new JadwalController();

        // setTitle("Manajemen Jadwal Kelas"); // Removed: JPanel doesn't have title
        // setSize(1000, 700); // Removed: Layout manager usually handles this
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("ID Jadwal (Otomatis):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIdJadwal, gbc);
        tfIdJadwal.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Mata Pelajaran:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfMataPelajaran, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Hari:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfHari, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Jam Mulai (HH:mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfJamMulai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Jam Selesai (HH:mm):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfJamSelesai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Ruangan:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfRuangan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(new JLabel("ID Pengajar:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIdPengajar, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validasiInput()) {
                    JadwalModel jadwal = new JadwalModel(
                            tfMataPelajaran.getText(),
                            tfHari.getText(),
                            tfJamMulai.getText(), // Asumsikan format HH:mm
                            tfJamSelesai.getText(), // Asumsikan format HH:mm
                            tfRuangan.getText(),
                            Integer.parseInt(tfIdPengajar.getText()) // Parse ID Pengajar
                    );
                    controller.tambahJadwal(jadwal);
                    // Update ID field dengan ID yang baru saja di-generate
                    tfIdJadwal.setText(String.valueOf(jadwal.getIdJadwal()));
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
                }
            }
        });

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            String idStr = tfIdJadwal.getText();
            if (!idStr.isEmpty() && idStr.matches("\\d+")) {
                if (validasiInput()) {
                    JadwalModel jadwal = new JadwalModel(
                            Integer.parseInt(idStr), // Parse ID Jadwal
                            tfMataPelajaran.getText(),
                            tfHari.getText(),
                            tfJamMulai.getText(),
                            tfJamSelesai.getText(),
                            tfRuangan.getText(),
                            Integer.parseInt(tfIdPengajar.getText()));
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
            String idStr = tfIdJadwal.getText();
            if (!idStr.isEmpty() && idStr.matches("\\d+")) {
                int id = Integer.parseInt(idStr);
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data dengan ID: " + id + "?",
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
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

        // add(inputPanel, BorderLayout.NORTH);
        JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputWrapper.add(inputPanel);
        add(inputWrapper, BorderLayout.NORTH);

        // Styling Buttons
        Dimension btnSize = new Dimension(120, 40);
        Font btnFont = new Font("SansSerif", Font.BOLD, 14);

        JButton[] buttons = { btnSimpan, btnUpdate, btnHapus, btnExport };
        for (JButton btn : buttons) {
            btn.setPreferredSize(btnSize);
            btn.setFont(btnFont);
        }

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnExport);

        add(buttonPanel, BorderLayout.SOUTH);

        // Menampilkan ID berikutnya saat panel pertama kali dimuat
        tfIdJadwal.setText(String.valueOf(controller.getNextId()));

        // Header tabel sesuai database
        String[] kolom = { "ID", "Mata Pelajaran", "Hari", "Jam Mulai", "Jam Selesai", "Ruangan", "ID Pengajar" };
        tableJadwal = new JTable(controller.getTableData(), kolom);
        JScrollPane scrollPane = new JScrollPane(tableJadwal);

        tableJadwal.getSelectionModel().addListSelectionListener(e -> {
            if (!tableJadwal.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = tableJadwal.getSelectedRow();
                tfIdJadwal.setText(tableJadwal.getValueAt(selectedRow, 0).toString());
                tfMataPelajaran.setText(tableJadwal.getValueAt(selectedRow, 1).toString());
                tfHari.setText(tableJadwal.getValueAt(selectedRow, 2).toString());
                tfJamMulai.setText(tableJadwal.getValueAt(selectedRow, 3).toString());
                tfJamSelesai.setText(tableJadwal.getValueAt(selectedRow, 4).toString());
                tfRuangan.setText(tableJadwal.getValueAt(selectedRow, 5).toString());
                tfIdPengajar.setText(tableJadwal.getValueAt(selectedRow, 6).toString());
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private boolean validasiInput() {
        String mapel = tfMataPelajaran.getText().trim();
        String hari = tfHari.getText().trim();
        String jamMulai = tfJamMulai.getText().trim();
        String jamSelesai = tfJamSelesai.getText().trim();
        String ruangan = tfRuangan.getText().trim();
        String idPengajarStr = tfIdPengajar.getText().trim();

        if (mapel.isEmpty() || hari.isEmpty() || jamMulai.isEmpty() || jamSelesai.isEmpty() || ruangan.isEmpty()
                || idPengajarStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi.");
            return false;
        }

        // Validasi format jam sederhana
        if (!jamMulai.matches("\\d{2}:\\d{2}") || !jamSelesai.matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Format jam harus HH:mm (contoh: 08:00).");
            return false;
        }

        // Validasi logika jam (jam selesai harus setelah jam mulai)
        if (jamMulai.compareTo(jamSelesai) >= 0) {
            JOptionPane.showMessageDialog(this, "Jam selesai harus setelah jam mulai.");
            return false;
        }

        // Validasi ID Pengajar adalah angka
        try {
            Integer.parseInt(idPengajarStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID Pengajar harus berupa angka.");
            return false;
        }

        return true;
    }

    private void clearFields() {
        // Menampilkan ID berikutnya yang akan di-generate
        tfIdJadwal.setText(String.valueOf(controller.getNextId()));
        tfMataPelajaran.setText("");
        tfHari.setText("");
        tfJamMulai.setText("");
        tfJamSelesai.setText("");
        tfRuangan.setText("");
        tfIdPengajar.setText("");
    }

    private void refreshTable() {
        tableJadwal.setModel(new DefaultTableModel(
                controller.getTableData(),
                new String[] { "ID", "Mata Pelajaran", "Hari", "Jam Mulai", "Jam Selesai", "Ruangan", "ID Pengajar" }));
    }
}