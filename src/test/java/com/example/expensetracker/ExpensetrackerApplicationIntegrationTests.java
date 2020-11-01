package com.example.expensetracker;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = ExpensetrackerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
class ExpensetrackerApplicationIntegrationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ExpenseRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		repository.deleteAll();
	}
	@After
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void givenExpenses_whenGetExpenses_thenStatus200()
			throws Exception {
		Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
		repository.save(expense);

		mvc.perform(get("/api/v1/expenses")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", is(expense.getName())))
				.andExpect(jsonPath("$[0].description", is(expense.getDescription())))
				.andExpect(jsonPath("$[0].amount", is(expense.getAmount().doubleValue())));
	}

	@Test
	public void givenExpenses_whenGetExpenseBYId_thenReturnExpense()
			throws Exception {

		Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
		expense = repository.save(expense);


		mvc.perform(get("/api/v1/expenses/{id}",expense.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(expense.getName())))
				.andExpect(jsonPath("$.description", is(expense.getDescription())))
				.andExpect(jsonPath("$.amount", is(expense.getAmount().doubleValue())));
	}

	@Test
	public void givenExpense_whenSaveExpense_thenReturnOkStatus()
			throws Exception {
		Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));

		mvc.perform(post("/api/v1/expenses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(expense)))
				.andExpect(status().isOk());
	}

	@Test
	public void givenExpenses_whenDeleteExpense_thenReturnOkStatus()
			throws Exception {

		Expense expense = new Expense("Tablet", "Some tablet", new BigDecimal("2000.0"));
		expense = repository.save(expense);


		mvc.perform(delete("/api/v1/expenses/{id}",expense.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
