package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;

import java.util.List;

public interface ExpenseServiceInterface {

    List<Expense> findAll();
    Expense save(Expense expense);
    Expense findById(Long id);
    void delete(Long id);
}
