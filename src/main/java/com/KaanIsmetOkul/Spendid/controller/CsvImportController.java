package com.KaanIsmetOkul.Spendid.controller;

import com.KaanIsmetOkul.Spendid.entity.Expense;
import com.KaanIsmetOkul.Spendid.entity.User;
import com.KaanIsmetOkul.Spendid.exceptionHandling.UserNotFound;
import com.KaanIsmetOkul.Spendid.service.CsvImportService;
import com.KaanIsmetOkul.Spendid.service.ExpenseService;
import com.KaanIsmetOkul.Spendid.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
public class CsvImportController {

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @PostMapping("/user/{id}/import")
    public ResponseEntity<?> addExpenseByCsv(@PathVariable UUID id, @RequestParam("file")MultipartFile file) throws Exception {
        try {
            User user = userService.getUser(id);
            if (user == null) {
                throw new UserNotFound("Unable to find user with id: " + id);
            }


            List<Expense> expenses = csvImportService.importCsv(file, user);


            List<Expense> savedExpenses = expenseService.saveExpenses(expenses);

            BigDecimal totalAmount = savedExpenses.stream()
                    .map(Expense::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully imported expenses");
            response.put("totalExpenses", savedExpenses.size());
            response.put("totalAmount", totalAmount);
            response.put("expenses", savedExpenses);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            throw new IOException("IO error: " + e.getMessage(), e);

        } catch (Exception e) {
            throw new Exception("Server error: " + e.getMessage(), e);
        }
    }


}
