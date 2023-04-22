package output.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dto.JobLog;
import util.DateUtil;
import util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

public class PdfsWriter {

    public static File writePdfFile(Map<Integer, Date> interval, Map<Date, JobLog> jls) {

        Date startInterval = interval.get(1);
        String fileName = DateUtil.getPeriodStringEng(startInterval) + ".pdf";

        FileUtils fileUtils = new FileUtils();
        File file = fileUtils.writePdfFile(fileName);

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(20, 20, 18, 18);
        document.open();
        document = CommonWriter.writeHeader(document, startInterval);
        document = JobLogWriter.writeJobLogs(document, jls, interval);
        document = CommonWriter.writeFooter(document, jls);
        document.close();
        return file;
    }
}
