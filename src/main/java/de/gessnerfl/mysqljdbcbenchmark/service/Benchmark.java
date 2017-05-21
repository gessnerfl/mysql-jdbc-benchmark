package de.gessnerfl.mysqljdbcbenchmark.service;

import de.gessnerfl.mysqljdbcbenchmark.config.BenchmarkConfigurationProperties;
import de.gessnerfl.mysqljdbcbenchmark.model.BenchmarkReportData;
import de.gessnerfl.mysqljdbcbenchmark.model.IterationReport;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Benchmark {
    private final BenchmarkConfigurationProperties benchmarkConfigurationProperties;
    private final BenchmarkDataService benchmarkDataService;
    private final ReportCreator reportCreator;
    private final Logger logger;

    @Autowired
    public Benchmark(BenchmarkConfigurationProperties benchmarkConfigurationProperties, BenchmarkDataService benchmarkDataService, ReportCreator reportCreator, Logger logger) {
        this.benchmarkConfigurationProperties = benchmarkConfigurationProperties;
        this.benchmarkDataService = benchmarkDataService;
        this.reportCreator = reportCreator;
        this.logger = logger;
    }

    public void run() {
        int iterations = benchmarkConfigurationProperties.getIterations();
        int insertsPerIteration = benchmarkConfigurationProperties.getInsertsPerIteration();

        logger.info("Start Benchmark with {} iterations and {} inserts per iteration", insertsPerIteration, insertsPerIteration);

        BenchmarkReportData benchmarkReportData = BenchmarkReportData.startBenchmarkWith(iterations, insertsPerIteration);
        for (int i = 1; i <= iterations; i++) {
            IterationReport iterationReport = runIteration(i, insertsPerIteration);
            benchmarkReportData.addIteration(iterationReport);
        }
        benchmarkReportData.endBenchmark();
        logger.info("Benchmark finished within {}ms", benchmarkReportData.getDuration().toMillis());
        reportCreator.writeReport(benchmarkReportData);
    }

    private IterationReport runIteration(int iteration, int numberOfInserts) {
        logger.info("Execute iteration {}", iteration);
        IterationReport iterationReport = IterationReport.startIteration(iteration);
        benchmarkDataService.insertRecords(numberOfInserts);
        benchmarkDataService.clearDatabase();
        iterationReport.endIteration();
        logger.info("Iteration {} completed in {}ms", iteration, iterationReport.getDuration().toMillis());
        return iterationReport;
    }

}
