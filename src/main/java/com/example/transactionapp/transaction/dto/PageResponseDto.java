package com.example.transactionapp.transaction.dto;

import java.util.List;

public record PageResponseDto<T>(
        int currentPage,
        int totalPages,
        long totalRecords,
        List<T> data
) {
}
