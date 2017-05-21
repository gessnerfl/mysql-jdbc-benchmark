package de.gessnerfl.mysqljdbcbenchmark.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BenchmarkReportData {
    public static BenchmarkReportData startBenchmarkWith(int numberOfIterations, int insertsPerIteration){
        return new BenchmarkReportData(numberOfIterations, insertsPerIteration);
    }

    private final int numberOfIterations;
    private final int insertsPerIteration;
    private final LocalDateTime start;
    private LocalDateTime end;
    private List<IterationReport> iterationReports = new ArrayList<>();

    private BenchmarkReportData(int numberOfIterations, int insertsPerIteration){
        this.numberOfIterations = numberOfIterations;
        this.insertsPerIteration = insertsPerIteration;
        start = LocalDateTime.now();
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public int getInsertsPerIteration() {
        return insertsPerIteration;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public Stream<IterationReport> getIterationReports() {
        return iterationReports.stream();
    }

    public void addIteration(IterationReport iterationReport) {
        iterationReports.add(iterationReport);
    }

    public void endBenchmark(){
        end = LocalDateTime.now();
    }

    public Duration getDuration(){
        if(end == null){
            throw new IllegalStateException("Benchmark not finished yet");
        }
        return Duration.between(start, end);
    }

    public Duration getAverageDurationPerIteration(){
        Duration duration = getDuration();
        return duration.dividedBy(numberOfIterations);
    }
}
