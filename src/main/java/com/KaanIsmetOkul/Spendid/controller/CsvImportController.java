package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.service.CsvImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class CsvImportController {

    private CsvImportService csvImportService;

    @PostMapping("/csv/user/{id}")
    public ResponseEntity<List<Expense>> addExpenseByCsv(@PathVariable UUID id) {

    }
}
