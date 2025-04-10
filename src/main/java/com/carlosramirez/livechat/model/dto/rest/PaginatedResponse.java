package com.carlosramirez.livechat.model.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination {
        private int currentPage;
        private int pageSize;
        private int totalPages;
        private long totalElements;
        private int pagesLeft;
        private boolean hasNext;
        private boolean hasPrevious;
        private boolean isFirstPage;
        private boolean isLastPage;
    }
}