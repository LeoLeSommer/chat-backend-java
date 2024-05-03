package com.leo.chat.dto.Paginated.response;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PaginatedResponse<TElem, TCursor> {
    private Iterable<TElem> data;
    private TCursor nextCursor;

    public PaginatedResponse() {
    }

    public PaginatedResponse(Iterable<TElem> data, TCursor nextCursor) {
        this.data = data;
        this.nextCursor = nextCursor;
    }

    public <TMappedElem> PaginatedResponse<TMappedElem, TCursor> mapData(Function<TElem, TMappedElem> mapper) {
        return new PaginatedResponse<>(StreamSupport.stream(data.spliterator(), false)
                .map(mapper)
                .collect(Collectors.toList()), nextCursor);
    }

    public Iterable<TElem> getData() {
        return data;
    }

    public TCursor getNextCursor() {
        return nextCursor;
    }
}
