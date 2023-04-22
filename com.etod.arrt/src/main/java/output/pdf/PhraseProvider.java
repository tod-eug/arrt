package output.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;

import static com.itextpdf.text.Font.*;

public class PhraseProvider {

    private static final String ARIAL = "src/main/resources/arial.ttf";
    private static final Integer HEADER_FONT_SIZE = 14;
    private static final Integer ROW_HEADER_FONT_SIZE = 10;
    private static final Integer ROW_FONT_SIZE = 8;
    private static final Integer HEADLINE_FONT_SIZE = 11;

    public Font getFont(int size) {
        Font font = FontFactory.getFont(ARIAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font.setSize(size);
        return font;
    }

    public Phrase getPhraseHeader(String content) {
        Font font = getFont(HEADER_FONT_SIZE);
        font.setStyle(BOLD);
        return new Phrase(content, font);
    }

    public Phrase getPhraseRowHeader(String content) {
        return new Phrase(content, getFont(ROW_HEADER_FONT_SIZE));
    }

    public Phrase getPhraseRow(String content) {
        return new Phrase(content, getFont(ROW_FONT_SIZE));
    }

    public Phrase getPhraseHeadline(String content) {
        Font font = getFont(HEADLINE_FONT_SIZE);
        return new Phrase(content, font);
    }

    public Phrase getPhraseHeadlineItalic(String content) {
        Font font = getFont(HEADLINE_FONT_SIZE);
        font.setStyle(ITALIC);
        return new Phrase(content, font);
    }

    public Phrase getPhraseHeadlineBold(String content) {
        Font font = getFont(HEADLINE_FONT_SIZE);
        font.setStyle(BOLD);
        return new Phrase(content, font);
    }
}
