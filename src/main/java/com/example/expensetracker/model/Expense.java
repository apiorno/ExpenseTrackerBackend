package com.example.expensetracker.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="EXPENSES")
@Setter
@Getter
public class Expense {

    public  Expense(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private BigDecimal amount;

    public Expense (String name, String description, BigDecimal amount){
        this.name=name;
        this.description=description;
        this.amount=amount;
    }
}
