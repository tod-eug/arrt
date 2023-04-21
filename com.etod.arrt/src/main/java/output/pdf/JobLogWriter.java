package output.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import dto.JobLog;
import util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JobLogWriter {

    public static final String datePattern = "dd.MM.yyyy";
    public static final String simpleTime = "HH:mm:ss";
    public static int numberOfColumns = 54;
    public static int hoursDividedOnColumns = 4;
    public static List<String> hours = Arrays.asList("6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18");
    public static List<String> minutes = Arrays.asList("0", "15", "30", "45");

    public static Document writeJobLogs(Document document, Map<Date, JobLog> jls) {

        PdfPTable table = new PdfPTable(new float[] { 4f, 1.5f,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1
        });
        table.setWidthPercentage(100);
        addTableHeader(table);
        addMonthJobLogs(table, jls);


        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static void addTableHeader(PdfPTable table) {
        String month = "Месяц";
        String dayOfWeek = "Д/н";

        CellsProvider cellsProvider = new CellsProvider();
        table.addCell(cellsProvider.getHeaderCell(month));
        table.addCell(cellsProvider.getHeaderCell(dayOfWeek));

        for (String s : hours) {
            table.addCell(cellsProvider.getMonthHeaderCell(s, hoursDividedOnColumns));
        }
    }

    private static void addMonthJobLogs(PdfPTable table, Map<Date, JobLog> jls) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
        SimpleDateFormat simpleFormat = new SimpleDateFormat(simpleTime);
        CellsProvider cellsProvider = new CellsProvider();

        Set<Date> dates = jls.keySet();
        int month = jls.entrySet().iterator().next().getKey().getMonth();

        //get month days
        int monthStartDate = 1;
        int monthEndDate = DateUtil.getLastDayOfMonth(month);

        for (int i = monthStartDate; i <= monthEndDate; i++) {
            Date d = DateUtil.getDate(i, month);

            if (!dates.contains(d)) {

                //write first 2 columns
                table.addCell(cellsProvider.getRowCell(dateFormat.format(d)));
                table.addCell(cellsProvider.getRowCell(DateUtil.getDayOfTheWeek(d)));

                for (int j = 2; j < numberOfColumns; j++) {
                    table.addCell(cellsProvider.getRowCell(" "));
                }
            } else {
                JobLog jl = jls.get(d);

                //write first 2 columns
                table.addCell(cellsProvider.getRowCell(dateFormat.format(jl.getJobDate())));
                table.addCell(cellsProvider.getRowCell(DateUtil.getDayOfTheWeek(jl.getJobDate())));

                int constantColumns = 2;
                int hourStart = jl.getStartInterval().getHours();
                int minuteStart = jl.getStartInterval().getMinutes();
                int hoursColumn = getAmountOfHoursToSkip(hourStart);
                int minutesColumn = getAmountOfMinutesToSkip(minuteStart);

                //sum all to get final values
                int hoursAndMinutesColumnsToSkip = constantColumns + hoursColumn + minutesColumn;
                int intervalColumn = getAmountOfColumnsForInterval(jl.getStartInterval(), jl.getEndInterval());
                int endOfIntervalColumn = constantColumns + hoursColumn + minutesColumn + intervalColumn;

                //inserting columns before starthour
                for (int j = constantColumns; j < hoursAndMinutesColumnsToSkip; j++) {
                    table.addCell(cellsProvider.getRowCell(" "));
                }
                //inserting interval column
                table.addCell(cellsProvider.getRowIntervalCell(simpleFormat.format(jl.getStartInterval()) + " - " + simpleFormat.format(jl.getEndInterval()) + " = " + jl.getHours() + " ч.",
                        intervalColumn));

                //inserting rest of columns to complete the row
                for (int k = endOfIntervalColumn; k < numberOfColumns; k++) {
                    table.addCell(cellsProvider.getRowCell(" "));
                }
            }
        }
    }

    private static int getAmountOfHoursToSkip(int hourStart) {
        int count = 0;
        for (String s : hours) {
            if (s.equals(String.valueOf(hourStart))) {
                break;
            } else {
                count = count + 1;
            }
        }
        return count * hoursDividedOnColumns;
    }

    private static int getAmountOfMinutesToSkip(int minutesStart) {
        int count = 0;
        for (String s : minutes) {
            if (s.equals(String.valueOf(minutesStart))) {
                return count;
            } else {
                count = count + 1;
            }
        }
        return count;
    }

    private static int getAmountOfColumnsForInterval(Date startInterval, Date endInterval) {
        long diffInMillies = Math.abs(endInterval.getTime() - startInterval.getTime());
        long minutesDiff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Long pieces = minutesDiff / 15;
        return pieces.intValue();
    }
}
