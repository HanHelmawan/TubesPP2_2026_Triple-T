package view.panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class HomePanel extends JPanel {

    public HomePanel(Consumer<String> nav) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Sistem Manajemen Jadwal Bimbel", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitle, gbc);

        // Subtitle / Instruction
        JLabel lblDesc = new JLabel("Silakan ikuti alur kerja berikut:", SwingConstants.CENTER);
        gbc.gridy = 1;
        add(lblDesc, gbc);

        // Button 1: Pengajar
        JButton btnPengajar = new JButton("1. Kelola Data Pengajar");
        btnPengajar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnPengajar.setPreferredSize(new Dimension(250, 40));
        btnPengajar.addActionListener(e -> nav.accept("Pengajar"));
        gbc.gridy = 2;
        add(btnPengajar, gbc);

        // Button 2: Jadwal
        JButton btnJadwal = new JButton("2. Kelola Data Jadwal");
        btnJadwal.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnJadwal.setPreferredSize(new Dimension(250, 40));
        btnJadwal.addActionListener(e -> nav.accept("Jadwal"));
        gbc.gridy = 3;
        add(btnJadwal, gbc);

        // Button 3: Siswa
        JButton btnSiswa = new JButton("3. Kelola Data Siswa");
        btnSiswa.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnSiswa.setPreferredSize(new Dimension(250, 40));
        btnSiswa.addActionListener(e -> nav.accept("Siswa"));
        gbc.gridy = 4;
        add(btnSiswa, gbc);
    }
}
