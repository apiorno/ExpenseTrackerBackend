package com.example.expensetracker;

import com.example.expensetracker.controller.ExpensesController;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ExpensesController.class)
public class ExpenseRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExpenseService service;

    @Test
    public void givenExpenses_whenGetExpenses_thenReturnJsonArray()
            throws Exception {

        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));

        List<Expense> allExpenses = Arrays.asList(expense);

        given(service.findAll()).willReturn(allExpenses);

        mvc.perform(get("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(expense.getName())))
                .andExpect(jsonPath("$[0].description", is(expense.getDescription())))
                .andExpect(jsonPath("$[0].amount", is(expense.getAmount().doubleValue())));;
    }

    @Test
    public void givenExpenses_whenGetExpenseBYId_thenReturnExpense()
            throws Exception {

        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        expense.setId((long)1);

        given(service.findById(expense.getId())).willReturn(expense);

        mvc.perform(get("/api/v1/expenses/{id}",expense.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(expense.getName())))
                .andExpect(jsonPath("$.description", is(expense.getDescription())))
                .andExpect(jsonPath("$.amount", is(expense.getAmount().doubleValue())));;
    }

    @Test
    public void givenExpense_whenSaveExpense_thenReturnOkStatus()
            throws Exception {

        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));


        given(service.save(expense)).willReturn(expense);

        mvc.perform(post("/api/v1/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenExpenses_whenDeleteExpense_thenReturnOkStatus()
            throws Exception {

        Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
        expense.setId((long)1);


        mvc.perform(delete("/api/v1/expenses/{id}",expense.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
