package io.bascom.model;

import io.bascom.util.StringUtil;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Entity
public class Book {

    private static final String MISSING_FIELD = "Book %s must not be missing, null, blank or contain only spaces";
    private static final String RATING_OUT_OF_RANGE = "The book rating range is from 0 (why did I read this??) to 5 (best! book! EVAAAAAR!!!!)";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookID", unique = true, nullable = false)
    private int id;

    // Mandatory fields
    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String author;

    // Optional fields
    private int year;
    private String genre;
    private String series;
    @Min(0)
    @Max(5)
    private int rating = 0;

    public Book() {
    }

    public BookError validate() {
        BookError bookError = new BookError(this);
        if (StringUtil.isEmpty(title)) {
            bookError.errorMessage = String.format(MISSING_FIELD, BookField.TITLE.value);
        }
        if (StringUtil.isEmpty(author)) {
            bookError.errorMessage = String.format(MISSING_FIELD, BookField.AUTHOR.value);
        }
        if (rating < 0 || rating > 5) {
            bookError.errorMessage = RATING_OUT_OF_RANGE;
        }
        return bookError;
    }

    public enum BookField {
        ID("id"),
        TITLE("title"),
        AUTHOR("author"),
        SERIES("series"),
        GENRE("genre"),
        YEAR("year"),
        RATING("rating");

        public final String value;

        BookField(String value) {
            this.value = value;
        }

        public static boolean isValidField(String value) {
            List<BookField> list = Arrays.asList(BookField.values());
            return list.stream().anyMatch(m -> m.value.equals(value));
        }
    }

    public int getId() {
        return id;
    }

    public Book setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getYear() {
        return year;
    }

    public Book setYear(int year) {
        this.year = year;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public Book setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public Book setSeries(String series) {
        this.series = series;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Book setRating(int rating) {
        this.rating = rating;
        return this;
    }
}