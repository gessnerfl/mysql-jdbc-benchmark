package de.gessnerfl.mysqljdbcbenchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class BenchmarkLauncher {

    private final Benchmark benchmark;
    private final ShutdownService shutdownService;

    @Autowired
    public BenchmarkLauncher(Benchmark benchmark, ShutdownService shutdownService) {
        this.benchmark = benchmark;
        this.shutdownService = shutdownService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void launch() {
        benchmark.run();
        shutdownService.shutdown(0);
    }
}
