package io.bascom.service;

import io.bascom.model.Book;
import io.bascom.model.BookFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService extends AbstractService {

    public List<String> getAuthors(final BookFilter filter) {
        if (filter.getSort().isEmpty()) {
            filter.addSort(Book.BookField.AUTHOR.value);
        }
        final List<Book> books = getBooks(filter);
        return books.stream().map(Book::getAuthor).distinct().collect(Collectors.toList());
    }

}