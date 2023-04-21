package output.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class CellsProvider {

    public PdfPCell getHeaderCell(String content) {
        PhraseProvider phraseProvider = new PhraseProvider();
        PdfPCell cell = new PdfPCell(phraseProvider.getPhraseRowHeader(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderWidth(1.1f);
        cell.setFixedHeight(15f);
        return cell;
    }

    public PdfPCell getMonthHeaderCell(String content, int colSpan) {
        PdfPCell cell = getHeaderCell(content);
        cell.setColspan(colSpan);
        return cell;
    }

    public PdfPCell getRowCell(String content) {
        PhraseProvider phraseProvider = new PhraseProvider();
        PdfPCell cell = new PdfPCell(phraseProvider.getPhraseRow(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }

    public PdfPCell getRowIntervalCell(String content, int colSpan) {
        PdfPCell cell = getRowCell(content);
        cell.setColspan(colSpan);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorderWidth(1.1f);
        return cell;
    }

    public void addEmptyRow(PdfPTable table, int numberOfColumns) {
        if (numberOfColumns > 0) {
            PdfPCell cellBlankRow = new PdfPCell(new Phrase(" "));
            table.addCell(cellBlankRow);
            addCells(table, numberOfColumns - 1);
        }
    }

    public void addCells(PdfPTable table, int numberOfCells) {
        if (numberOfCells > 0)
            for (int i = 0; i < numberOfCells; i++) {
                table.addCell("");
            }
    }
}
