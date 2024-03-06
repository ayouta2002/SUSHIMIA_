package com.esprit.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.esprit.models.CategorieMenu;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDFExporter {

    public static void exportToPDF(TableView<CategorieMenu> tableView, File file, List<TableColumn<CategorieMenu, ?>> selectedColumns) {
        ObservableList<CategorieMenu> items = tableView.getItems();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, page.getMediaBox().getHeight() - 50);
                contentStream.showText("Table Data");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                float yPosition = page.getMediaBox().getHeight() - 80; // Adjusted for spacing
                float rowHeight = 20;

                for (TableColumn<CategorieMenu, ?> column : selectedColumns) {
                    String columnName = column.getText();
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(columnName);
                    contentStream.endText();
                    yPosition -= rowHeight;
                }

                contentStream.setFont(PDType1Font.HELVETICA, 10);

                for (CategorieMenu item : items) {
                    yPosition -= rowHeight;
                    for (TableColumn<CategorieMenu, ?> column : selectedColumns) {
                        Object cellData = column.getCellData(item);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(50, yPosition);
                        contentStream.showText(cellData != null ? cellData.toString() : "");
                        contentStream.endText();
                    }
                }
            }

            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
