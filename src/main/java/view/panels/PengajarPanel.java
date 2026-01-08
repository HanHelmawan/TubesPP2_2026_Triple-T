package view.panels;

import controller.PengajarController;
import model.PengajarModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PengajarPanel extends JPanel {
    private JTextField tfIdPengajar = new JTextField(20);
    private JTextField tfNama = new JTextField(20);
    private JTextField tfSpesialisasi = new JTextField(20); // Renamed from tfBidang
    private JTextField tfNoTelepon = new JTextField(15);
    private JTextField tfAlamat = new JTextField(30);
    private JTable tablePengajar;
    private PengajarController controller;

    public PengajarPanel() {
        controller = new PengajarController();
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID Pengajar (Read Only / Auto) - Hidden
        // gbc.gridx = 0; gbc.gridy = 0;
        // inputPanel.add(new JLabel("ID Pengajar:"), gbc);
        // gbc.gridx = 1;
        // inputPanel.add(tfIdPengajar, gbc);
        tfIdPengajar.setEditable(false);

        // Nama
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Nama Pengajar:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfNama, gbc);

        // Spesialisasi
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Spesialisasi:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfSpesialisasi, gbc);

        // No Telepon
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("No Telepon:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfNoTelepon, gbc);

        // Alamat
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(tfAlamat, gbc);

        JPanel buttonPanel = new JPanel();
        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.addActionListener(e -> {
            if (validasiInput()) {
                // Use constructor without ID for insert
                PengajarModel pengajar = new PengajarModel(
                        tfNama.getText(),
                        tfSpesialisasi.getText(),
                        tfNoTelepon.getText(),
                        tfAlamat.getText());
                controller.tambahPengajar(pengajar);
                // Update ID field dengan ID yang baru saja di-generate
                tfIdPengajar.setText(pengajar.getIdPengajar());
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
            }
        });

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            if (validasiInput() && !tfIdPengajar.getText().isEmpty()) {
                PengajarModel pengajar = new PengajarModel(
                        tfIdPengajar.getText(),
                        tfNama.getText(),
                        tfSpesialisasi.getText(),
                        tfNoTelepon.getText(),
                        tfAlamat.getText());
                controller.updatePengajar(pengajar);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate.");
            } else if (tfIdPengajar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Pilih data dari tabel untuk diupdate.");
            }
        });

        JButton btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(e -> {
            String id = tfIdPengajar.getText();
            if (!id.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus data dengan ID: " + id + "?",
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deletePengajar(id);
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
        tfIdPengajar.setText(controller.getNextId());

        String[] kolom = { "ID Pengajar", "Nama", "Spesialisasi", "No Telepon", "Alamat" };
        tablePengajar = new JTable(controller.getTableData(), kolom);
        JScrollPane scrollPane = new JScrollPane(tablePengajar);

        tablePengajar.getSelectionModel().addListSelectionListener(e -> {
            if (!tablePengajar.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = tablePengajar.getSelectedRow();
                tfIdPengajar.setText(tablePengajar.getValueAt(selectedRow, 0).toString());
                tfNama.setText(tablePengajar.getValueAt(selectedRow, 1).toString());
                tfSpesialisasi.setText(tablePengajar.getValueAt(selectedRow, 2).toString());
                tfNoTelepon.setText(tablePengajar.getValueAt(selectedRow, 3) != null
                        ? tablePengajar.getValueAt(selectedRow, 3).toString()
                        : "");
                tfAlamat.setText(tablePengajar.getValueAt(selectedRow, 4) != null
                        ? tablePengajar.getValueAt(selectedRow, 4).toString()
                        : "");
            }
        });

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Cari");
        searchPanel.add(new JLabel("Cari Nama/Spesialisasi:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText();
            java.util.List<PengajarModel> list = controller.searchPengajar(keyword);
            String[] cols = { "ID Pengajar", "Nama", "Spesialisasi", "No Telepon", "Alamat" };
            Object[][] data = new Object[list.size()][5];
            for (int i = 0; i < list.size(); i++) {
                PengajarModel p = list.get(i);
                data[i][0] = p.getIdPengajar();
                data[i][1] = p.getNama();
                data[i][2] = p.getSpesialisasi();
                data[i][3] = p.getNoTelepon();
                data[i][4] = p.getAlamat();
            }
            tablePengajar.setModel(new DefaultTableModel(data, cols));
        });

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.add(searchPanel, BorderLayout.NORTH);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);
        add(tableWrapper, BorderLayout.CENTER);
    }

    private boolean validasiInput() {
        if (tfNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama wajib diisi.");
            return false;
        }
        return true;
    }

    private void clearFields() {
        // Menampilkan ID berikutnya yang akan di-generate
        tfIdPengajar.setText(controller.getNextId());
        tfNama.setText("");
        tfSpesialisasi.setText("");
        tfNoTelepon.setText("");
        tfAlamat.setText("");
    }

    private void refreshTable() {
        tablePengajar.setModel(new DefaultTableModel(
                controller.getTableData(),
                new String[] { "ID Pengajar", "Nama", "Spesialisasi", "No Telepon", "Alamat" }));
    }
}
