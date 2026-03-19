package com.hqzp.recruit.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Paginated response payload.
 *
 * @param <T> record type
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Total record count. */
    private long total;

    /** Current page records. */
    private List<T> records;

    /** Current page number (1-based). */
    private long current;

    /** Page size. */
    private long size;

    /** Total pages. */
    private long pages;

    public static <T> PageResult<T> of(long total, List<T> records, long current, long size) {
        long pages = size == 0 ? 0 : (total + size - 1) / size;
        return new PageResult<>(total, records, current, size, pages);
    }
}
