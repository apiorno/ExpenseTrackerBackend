package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements ExpenseServiceInterface {

    @Autowired
    ExpenseRepository repository;

    @Override
    public List<Expense> findAll() {
        return repository.findAll();
    }

    @Override
    public Expense save(Expense expense) {
        repository.save(expense);
        return expense;
    }

    @Override
    public Expense findById(Long id) {
        Optional<Expense> expense = repository.findById(id);
        return expense.orElse(null);
    }

    @Override
    public void delete(Long id ) {
        repository.deleteById(id);
    }
}
