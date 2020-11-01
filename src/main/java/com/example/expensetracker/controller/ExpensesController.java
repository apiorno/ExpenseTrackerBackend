package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class ExpensesController {

    @Autowired
    ExpenseService service;

    @GetMapping("/expenses")
    public ResponseEntity<List<Expense>> get(){
        List<Expense> expenses = service.findAll();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense>  getById(@PathVariable("id") Long id ){
        Expense expense = service.findById(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Expense> save(@RequestBody Expense expense){
         Expense resultExpense = service.save(expense);
        return new ResponseEntity<>(resultExpense, HttpStatus.OK);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<String>  delete(@PathVariable("id") Long id ){
        service.delete(id);
        return new ResponseEntity<>("Expense deleted", HttpStatus.OK);
    }
}
