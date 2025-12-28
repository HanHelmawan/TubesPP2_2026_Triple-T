package controller;

import model.SiswaModel;
import view.panels.SiswaPanel;

import javax.swing.*;
import java.awt.event.*;

public class SiswaController {

    private SiswaPanel view;
    private SiswaModel model;

    public SiswaController(SiswaPanel view, SiswaModel model) {
        this.view = view;
        this.model = model;

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

        model.insert(
            view.txtNama.getText(),
            view.txtSekolah.getText(),
            view.txtKelas.getText(),
            view.txtAlamat.getText(),
            view.txtTelepon.getText(),
            view.cbStatus.getSelectedItem().toString()
        );

        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        loadTable();
        resetForm();
    }

    private void loadTable() {
        view.setTable(model.getData());
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
