package com.faithfulolaleru.firstbatch;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class SqlScriptRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;


    @Override
    public void run(String... args) throws Exception {
//        executeSqlScriptMysql("sql/create_account_postgres.sql");
         executeSqlScriptPostgres("sql/create_account_postgres.sql");
         executeSqlScriptPostgres("sql/batch-schema-postgresql.sql");
        // executeSqlScriptMysql("sql/batch-schema-mysql.sql");
    }

    private void executeSqlScriptPostgres(String scriptPath) {
        try {
            ClassPathResource resource = new ClassPathResource(scriptPath);
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sqlScript = new String(bytes, StandardCharsets.UTF_8);

            jdbcTemplate.execute(sqlScript);
        } catch (IOException e) {
            // Handle IOException if necessary
            e.printStackTrace();
        }
    }

    private void executeSqlScriptMysql(String scriptPath) {
        try {
            ClassPathResource resource = new ClassPathResource(scriptPath);
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        } catch (Exception e) {
            // Handle exceptions if necessary
            e.printStackTrace();
        }
    }
}
