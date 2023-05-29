package com.faithfulolaleru.firstbatch;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CsvReader {

    public List<Account> readCsvFile(Path filePath) throws IOException {
        List<Account> data = new ArrayList<>();

        try (Reader reader = new FileReader(filePath.toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                Integer id = Integer.valueOf(csvRecord.get("Id"));
                String code = csvRecord.get("Code");
                String accountName = csvRecord.get("Account Name");
                String type = csvRecord.get("Type");
                String allowReconciliation = csvRecord.get("Allow Reconciliation");
                String externalId = csvRecord.get("External ID");

                Account model = new Account(id, code, accountName, type, allowReconciliation, externalId);
                data.add(model);
            }
        }

        return data;
    }

    public List<Account> readCsvFile2(Path filePath) throws IOException {
        List<Account> data = new ArrayList<>();

        try (Reader reader = new FileReader(filePath.toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser.getRecords()) {
                Integer id = Integer.valueOf(csvRecord.get("Id"));
                String code = csvRecord.get("Code");
                String accountName = csvRecord.get("Account Name");
                String type = csvRecord.get("Type");
                String allowReconciliation = csvRecord.get("Allow Reconciliation");
                String externalId = csvRecord.get("External ID");

                Account model = new Account(id, code, accountName, type, allowReconciliation, externalId);
                data.add(model);
            }
        }

        return data;
    }

    public List<Account> readCsvFile3(InputStream is) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader())) {

            // CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()

            List<Account> data = new ArrayList<>();

            log.info("Csv parser header map --> ", csvParser.getHeaderMap().entrySet());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();


            csvRecords.forEach(csvRecord -> System.out.println(csvRecord.get("Code")));
            csvRecords.forEach(csvRecord -> System.out.println(csvRecord.getRecordNumber()));

            for (CSVRecord csvRecord : csvRecords) {
                // Integer id = Integer.valueOf(csvRecord.get("Id"));
                String code = csvRecord.get("Code");
                String accountName = csvRecord.get("Account Name");
                String type = csvRecord.get("Type");
                String allowReconciliation = csvRecord.get("Allow Reconciliation");
                String externalId = csvRecord.get("External ID");

                Account model = new Account(code, accountName, type, allowReconciliation, externalId);
                data.add(model);
            }

            return data;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
