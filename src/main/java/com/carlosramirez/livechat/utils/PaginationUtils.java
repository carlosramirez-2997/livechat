package com.carlosramirez.livechat.utils;

import com.carlosramirez.livechat.model.dto.rest.PaginatedResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public class PaginationUtils {

    public static <D> PaginatedResponse<D> mapPage(Page<?> source, List<D> content) {
        int pagesLeft = source.getTotalPages() - source.getNumber() - 1;

        PaginatedResponse.Pagination meta = new PaginatedResponse.Pagination(
                source.getNumber(),
                source.getSize(),
                source.getTotalPages(),
                source.getTotalElements(),
                pagesLeft,
                source.hasNext(),
                source.hasPrevious(),
                source.isFirst(),
                source.isLast()
        );

        return new PaginatedResponse<>(content, meta);
    }
}
