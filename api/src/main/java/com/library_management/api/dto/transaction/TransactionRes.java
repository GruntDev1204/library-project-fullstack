package com.library_management.api.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionRes {
    Long id;
    String email;
    Long bookId;
    String bookName;
    Integer numberOfBooks;
    String status;
    Boolean isPurchased;
    Double saleValue;
    Double singlePrice;
    LocalDateTime transactionDate;
    LocalDateTime expiredDate;
}
