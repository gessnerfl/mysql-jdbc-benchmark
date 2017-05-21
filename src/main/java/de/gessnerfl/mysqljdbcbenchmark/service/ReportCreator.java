package de.gessnerfl.mysqljdbcbenchmark.service;

import de.gessnerfl.mysqljdbcbenchmark.model.BenchmarkReportData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportCreator {

    private static final String FILENAME_DATE_TIME_FORMAT = "yyyyMMdd_HHmmss";
    private static final String HORIZONTAL_LINE = "-----------------------------------------------------------";

    public void writeReport(BenchmarkReportData reportData){
        String dateTime = new SimpleDateFormat(FILENAME_DATE_TIME_FORMAT).format(new Date());
        String filename = "banchmarkResults-"+dateTime+".txt";

        try {
            FileWriter writer = new FileWriter(new File(filename));
            writer.append(HORIZONTAL_LINE);
            writer.append("Benchmark Execution:");
            writer.append(HORIZONTAL_LINE);
            writer.append("Number of Iterations: " + reportData.getNumberOfIterations());
            writer.append("Number of Inserts per Iteration: " + reportData.getInsertsPerIteration());
            writer.append("Start Time: " + reportData.getInsertsPerIteration());
            writer.append("End Time : " + reportData.getInsertsPerIteration());

            writer.append(HORIZONTAL_LINE);
            writer.append("Total Result:");
            writer.append(HORIZONTAL_LINE);

            writer.append(HORIZONTAL_LINE);
            writer.append("Iteration Results:");
            writer.append(HORIZONTAL_LINE);
        }catch (IOException e){

        }
    }
}
