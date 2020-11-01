package com.example.expensetracker;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.service.ExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class ExpenseServiceTest {

    @TestConfiguration
    static class ExpenseServiceTestContextConfiguration {

        @Bean
        public ExpenseService expenseService() {
            return new ExpenseService();
        }
    }

    @Autowired
    private ExpenseService expenseService;

    @MockBean
    private ExpenseRepository expenseRepository;

    @Before
    public void setUp() {
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        expense.setId((long) 1);
        Mockito.when(expenseRepository.findById(expense.getId()))
                .thenReturn(Optional.of(expense));
        Mockito.when(expenseRepository.findAll())
                .thenReturn(Collections.singletonList(expense));
    }

    @Test
    public void whenFindAll_thenExpensesShouldBeFound() {
        List<Expense> found = expenseService.findAll();

        assertThat(found.size())
                .isEqualTo(1);
        assertThat(found.get(0).getName())
                .isEqualTo("Tablet");
    }

    @Test
    public void whenValidId_thenExpenseShouldBeFound() {
        Long id = (long) 1;
        Expense found = expenseService.findById(id);

        assertThat(found.getName())
                .isEqualTo("Tablet");
    }
    @Test
    public void whenSave_thenRepositorySaveShouldBeCalled() {
        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        expenseService.save(expense);

        verify(expenseRepository).save(expense);
    }
    @Test
    public void whenDelete_thenRepositoryDeleteShouldBeCalled() {
        Long id = (long) 1;
        expenseService.delete(id);

        verify(expenseRepository).deleteById(id);
    }
}
