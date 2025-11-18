package co.edu.uniquindio.envio.model.adapter;

import co.edu.uniquindio.envio.mapping.dto.EnvioDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PdfReportGeneratorAdapter implements ReportGenerator {
    @Override
    public void generateReport(List<EnvioDto> envios, String reportTitle, String filePath) throws IOException {
        File file = new File(filePath);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText(reportTitle);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Fecha de Generación: " + LocalDate.now());
            contentStream.endText();

            float yStart = 700;
            float margin = 50;
            float yPosition = yStart;
            int rowsPerPage = 25;
            int rowCount = 0;

            String[] headers = {"ID Envio", "Fecha", "Origen", "Destino", "Estado", "Costo"};
            float[] columnWidths = {80, 80, 100, 100, 80, 60};

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
            float x = margin;
            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(headers[i]);
                contentStream.endText();
                x += columnWidths[i];
            }
            yPosition -= 15;

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
            for (EnvioDto envio : envios) {
                if (rowCount >= rowsPerPage) {
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    yPosition = 750;
                    rowCount = 0;

                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                    x = margin;
                    for (int i = 0; i < headers.length; i++) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(x, yPosition);
                        contentStream.showText(headers[i]);
                        contentStream.endText();
                        x += columnWidths[i];
                    }
                    yPosition -= 15;
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 8);
                }

                x = margin;
                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(envio.id());
                contentStream.endText();
                x += columnWidths[0];

                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(envio.fechaCreacion().toString());
                contentStream.endText();
                x += columnWidths[1];

                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(envio.direccionOrigen());
                contentStream.endText();
                x += columnWidths[2];

                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(envio.direccionDestino());
                contentStream.endText();
                x += columnWidths[3];

                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(envio.estadoActual());
                contentStream.endText();
                x += columnWidths[4];

                contentStream.beginText();
                contentStream.newLineAtOffset(x, yPosition);
                contentStream.showText(String.format("%.2f", envio.costo()));
                contentStream.endText();
                x += columnWidths[5];

                yPosition -= 12;
                rowCount++;
            }

            contentStream.close();
            document.save(file);
            System.out.println("Reporte PDF generado exitosamente en: " + file.getAbsolutePath());
        }
    }
}
