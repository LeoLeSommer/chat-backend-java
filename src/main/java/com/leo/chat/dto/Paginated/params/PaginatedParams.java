package com.leo.chat.dto.Paginated.params;

public class PaginatedParams<TCursor> {
    private TCursor nextCursor;
    private Integer limit;

    public PaginatedParams(TCursor nextCursor, Integer limit) {
        this.nextCursor = nextCursor;
        this.limit = limit;
    }

    public TCursor getNextCursor() {
        return nextCursor;
    }

    public Integer getLimit() {
        return limit;
    }
}
