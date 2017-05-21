package de.gessnerfl.mysqljdbcbenchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BenchmarkDataService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BenchmarkDataService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int clearDatabase() {
        return jdbcTemplate.update("DELETE FROM benachmark_data");
    }

    public void insertRecords(int numberOfInserts) {
        for (int i = 1; i <= numberOfInserts; i++) {
            insertRecord(i);
        }
    }

    private void insertRecord(int i) {
        int id = 1_000_000 + i;
        String string1 = "String value for first string column for insert " + i;
        String string2 = "String value for second string column for insert " + i;
        int numeric1 = 2_000_000 + i;
        int numeric2 = 3_000_000 + i;
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        jdbcTemplate.update("INSERT INTO benachmark_data (id, string1, string2, numeric1, numeric2, create_time) VALUES (?,?,?,?,?,?)", id, string1, string2, numeric1, numeric2, createTime);
    }

}
