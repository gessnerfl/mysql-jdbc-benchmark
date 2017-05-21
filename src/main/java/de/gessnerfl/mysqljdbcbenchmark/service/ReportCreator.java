package de.gessnerfl.mysqljdbcbenchmark.service;

import de.gessnerfl.mysqljdbcbenchmark.model.BenchmarkReportData;
import de.gessnerfl.mysqljdbcbenchmark.model.IterationReport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ReportCreator {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String FILENAME_DATE_TIME_FORMAT = "yyyyMMdd_HHmmss";
    private static final String HORIZONTAL_LINE = "-----------------------------------------------------------"+LINE_SEPARATOR;
    private static final String INDENT = "    ";

    private final Logger logger;

    @Autowired
    public ReportCreator(Logger logger) {
        this.logger = logger;
    }

    public void writeReport(BenchmarkReportData reportData){
        String dateTime = new SimpleDateFormat(FILENAME_DATE_TIME_FORMAT).format(new Date());
        String filename = "benchmarkResults-"+dateTime+".txt";

        File reportFile = new File(filename);
        try(FileWriter writer = new FileWriter(reportFile)){
            writer.append(HORIZONTAL_LINE);
            writer.append("Benchmark Execution:"+LINE_SEPARATOR);
            writer.append(HORIZONTAL_LINE);
            writer.append("Number of Iterations: " + reportData.getNumberOfIterations()+LINE_SEPARATOR);
            writer.append("Number of Inserts per Iteration: " + reportData.getInsertsPerIteration()+LINE_SEPARATOR);
            writer.append("Start Time: " + reportData.getStart().format(DateTimeFormatter.ISO_DATE_TIME)+LINE_SEPARATOR);
            writer.append("End Time : " + reportData.getEnd().format(DateTimeFormatter.ISO_DATE_TIME)+LINE_SEPARATOR+LINE_SEPARATOR);

            writer.append(HORIZONTAL_LINE);
            writer.append("Total Result:"+LINE_SEPARATOR);
            writer.append(HORIZONTAL_LINE);
            writer.append("Overall Duration: "+reportData.getDuration().toMillis()+"ms"+LINE_SEPARATOR);
            writer.append("Avg. Duration per Iteration: "+reportData.getAverageDurationPerIteration().toMillis()+"ms"+LINE_SEPARATOR+LINE_SEPARATOR);

            writer.append(HORIZONTAL_LINE);
            writer.append("Iteration Results:"+LINE_SEPARATOR);
            writer.append(HORIZONTAL_LINE);

            reportData.getIterationReports().forEach((r) -> writeIterationReport(writer, r));
        }catch (IOException | WrappedIOException e){
            logger.error("Failed to write report", e);
        }
        logger.info("Created Benchmark Report in file: "+reportFile.getAbsolutePath());
    }

    private void writeIterationReport(FileWriter writer, IterationReport report) {
        try {
            writer.append("Iteration " + report.getIteration() + ":" + LINE_SEPARATOR);
            writer.append(INDENT + "Start Time: " + report.getStart().format(DateTimeFormatter.ISO_DATE_TIME) + LINE_SEPARATOR);
            writer.append(INDENT + "End Time: " + report.getEnd().format(DateTimeFormatter.ISO_DATE_TIME) + LINE_SEPARATOR);
            writer.append(INDENT + "Duration: " + report.getDuration().toMillis() + "ms" + LINE_SEPARATOR + LINE_SEPARATOR);
        }catch (IOException e){
            throw new WrappedIOException(e);
        }
    }

    private class WrappedIOException extends RuntimeException {
        public WrappedIOException(IOException e){
            super(e.getMessage(), e);
        }
    }
}
