package util;

import model.JadwalModel;
import model.PengajarModel;
import model.SiswaModel;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.List;

/**
 * Utility class for exporting data using native Java Printing API via HTML.
 * This method is more robust for generating reports than printing an invisible
 * JTable.
 */
public class PDFExport {

    public static void exportSiswaToPdf(List<SiswaModel> listSiswa, String filename) {
        if (listSiswa == null || listSiswa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data siswa untuk diekspor.");
            return;
        }

        StringBuilder html = startHtml("Laporan Data Siswa");
        html.append("<tr>")
                .append("<th>ID</th><th>Nama</th><th>Sekolah</th><th>Kelas</th><th>Telepon</th><th>Alamat</th><th>Status</th>")
                .append("</tr>");

        for (SiswaModel s : listSiswa) {
            html.append("<tr>");
            html.append("<td>").append(s.getIdSiswa()).append("</td>");
            html.append("<td>").append(safe(s.getNama())).append("</td>");
            html.append("<td>").append(safe(s.getSekolah())).append("</td>");
            html.append("<td>").append(safe(s.getKelas())).append("</td>");
            html.append("<td>").append(safe(s.getTelepon())).append("</td>");
            html.append("<td>").append(safe(s.getAlamat())).append("</td>");
            html.append("<td>").append(safe(s.getStatus())).append("</td>");
            html.append("</tr>");
        }

        endHtml(html);
        printHtml(html.toString());
    }

    public static void exportPengajarToPdf(List<PengajarModel> listPengajar, String filename) {
        if (listPengajar == null || listPengajar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data pengajar untuk diekspor.");
            return;
        }

        StringBuilder html = startHtml("Laporan Data Pengajar");
        html.append("<tr>")
                .append("<th>ID</th><th>Nama</th><th>Spesialisasi</th><th>Telepon</th><th>Alamat</th>")
                .append("</tr>");

        for (PengajarModel p : listPengajar) {
            html.append("<tr>");
            html.append("<td>").append(safe(p.getIdPengajar())).append("</td>");
            html.append("<td>").append(safe(p.getNama())).append("</td>");
            html.append("<td>").append(safe(p.getSpesialisasi())).append("</td>");
            html.append("<td>").append(safe(p.getNoTelepon())).append("</td>");
            html.append("<td>").append(safe(p.getAlamat())).append("</td>");
            html.append("</tr>");
        }

        endHtml(html);
        printHtml(html.toString());
    }

    public static void exportJadwalToPdf(List<JadwalModel> listJadwal, String filename) {
        if (listJadwal == null || listJadwal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data jadwal untuk diekspor.");
            return;
        }

        StringBuilder html = startHtml("Laporan Data Jadwal");
        html.append("<tr>")
                .append("<th>ID</th><th>Mapel</th><th>Hari</th><th>Jam Mulai</th><th>Jam Selesai</th><th>Ruangan</th><th>ID Pengajar</th>")
                .append("</tr>");

        for (JadwalModel j : listJadwal) {
            html.append("<tr>");
            html.append("<td>").append(j.getIdJadwal()).append("</td>");
            html.append("<td>").append(safe(j.getMataPelajaran())).append("</td>");
            html.append("<td>").append(safe(j.getHari())).append("</td>");
            html.append("<td>").append(safe(j.getJamMulai())).append("</td>");
            html.append("<td>").append(safe(j.getJamSelesai())).append("</td>");
            html.append("<td>").append(safe(j.getRuangan())).append("</td>");
            html.append("<td>").append(j.getIdPengajar()).append("</td>");
            html.append("</tr>");
        }

        endHtml(html);
        printHtml(html.toString());
    }

    // --- Helper Methods ---

    private static StringBuilder startHtml(String title) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><style>");
        sb.append("body { font-family: sans-serif; }");
        sb.append("table { border-collapse: collapse; width: 100%; }");
        sb.append("th { background-color: #e0e0e0; border: 1px solid black; padding: 5px; }");
        sb.append("td { border: 1px solid black; padding: 5px; font-size: 10px; }");
        sb.append("h2 { text-align: center; }");
        sb.append("</style></head><body>");
        sb.append("<h2>").append(title).append("</h2>");
        sb.append("<table border='1' cellpadding='4' cellspacing='0'>"); // inline attributes for HTML3.2 compatibility
        return sb;
    }

    private static void endHtml(StringBuilder sb) {
        sb.append("</table></body></html>");
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static void printHtml(String htmlContent) {
        // Run on EDT
        SwingUtilities.invokeLater(() -> {
            JTextPane textPane = new JTextPane();
            textPane.setContentType("text/html");
            textPane.setText(htmlContent);

            try {
                // Show print dialog
                boolean done = textPane.print(new MessageFormat("{0}"), new MessageFormat("Halaman {0}"));
                if (done) {
                    JOptionPane.showMessageDialog(null, "Export selesai!");
                }
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal mencetak: " + e.getMessage());
            }
        });
    }
}
