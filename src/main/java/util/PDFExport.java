/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import model.JadwalModel;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.UnitValue;
import javax.swing.JOptionPane;
import java.util.List;

public class PDFExport {
    public static void exportJadwalToPdf(List<JadwalModel> list, String filename) {
        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Daftar Jadwal Kelas Bimbel").setFontSize(18).setBold());
            document.add(new Paragraph(" ")); // Jarak

            // Buat tabel dengan 6 kolom
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Header Tabel
            table.addHeaderCell(new Cell().add(new Paragraph("ID Jadwal").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("ID Pengajar").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("ID Mapel").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Hari").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Jam Mulai").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Jam Selesai").setBold()));

            // Isi Tabel
            for (JadwalModel j : list) {
                table.addCell(new Cell().add(new Paragraph(j.getIdJadwal())));
                table.addCell(new Cell().add(new Paragraph(j.getIdPengajar())));
                table.addCell(new Cell().add(new Paragraph(j.getIdMapel())));
                table.addCell(new Cell().add(new Paragraph(j.getHari())));
                table.addCell(new Cell().add(new Paragraph(j.getJamMulai())));
                table.addCell(new Cell().add(new Paragraph(j.getJamSelesai())));
            }

            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null, "PDF berhasil diekspor ke: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengekspor PDF: " + e.getMessage());
        }
    }
}