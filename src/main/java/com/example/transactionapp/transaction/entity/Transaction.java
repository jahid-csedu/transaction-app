package com.example.transactionapp.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trx_id")
    private Long trxId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "trx_amount")
    private BigDecimal trxAmount;
    @Column(name = "description")
    private String description;
    @Column(name = "trx_date")
    private LocalDate trxDate;
    @Column(name = "trx_time")
    private LocalTime trxTime;
    @Column(name = "customer_id")
    private String customerId;
    @Version
    private Long version;
}
