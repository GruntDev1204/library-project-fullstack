package com.library_management.api.attribute.attribute_class;

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
