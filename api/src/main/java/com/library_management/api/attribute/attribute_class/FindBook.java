package com.library_management.api.attribute.attribute_class;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FindBook {
    String name;
    String amount;
    Long categoryId;
}
