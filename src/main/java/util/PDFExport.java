package util;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import model.JadwalModel;
import model.PengajarModel;
import model.SiswaModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/*
*
 * @author raiha
 */
public class PDFExport {

    /**
     * Export data siswa ke PDF
     * 
     * @param listSiswa List data siswa
     * @param filename Nama file output
     */
    public static void exportSiswaToPdf(List<SiswaModel> listSiswa, String filename) {
        if (listSiswa == null || listSiswa.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data siswa untuk diekspor.");
            return;
        }


        File file = new File(filename);
        if (!file.isAbsolute()) {
            // Jika hanya nama file, simpan di direktori project
            file = new File(System.getProperty("user.dir"), filename);
        }
        
        try (PdfWriter writer = new PdfWriter(file);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Judul
            Paragraph title = new Paragraph("LAPORAN DATA SISWA")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Membuat tabel dengan 7 kolom
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2, 2, 3, 2}))
                    .useAllAvailableWidth();

            // Header tabel
            String[] headers = {"ID", "Nama", "Sekolah", "Kelas", "Telepon", "Alamat", "Status"};
            for (String header : headers) {
                Cell headerCell = new Cell()
                        .add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(8);
                table.addHeaderCell(headerCell);
            }

            // Data siswa
            for (SiswaModel siswa : listSiswa) {
                table.addCell(createCell(String.valueOf(siswa.getIdSiswa())));
                table.addCell(createCell(siswa.getNama() != null ? siswa.getNama() : ""));
                table.addCell(createCell(siswa.getSekolah() != null ? siswa.getSekolah() : ""));
                table.addCell(createCell(siswa.getKelas() != null ? siswa.getKelas() : ""));
                table.addCell(createCell(siswa.getTelepon() != null ? siswa.getTelepon() : ""));
                table.addCell(createCell(siswa.getAlamat() != null ? siswa.getAlamat() : ""));
                table.addCell(createCell(siswa.getStatus() != null ? siswa.getStatus() : ""));
            }

            document.add(table);

            // Footer
            Paragraph footer = new Paragraph("Total Data: " + listSiswa.size())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(10);
            document.add(footer);

            JOptionPane.showMessageDialog(null, 
                "Data siswa berhasil diekspor!\n\n" +
                "Lokasi file: " + file.getAbsolutePath() + "\n" +
                "Total data: " + listSiswa.size() + " siswa");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal membuat file PDF: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error: Library iTextPDF tidak ditemukan!\n" +
                "Pastikan dependency iTextPDF 7.2.5 sudah ditambahkan ke project.\n" +
                "Detail: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat export PDF: " + e.getMessage() + "\n" +
                "Pastikan library iTextPDF sudah terinstall dengan benar.");
        }
    }

    /**
     * Export data pengajar ke PDF
     * 
     * @param listPengajar List data pengajar
     * @param filename Nama file output
     */
    public static void exportPengajarToPdf(List<PengajarModel> listPengajar, String filename) {
        if (listPengajar == null || listPengajar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data pengajar untuk diekspor.");
            return;
        }

        // Jika filename tidak ada path, gunakan dialog untuk memilih lokasi
        File file = new File(filename);
        if (!file.isAbsolute()) {
            // Jika hanya nama file, simpan di direktori project
            file = new File(System.getProperty("user.dir"), filename);
        }
        
        try (PdfWriter writer = new PdfWriter(file);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Judul
            Paragraph title = new Paragraph("LAPORAN DATA PENGAJAR")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Membuat tabel dengan 5 kolom
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 3, 2, 3}))
                    .useAllAvailableWidth();

            // Header tabel
            String[] headers = {"ID Pengajar", "Nama", "Spesialisasi", "No Telepon", "Alamat"};
            for (String header : headers) {
                Cell headerCell = new Cell()
                        .add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(8);
                table.addHeaderCell(headerCell);
            }

            // Data pengajar
            for (PengajarModel pengajar : listPengajar) {
                table.addCell(createCell(pengajar.getIdPengajar() != null ? pengajar.getIdPengajar() : ""));
                table.addCell(createCell(pengajar.getNama() != null ? pengajar.getNama() : ""));
                table.addCell(createCell(pengajar.getSpesialisasi() != null ? pengajar.getSpesialisasi() : ""));
                table.addCell(createCell(pengajar.getNoTelepon() != null ? pengajar.getNoTelepon() : ""));
                table.addCell(createCell(pengajar.getAlamat() != null ? pengajar.getAlamat() : ""));
            }

            document.add(table);

            // Footer
            Paragraph footer = new Paragraph("Total Data: " + listPengajar.size())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(10);
            document.add(footer);

            JOptionPane.showMessageDialog(null, 
                "Data pengajar berhasil diekspor!\n\n" +
                "Lokasi file: " + file.getAbsolutePath() + "\n" +
                "Total data: " + listPengajar.size() + " pengajar");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal membuat file PDF: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error: Library iTextPDF tidak ditemukan!\n" +
                "Pastikan dependency iTextPDF 7.2.5 sudah ditambahkan ke project.\n" +
                "Detail: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat export PDF: " + e.getMessage() + "\n" +
                "Pastikan library iTextPDF sudah terinstall dengan benar.");
        }
    }

    /**
     * Export data jadwal ke PDF
     * 
     * @param listJadwal List data jadwal
     * @param filename Nama file output
     */
    public static void exportJadwalToPdf(List<JadwalModel> listJadwal, String filename) {
        if (listJadwal == null || listJadwal.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada data jadwal untuk diekspor.");
            return;
        }

        File file = new File(filename);
        if (!file.isAbsolute()) {
            // Jika hanya nama file, simpan di direktori project
            file = new File(System.getProperty("user.dir"), filename);
        }
        
        try (PdfWriter writer = new PdfWriter(file);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Judul
            Paragraph title = new Paragraph("LAPORAN JADWAL KELAS")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(title);

            // Membuat tabel dengan 7 kolom
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2, 2, 2, 2}))
                    .useAllAvailableWidth();

            // Header tabel
            String[] headers = {"ID", "Mata Pelajaran", "Hari", "Jam Mulai", "Jam Selesai", "Ruangan", "ID Pengajar"};
            for (String header : headers) {
                Cell headerCell = new Cell()
                        .add(new Paragraph(header))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setPadding(8);
                table.addHeaderCell(headerCell);
            }

            // Data jadwal
            for (JadwalModel jadwal : listJadwal) {
                table.addCell(createCell(String.valueOf(jadwal.getIdJadwal())));
                table.addCell(createCell(jadwal.getMataPelajaran() != null ? jadwal.getMataPelajaran() : ""));
                table.addCell(createCell(jadwal.getHari() != null ? jadwal.getHari() : ""));
                table.addCell(createCell(jadwal.getJamMulai() != null ? jadwal.getJamMulai() : ""));
                table.addCell(createCell(jadwal.getJamSelesai() != null ? jadwal.getJamSelesai() : ""));
                table.addCell(createCell(jadwal.getRuangan() != null ? jadwal.getRuangan() : ""));
                table.addCell(createCell(String.valueOf(jadwal.getIdPengajar())));
            }

            document.add(table);

            // Footer
            Paragraph footer = new Paragraph("Total Data: " + listJadwal.size())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(10);
            document.add(footer);

            JOptionPane.showMessageDialog(null, 
                "Data jadwal berhasil diekspor!\n\n" +
                "Lokasi file: " + file.getAbsolutePath() + "\n" +
                "Total data: " + listJadwal.size() + " jadwal");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal membuat file PDF: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error: Library iTextPDF tidak ditemukan!\n" +
                "Pastikan dependency iTextPDF 7.2.5 sudah ditambahkan ke project.\n" +
                "Detail: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Error saat export PDF: " + e.getMessage() + "\n" +
                "Pastikan library iTextPDF sudah terinstall dengan benar.");
        }
    }

    /**
     * Helper method untuk membuat cell dengan padding dan alignment
     * 
     * @param text Teks untuk cell
     * @return Cell object
     */
    private static Cell createCell(String text) {
        return new Cell()
                .add(new Paragraph(text != null ? text : ""))
                .setPadding(5)
                .setTextAlignment(TextAlignment.LEFT);
    }
}
