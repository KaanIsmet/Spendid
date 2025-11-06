package com.KaanIsmetOkul.Spendid.service;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.dataformat.csv.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvImportService {

    private final CsvMapper mapper;
    private final CsvSchema schema;
    private final ExpenseService expenseService;
    private final String fileName;

    public CsvImportService(ExpenseService service, String fileName) {
        mapper = new CsvMapper();
        this.fileName = fileName;
        expenseService = service;
        schema = CsvSchema.emptySchema().withHeader();
    }

    public List<Expense> importCsv(User user) throws IOException {
        File csvFile = new File(fileName);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("Unable to find file: " + fileName);
        }

        if (csvFile.length() == 0) {
            throw new IOException("File: " + fileName + " is empty");
        }
        try {
            MappingIterator<Expense> iterator = mapper.readerWithSchemaFor(Expense.class).with(schema).readValues(csvFile);
            List<Expense> expenses = iterator.readAll();
            LocalDateTime now = LocalDateTime.now();
            for (Expense expense : expenses) {
                if (expense.getAmount() == null || expense.getExpenseCategory() == null || expense.getDate() == null) {
                    throw new IOException("Invalid expense in csv file. Must have non empty values");
                }
                expense.setUser(user);
                expense.setCreatedAt(now);
                expense.setUpdatedAt(now);
            }

            return expenses;
        }
        catch (IOException e) {
            throw new IOException("Unable to import csv file: " + e.getMessage(), e);
        }
    }
}
