package com.library_management.api.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookRes {
    Long id;
    String name;
    Long quantity;
    String categoryName;
    Long categoryId;
    Double promotionValue;
    Double price;
    Long transactionVolume;
    String avatar;
}
