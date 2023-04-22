package output.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class CellsProvider {

    public static PdfPCell getHeaderCell(String content) {
        PdfPCell cell = new PdfPCell(PhraseProvider.getPhraseRowHeader(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderWidth(1.1f);
        cell.setFixedHeight(15f);
        return cell;
    }

    public static PdfPCell getMonthHeaderCell(String content, int colSpan) {
        PdfPCell cell = getHeaderCell(content);
        cell.setColspan(colSpan);
        return cell;
    }

    public static PdfPCell getRowCell(String content) {
        PdfPCell cell = new PdfPCell(PhraseProvider.getPhraseRow(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    public static PdfPCell getRowIntervalCell(String content, int colSpan) {
        PdfPCell cell = getRowCell(content);
        cell.setColspan(colSpan);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidth(1.1f);
        return cell;
    }

    public static void addEmptyRow(PdfPTable table, int numberOfColumns) {
        if (numberOfColumns > 0) {
            PdfPCell cellBlankRow = new PdfPCell(new Phrase(" "));
            table.addCell(cellBlankRow);
            addCells(table, numberOfColumns - 1);
        }
    }

    public static void addCells(PdfPTable table, int numberOfCells) {
        if (numberOfCells > 0)
            for (int i = 0; i < numberOfCells; i++) {
                table.addCell("");
            }
    }
}
