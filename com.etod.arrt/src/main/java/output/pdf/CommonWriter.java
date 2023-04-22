package output.pdf;

import com.itextpdf.text.*;
import dto.JobLog;
import util.DateUtil;

import java.util.Date;
import java.util.Map;
import java.util.Set;


public class CommonWriter {

    public static final String employerHeadLine = "Организация: ";
    public static final String employeeHeadLine = "Работник: ";
    public static final String periodHeadLine = "Период: ";

    public static final String employerName = "Hotel-Pension Bregenz";
    public static final String employeeName = "Тодаренко Кристина";

    public static final Double pricePerHour = 13d;

    public static Document writeHeader(Document document, Date startInterval) {

        Paragraph p1 = new Paragraph();
        p1.add(PhraseProvider.getPhraseHeadline(employerHeadLine));
        p1.add(PhraseProvider.getPhraseHeadlineItalic(employerName));
        Paragraph p2 = new Paragraph();
        p2.add(PhraseProvider.getPhraseHeadline(employeeHeadLine));
        p2.add(PhraseProvider.getPhraseHeadlineItalic(employeeName));
        p2.setPaddingTop(10f);
        Paragraph p3 = new Paragraph();
        p3.add(PhraseProvider.getPhraseHeadline(periodHeadLine));
        p3.add(PhraseProvider.getPhraseHeadlineItalic(DateUtil.getPeriodString(startInterval)));
        p3.setPaddingTop(10f);

        try {
            document.add(p1);
            document.add(p2);
            document.add(p3);
            document.add(new Paragraph(10, "\u00a0"));
            document.add(new Paragraph(10, "\u00a0"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document writeFooter(Document document, Map<Date, JobLog> jls) {

        Double hoursSum = 0d;
        Set<Date> set = jls.keySet();

        for (Date d : set) {
            JobLog jl = jls.get(d);
            hoursSum = hoursSum + jl.getHours();
        }

        Double sum = hoursSum * pricePerHour;

        Paragraph p1 = new Paragraph();
        p1.add(PhraseProvider.getPhraseHeadline("Общее количество часов: "));
        p1.add(PhraseProvider.getPhraseHeadlineItalic(hoursSum.toString()));
        p1.add(PhraseProvider.getPhraseHeadlineItalic(" ч."));

        Paragraph p2 = new Paragraph();
        p2.add(PhraseProvider.getPhraseHeadline("Часовая ставка: "));
        p2.add(PhraseProvider.getPhraseHeadlineItalic(pricePerHour.toString()));
        p2.add(PhraseProvider.getPhraseHeadlineItalic(" Eur"));
        p2.setPaddingTop(10f);

        Paragraph p3 = new Paragraph();
        p3.add(PhraseProvider.getPhraseHeadline("Общая сумма: "));
        p3.add(PhraseProvider.getPhraseHeadlineItalic(sum.toString()));
        p3.add(PhraseProvider.getPhraseHeadlineItalic(" Eur"));
        p3.setPaddingTop(10f);

        try {
            document.add(new Paragraph(10, "\u00a0"));
            document.add(p1);
            document.add(p2);
            document.add(p3);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }
}
