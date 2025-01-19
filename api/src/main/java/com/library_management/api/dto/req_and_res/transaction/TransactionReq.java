package com.library_management.api.dto.req_and_res.transaction;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Email of customer cannot be empty")
    String email;

    @NotNull(message = "Book id cannot be empty")
    Long bookId;

    @NotNull(message = "Number of books cannot be empty")
    Integer numberOfBooks;

    String status;
    Boolean isPurchased;
    Double saleValue;
}
