package com.library_management.api.helper.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageOption {
    Integer index = 1;
    Integer size = 5;
    String sortByCol = "id";
    String sortOption = "desc";
}