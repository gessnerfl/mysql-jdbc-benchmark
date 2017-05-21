package de.gessnerfl.mysqljdbcbenchmark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "de.gessnerfl.docker.mysqlBenchmark")
public class BenchmarkConfigurationProperties {

    public static final int DEFAULT_NUMBER_OF_ITERATIONS = 5;
    public static final int DEFAULT_NUMBER_OF_INSERTS_PER_ITERATION = 100_000;

    private int iterations = DEFAULT_NUMBER_OF_ITERATIONS;
    private int insertsPerIteration = DEFAULT_NUMBER_OF_INSERTS_PER_ITERATION;

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getInsertsPerIteration() {
        return insertsPerIteration;
    }

    public void setInsertsPerIteration(int insertsPerIteration) {
        this.insertsPerIteration = insertsPerIteration;
    }

}
