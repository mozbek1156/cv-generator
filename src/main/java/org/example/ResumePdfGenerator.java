package org.example;

//1- Kütüphaneler
// 2-iText kütüphaneleri

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

//3- java stanadart kütüphaneleri
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

// 4- pdf oluşturma
public class ResumePdfGenerator {

    private static Font TITLE_FONT;
    private static Font HEADER_FONT;
    private static Font NORMAL_FONT;
    private static Font SMALL_FONT;

    public static void main(String[] args) {
        try {
            createCv("ozgecmis.pdf");
            System.out.println("PDF başarıyla oluşturuldu: ozgecmis.pdf");
        } catch (Exception e) {
            System.err.println("Hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //5- font ayarları
    private static void initFonts() throws IOException, DocumentException {
        // Türkçe karakterler için gömülü TTF fontu (resources/fonts/NotoSans-Regular.ttf)
        try (InputStream fontStream = getResource("/fonts/NotoSans-Regular.ttf")) {
            if (fontStream == null) {
                throw new IOException("Font bulunamadı. 'src/main/resources/fonts/NotoSans-Regular.ttf' ekleyin.");
            }
            byte[] fontBytes = fontStream.readAllBytes();
            BaseFont base = BaseFont.createFont("NotoSans-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, false, fontBytes, null);

            TITLE_FONT = new Font(base, 20, Font.BOLD, BaseColor.DARK_GRAY);
            HEADER_FONT = new Font(base, 14, Font.BOLD, BaseColor.BLACK);
            NORMAL_FONT = new Font(base, 11, Font.NORMAL, BaseColor.BLACK);
            SMALL_FONT = new Font(base, 10, Font.ITALIC, BaseColor.GRAY);
        }
    }
 // 6- ÜSt baslık ve fotogarf
    public static void createCv(String filename) throws Exception {
        initFonts();

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        PdfPTable header = new PdfPTable(new float[] { 3f, 1f });
        header.setWidthPercentage(100);

        PdfPCell nameCell = new PdfPCell();
        nameCell.setBorder(PdfPCell.NO_BORDER);
        nameCell.addElement(new Paragraph("Mohammed Ozbek", TITLE_FONT));
        nameCell.addElement(new Paragraph("Java Geliştirici", NORMAL_FONT));
        nameCell.addElement(new Paragraph("E-posta: example@mail.com | Tel: +90 5xx xxx xx xx", SMALL_FONT));
        nameCell.addElement(new Paragraph("Konum: Ankara", SMALL_FONT));
        header.addCell(nameCell);

        PdfPCell photoCell = new PdfPCell();
        photoCell.setBorder(PdfPCell.NO_BORDER);
        Image photo = loadImage("/pp.jpg", 110f);
        if (photo != null) {
            photoCell.addElement(photo);
        } else {
            photoCell.addElement(new Paragraph("Fotoğraf bulunamadı", SMALL_FONT));
        }
        header.addCell(photoCell);

        document.add(header);
        document.add(new Paragraph(" "));

        // 7-Özet
        document.add(new Paragraph("Özet", HEADER_FONT));
        document.add(new Paragraph(
                "Problem çözmeyi seven, ekip çalışmasına yatkın bir Java geliştiricisi. " +
                        "PDF üretimi, REST servisleri ve veri modelleme konularında hevesli.", NORMAL_FONT));
        document.add(new Paragraph(" "));

        // Yetenekler
        document.add(new Paragraph("Yetenekler", HEADER_FONT));
        PdfPTable skills = simpleListTable(new String[] {
                "Java 17, Maven", "iText ile PDF üretimi", "REST, JSON", "SQL, JPA", "Git, GitHub"
        });
        document.add(skills);
        document.add(new Paragraph(" "));

        // 8- İş deneyimleri
        document.add(new Paragraph("İş Deneyimi", HEADER_FONT));
        document.add(experienceBlock(
                "Backend Developer", "Hayali Teknoloji A.Ş.", "01/2024 - Güncel",
                new String[] {
                        "Spring Boot ile REST API geliştirme.",
                        "iText kullanarak PDF raporlama altyapısı.",
                        "JUnit ile birim testleri ve CI süreçlerine katkı."
                }
        ));
        document.add(experienceBlock(
                "Java Developer", "Uydurma Yazılım Ltd.", "06/2022 - 12/2023",
                new String[] {
                        "Veri modelleme ve performans iyileştirmeleri.",
                        "Maven çok modüllü yapı bakımı.",
                        "Dokümantasyon ve bilgi paylaşımı."
                }
        ));
        document.add(experienceBlock(
                "Stajyer", "Kurgusal Bilişim", "07/2021 - 09/2021",
                new String[] {
                        "Temel Java projeleri ve kod incelemeleri.",
                        "Versiyon kontrol süreçlerine katılım.",
                        "Takım içi görevlerde destek."
                }
        ));
        document.add(new Paragraph(" "));

        // 9- Eğitim
        document.add(new Paragraph("Eğitim", HEADER_FONT));
        document.add(new Paragraph("B.S. Bilgisayar Mühendisliği, Örnek Üniversitesi (2017 - 2021)", NORMAL_FONT));
        document.add(new Paragraph(" "));

        // Alt bilgi
        document.add(new Paragraph("Güncellenme tarihi: " + LocalDate.now(), SMALL_FONT));

        document.close();
    }

    private static PdfPTable simpleListTable(String[] items) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        for (String item : items) {
            PdfPCell bullet = new PdfPCell(new Phrase("•", NORMAL_FONT));
            bullet.setBorder(PdfPCell.NO_BORDER);
            bullet.setHorizontalAlignment(Element.ALIGN_RIGHT);
            PdfPCell text = new PdfPCell(new Phrase(item, NORMAL_FONT));
            text.setBorder(PdfPCell.NO_BORDER);
            table.addCell(bullet);
            table.addCell(text);
        }
        return table;
    }

    // 10-iş bloğunu döndürme oluşturma
    private static PdfPTable experienceBlock(String title, String company, String dates, String[] bullets) {
        PdfPTable block = new PdfPTable(1);
        block.setWidthPercentage(100);

        Paragraph header = new Paragraph(title + " — " + company + " (" + dates + ")", NORMAL_FONT);
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBorder(PdfPCell.NO_BORDER);
        headerCell.addElement(header);
        block.addCell(headerCell);

        PdfPTable bulletsTable = simpleListTable(bullets);
        PdfPCell bulletsCell = new PdfPCell(bulletsTable);
        bulletsCell.setBorder(PdfPCell.NO_BORDER);
        block.addCell(bulletsCell);

        return block;
    }

    // 11-Resim okuma
    private static Image loadImage(String classpath, float maxWidth) {
        try (InputStream in = getResource(classpath)) {
            if (in == null) return null;
            byte[] bytes = in.readAllBytes();
            Image img = Image.getInstance(bytes);
            img.scaleToFit(maxWidth, maxWidth);
            img.setAlignment(Image.ALIGN_RIGHT);
            return img;
        } catch (Exception e) {
            return null;
        }
    }

    private static InputStream getResource(String path) {
        return ResumePdfGenerator.class.getResourceAsStream(path);
    }
}