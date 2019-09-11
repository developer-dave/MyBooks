package io.bascom.model;

public class BookResult {

    private String title;
    private String author;
    private int id;
    private String error;
    private String message;
    private boolean success = false;

    public BookResult() {

    }

    public String getTitle() {
        return title;
    }

    public BookResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookResult setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getId() {
        return id;
    }

    public BookResult setId(int id) {
        this.id = id;
        return this;
    }

    public String getError() {
        return error;
    }

    public BookResult setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BookResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BookResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
