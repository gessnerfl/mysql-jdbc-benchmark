package de.gessnerfl.mysqljdbcbenchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ShutdownService {
    private final ApplicationContext applicationContext;

    @Autowired
    public ShutdownService(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public void shutdown(int rc){
        SpringApplication.exit(applicationContext, () -> rc);
    }
}
