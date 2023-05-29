package com.faithfulolaleru.firstbatch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/csv")
@AllArgsConstructor
public class CsvController {

    private AccountRepository accountRepository;

    private CsvReader csvReader;


    @PostMapping("/import")
    public String importCsvToDB() {
        String result = null;
        try {
            File file = ResourceUtils.getFile("classpath:Book1.csv");
            File file1 = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("Book1.csv")).getFile());

            List<Account> accounts = csvReader.readCsvFile3(new FileInputStream(file1));
            accountRepository.saveAll(accounts);

            result = "Successfully saved";
        } catch (FileNotFoundException e) {
            System.out.println("exception encountered --> ");
            result = "Error saving";
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error saving";
        }

        return result;
    }
}
