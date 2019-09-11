package io.bascom.service;

import io.bascom.model.Book;
import io.bascom.model.Book.BookField;
import io.bascom.model.BookFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService extends AbstractService {

    public List<String> getGenres(final BookFilter filter) {
        if (filter.getSort().isEmpty()) {
            filter.addSort(BookField.GENRE.value);
        }
        final List<Book> books = getBooks(filter);
        return books.stream().map(Book::getGenre).distinct().collect(Collectors.toList());
    }

}