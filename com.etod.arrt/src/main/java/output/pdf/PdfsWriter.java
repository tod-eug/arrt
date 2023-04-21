package output.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dto.JobLog;
import util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PdfsWriter {

    public File writePdfFile(String fileName, Map<Date, JobLog> jls) {

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
        document = CommonWriter.writeHeader(document, jls);
        document = JobLogWriter.writeJobLogs(document, jls);
        document = CommonWriter.writeFooter(document, jls);
        document.close();
        return file;
    }
}
