package com.library_management.api.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    Long quantity;

    @ManyToOne
    @JoinColumn(name = "id_category")
    Category category;

    @Column(name = "promotion_value", columnDefinition = "double default 0.0")
    Double promotionValue;

    @Column(columnDefinition = "DECIMAL(15, 0) DEFAULT 0")
    Double price;

    @Column(name = "transaction_volume", nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    Long transactionVolume;

    @Column(columnDefinition = "varchar(500)", nullable = true)
    String avatar;
}
