package com.faithfulolaleru.firstbatch;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomWriter implements ItemWriter<Account> {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void write(Chunk<? extends Account> chunk) throws Exception {
        for(Account item : chunk) {
            jdbcTemplate.update("INSERT INTO account (id, code, account_name, type, allow_reconciliation, external_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                            item.getId(), item.getCode(), item.getAccountName(), item.getType(),
                            item.getAllowReconciliation(), item.getExternalId());
        }
    }
}
