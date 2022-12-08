package ru.practicum.ewmmainservice.pageable;

import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class EwmPageable implements Pageable {
    private final int offset;
    private final int limit;
    private final Sort sort;

    public EwmPageable(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static Pageable of(Integer from, Integer size) {
        return new EwmPageable(from, size, Sort.unsorted());
    }

    public static Pageable of(Integer from, Integer size, Sort sort) {
        return new EwmPageable(from, size, sort);
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public @NonNull Sort getSort() {
        return sort;
    }

    @Override
    public @NonNull Pageable next() {
        return new EwmPageable(offset + limit, limit, sort);
    }

    @Override
    public @NonNull Pageable previousOrFirst() {
        return new EwmPageable(offset, limit, sort);
    }

    @Override
    public @NonNull Pageable first() {
        return new EwmPageable(offset, limit, sort);
    }

    @Override
    public @NonNull Pageable withPage(int pageNumber) {
        return new EwmPageable(offset + limit * pageNumber, limit, sort);
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}