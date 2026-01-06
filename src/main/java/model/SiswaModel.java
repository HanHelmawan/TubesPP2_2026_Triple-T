package model;

import java.sql.*;
import javax.swing.table.DefaultTableModel;
import config.DBConnections;

public class SiswaModel {

    public void insert(String nama, String sekolah, String kelas, 
                       String alamat, String telepon, String status) {
        try {
            Connection conn = DBConnections.getConnection();
            String sql = "INSERT INTO siswa (nama_siswa, asal_sekolah, kelas, alamat, no_telepon, status_aktif) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nama);
            ps.setString(2, sekolah);
            ps.setString(3, kelas);
            ps.setString(4, alamat);
            ps.setString(5, telepon);
            ps.setString(6, status);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel getData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Sekolah");
        model.addColumn("Kelas");
        model.addColumn("Telepon");
        model.addColumn("Status");

        try {
            Connection conn = DBConnections.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM siswa");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_siswa"),
                    rs.getString("nama_siswa"),
                    rs.getString("asal_sekolah"),
                    rs.getString("kelas"),
                    rs.getString("no_telepon"),
                    rs.getString("status_aktif")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}
