package com.library_management.api.dto.transaction;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionReq {
    @NotNull(message = "Book id cannot be empty")
    Long bookId;

    @NotNull(message = "Number of books cannot be empty")
    Integer numberOfBooks;

    String status;
    Boolean isPurchased;
    Double saleValue;
}
