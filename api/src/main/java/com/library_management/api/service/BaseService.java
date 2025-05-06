package com.library_management.api.service;

import com.library_management.api.helper.pagination.PageOption;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BaseService {
    @NotNull
    private static Pageable getPageable(PageOption options) {
        Integer index =  options.getIndex();
        Integer size = options.getSize();
        String sortBy = options.getSortByCol();
        String sortOrder = options.getSortOption();
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        return PageRequest.of(index - 1, size  , sort);
    }

    protected static Pageable dataPageOption(PageOption options) {
        if (options == null) {
            return null;
        }else {
            return getPageable(options);
        }
    }
}
