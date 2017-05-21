package de.gessnerfl.mysqljdbcbenchmark.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class IterationReport {

    public static IterationReport startIteration(int iteration){
        return new IterationReport(iteration);
    }

    private final int iteration;
    private final LocalDateTime start;
    private LocalDateTime end;

    private IterationReport(int iteration){
        this.iteration = iteration;
        this.start = LocalDateTime.now();
    }

    public void endIteration(){
        end = LocalDateTime.now();
    }

    public Duration getDuration(){
        if(end == null){
            throw new IllegalStateException("Iteration not finished yet");
        }
        return Duration.between(start, end);
    }

}
