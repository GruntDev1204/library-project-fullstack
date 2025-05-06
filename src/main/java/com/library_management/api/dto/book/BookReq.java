package com.library_management.api.dto.book;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookReq {
    @NotBlank(message = "Name cannot be empty")
    String name;

    @NotNull(message = "quantity cannot be empty")
    @Min(1)
    Long quantity;

    @DecimalMin(value = "0.0", message = "Promotion value must be greater than 0")
    @DecimalMax(value = "100.0", message = "Promotion value must not be greater than 100")
    Double promotionValue;

    Long categoryId;
    @DecimalMin(value = "1.0")
    Double price;

    String avatar;
}
