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
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_name", nullable = false)
    String userName;

    String password;
    String status;

    @Column(name = "is2FA", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    Boolean is2FA;

    @Column(name = "secret_key", nullable = true, columnDefinition = "VARCHAR(255) DEFAULT NULL")
    String secretKey;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;
}
