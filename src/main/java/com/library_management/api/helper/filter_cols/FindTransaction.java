package com.library_management.api.helper.filter_cols;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindTransaction {
    String status;
    Boolean isPurchased;
    String email;
    Long idBook;
    LocalDateTime transactionDate;
    Long transactionQuantity;
    Long idCateGoryOfBook;
    String nameBook;
}
