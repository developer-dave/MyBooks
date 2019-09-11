package io.bascom.model;

import io.bascom.util.StringUtil;

public class BookError {

    public final Book book;
    public String errorMessage;

    public BookError(Book book) {
        this.book = book;
    }

    public boolean hasError() {
        return StringUtil.isNotEmpty(errorMessage);
    }
}
