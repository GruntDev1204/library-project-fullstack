package com.library_management.api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @Column(name = "number_of_books", nullable = false)
    Long numberOfBooks;

    @Column(name = "transaction_date", nullable = false)
    LocalDateTime transactionDate;

    @Column(name = "expired_date", nullable = false)
    LocalDateTime expiredDate;

    @Column(name = "is_purchased", columnDefinition = "boolean default false")
    Boolean isPurchased;

    @Column(name = "sale_value", nullable = false, columnDefinition = "DOUBLE DEFAULT 0")
    Double saleValue;

    @Column(name = "single_price",  nullable = false , columnDefinition = "Decimal(15, 2) DEFAULT 0")
    Double singlePrice;

    @Column(name = "status", nullable = false)
    String status;
}
