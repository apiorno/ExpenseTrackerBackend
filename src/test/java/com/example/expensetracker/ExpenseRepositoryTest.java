package com.example.expensetracker;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExpenseRepository repository;

    @Test
    public void whenFindByID_thenReturnExpense() {
        // expense
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        entityManager.persist(expense);
        entityManager.flush();

        // when
        Expense found = repository.findById(expense.getId()).orElse(null);

        // then
        assertThat(found.getName())
                .isEqualTo(expense.getName());
        assertThat(found.getDescription())
                .isEqualTo(expense.getDescription());
        assertThat(found.getAmount())
                .isEqualTo(expense.getAmount());
    }

    @Test
    public void whenFindAll_thenReturnAllExpenses() {
        // expense
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        Expense expense2 = new Expense("Tablet2", "Some tablet2", new BigDecimal("2000.0"));
        assertThat(repository.findAll()).isEmpty();
        entityManager.persist(expense);
        entityManager.persist(expense2);
        entityManager.flush();

        // when
        List<Expense> found = repository.findAll();

        // then
        assertThat(found).isNotEmpty();
        assertThat(found).size().isEqualTo(2);
    }
    @Test
    public void whenSave_thenExpenseIsStored() {
        // expense
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        repository.save(expense);

        // when
        Expense found = repository.findById(expense.getId()).orElse(null);

        // then
        assertThat(found.getName())
                .isEqualTo(expense.getName());
        assertThat(found.getDescription())
                .isEqualTo(expense.getDescription());
        assertThat(found.getAmount())
                .isEqualTo(expense.getAmount());
    }
    @Test
    public void whenDelete_thenSavedExpenseIsGone() {
        // expense
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        repository.save(expense);

        // when
        Expense found = repository.findById(expense.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();

        repository.delete(expense);

        // when
        found = repository.findById((long) 1).orElse(null);

        // then
        assertThat(found).isNull();
    }

}
