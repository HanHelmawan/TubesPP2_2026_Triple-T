package view.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SiswaPanel extends JPanel {

    public JTextField txtNama, txtSekolah, txtKelas, txtTelepon;
    public JTextArea txtAlamat;
    public JComboBox<String> cbStatus;
    public JButton btnTambah, btnReset;
    public JTable table;

    public SiswaPanel() {
        setLayout(null);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(20, 20, 100, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(120, 20, 200, 25);
        add(txtNama);

        JLabel lblSekolah = new JLabel("Sekolah");
        lblSekolah.setBounds(20, 60, 100, 25);
        add(lblSekolah);

        txtSekolah = new JTextField();
        txtSekolah.setBounds(120, 60, 200, 25);
        add(txtSekolah);

        JLabel lblKelas = new JLabel("Kelas");
        lblKelas.setBounds(20, 100, 100, 25);
        add(lblKelas);

        txtKelas = new JTextField();
        txtKelas.setBounds(120, 100, 200, 25);
        add(txtKelas);

        JLabel lblTelepon = new JLabel("No Telepon");
        lblTelepon.setBounds(20, 140, 100, 25);
        add(lblTelepon);

        txtTelepon = new JTextField();
        txtTelepon.setBounds(120, 140, 200, 25);
        add(txtTelepon);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setBounds(20, 180, 100, 25);
        add(lblStatus);

        cbStatus = new JComboBox<>(new String[]{"Aktif", "Non-Aktif"});
        cbStatus.setBounds(120, 180, 200, 25);
        add(cbStatus);

        btnTambah = new JButton("Tambah");
        btnTambah.setBounds(120, 220, 90, 30);
        add(btnTambah);

        btnReset = new JButton("Reset");
        btnReset.setBounds(230, 220, 90, 30);
        add(btnReset);

        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(350, 20, 500, 230);
        add(sp);
    }

    public void setTable(DefaultTableModel model) {
        table.setModel(model);
    }
}
