package com.library_management.api.helper.log;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorLog {

    private static String getListErrorToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    private static  String filteredListErrors(Exception e) {
        BufferedReader reader = new BufferedReader(new StringReader(getListErrorToString(e)));
        StringBuilder truncatedStackTrace = new StringBuilder();
        String line;
        int lineCount = 0;

        try {
            while ((line = reader.readLine()) != null && lineCount < 10) {
                truncatedStackTrace.append(line).append(System.lineSeparator());
                lineCount++;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return truncatedStackTrace.toString();
    }

    public static void logError(Exception e , String logFilePath, String format ) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        String logEntry = String.format("%s" + format + "%s%n", timestamp, filteredListErrors(e));
        File logFile = new File(logFilePath);
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(logEntry);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
