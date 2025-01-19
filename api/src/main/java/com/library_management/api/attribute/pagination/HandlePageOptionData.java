package com.library_management.api.attribute.pagination;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class HandlePageOptionData {
    public Pageable dataPageOption(PageOption options) {
        if (options == null) {
            return null;
        }else {
            return getPageable1(options);
        }
    }

    @NotNull
    private Pageable getPageable1(PageOption options) {
        return this.getPageable(options);
    }

    @NotNull
    private Pageable getPageable(PageOption options) {
        Integer index =  options.getIndex();
        Integer size = options.getSize();
        String sortBy = options.getSortByCol();
        String sortOrder = options.getSortOption();
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        return PageRequest.of(index - 1, size  , sort);
    }
}