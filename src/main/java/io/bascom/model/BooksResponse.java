package io.bascom.model;

import java.util.ArrayList;
import java.util.List;

public class BooksResponse {

    private final List<BookResult> bookResults = new ArrayList<>();
    private boolean success = false;

    public BooksResponse() {

    }

    public List<BookResult> getBookResults() {
        return bookResults;
    }

    public BooksResponse addBookResult(final BookResult bookResult) {
        this.bookResults.add(bookResult);
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BooksResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
