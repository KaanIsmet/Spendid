package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.dataformat.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

    private final CsvMapper mapper;
    private final CsvSchema schema;
    private final ExpenseService expenseService;

    public CsvImportService(ExpenseService expenseService) {
        mapper = new CsvMapper();
        this.expenseService = expenseService;
        this.mapper.registerModule(new JavaTimeModule());
        schema = CsvSchema.emptySchema().withHeader();
    }

    public List<Expense> importCsv(MultipartFile file, User user) throws IOException {
        if (file.isEmpty())
            throw new FileNotFoundException("Unable to retreive file");

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".csv")) {
            throw new IOException("file is empty");
        }
        try (InputStream inputStream = file.getInputStream()){
            return parseAndValidateExpenses(inputStream, user);
        } catch (IOException e) {

            throw new IOException("Unable to import csv file: " + e.getMessage(), e);
        }

    }

    public List<Expense> parseAndValidateExpenses(InputStream inputStream, User user) throws IOException {
        MappingIterator<Expense> iterator = mapper.readerWithSchemaFor(Expense.class).with(schema).readValues(inputStream);
        List<Expense> expenses = iterator.readAll();
        if (expenses.isEmpty()) {
            throw new IOException("CSV file contains no expenses");
        }

        LocalDateTime now = LocalDateTime.now();
        List<String> validationErrors = new ArrayList<>();
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            int rowNumber = i + 2;
            if (expense.getAmount() == null) {
                validationErrors.add("Row" + rowNumber + ": Amount is required");
            } else if (expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                validationErrors.add("Row" + rowNumber + ": Amount must be positive");
            }
            if (expense.getDate() == null) {
                validationErrors.add("Row" + rowNumber + ": Date is required");
            }
            if (validationErrors.isEmpty()) {
                expense.setUser(user);
                expense.setCreatedAt(now);
                expense.setUpdatedAt(now);
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new IOException("Validation erros" + String.join("; ", validationErrors));
        }

        return expenses;
    }
}
