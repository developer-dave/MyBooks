package io.bascom.service;

import io.bascom.model.*;
import io.bascom.model.Book.BookField;
import io.bascom.repository.BookRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends AbstractService {

    private static final String BOOK_EXISTS = "A book with this title and author already exists.";
    private static final String BOOK_DOES_NOT_EXIST = "Book does not exist.";
    private static final String SAVE_SUCCESS = "Book saved.";
    private static final String UPDATE_SUCCESS = "Book updated.";
    private static final String DELETE_SUCCESS = "Book deleted.";

    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }


    public List<Book> getBooks(final BookFilter filter) {
        if (filter.getSort().isEmpty()) {
            filter.addSort(BookField.TITLE.value);
        }
        return super.getBooks(filter);
    }

    public BooksResponse createBooks(final List<Book> books) {
        return createOrUpdateBooks(books, true);
    }

    public BooksResponse updateBooks(final List<Book> books) {
        return createOrUpdateBooks(books, false);
    }

    private BooksResponse createOrUpdateBooks(final List<Book> books, final boolean isNew) {
        final BooksResponse booksResponse = new BooksResponse();
        boolean success = true;
        for (final Book book : books) {
            final BookResult bookResult = isNew ? createBook(book) : updateBook(book);
            success = success && bookResult.isSuccess();
            booksResponse.addBookResult(bookResult);
        }
        booksResponse.setSuccess(success);
        return booksResponse;
    }

    private BookResult createBook(final Book book) {
        final BookResult bookResult = new BookResult();
        bookResult.setTitle(book.getTitle());
        final BookError bookError = book.validate();
        if (bookError.hasError()) {
            bookResult.setError(bookError.errorMessage);
        } else if (bookExists(book.getTitle(), book.getAuthor())) {
            bookResult.setAuthor(book.getAuthor());
            bookResult.setError(BOOK_EXISTS);
        } else {
            final Book createdBook = bookRepository.save(book);
            bookResult.setId(createdBook.getId());
            bookResult.setMessage(SAVE_SUCCESS);
            bookResult.setSuccess(true);
        }
        return bookResult;
    }

    private BookResult updateBook(final Book book) {
        final BookResult bookResult = new BookResult();
        bookResult.setId(book.getId());
        final BookError bookError = book.validate();
        if (bookError.hasError()) {
            bookResult.setError(bookError.errorMessage);
        } else if (!bookRepository.existsById(book.getId())) {
            bookResult.setError(BOOK_DOES_NOT_EXIST);
        } else if (similarBookExists(book.getTitle(), book.getAuthor(), book.getId())) {
            bookResult.setTitle(book.getTitle());
            bookResult.setAuthor(book.getAuthor());
            bookResult.setError(BOOK_EXISTS);
        } else {
            bookRepository.save(book);
            bookResult.setMessage(UPDATE_SUCCESS);
            bookResult.setSuccess(true);
        }
        return bookResult;
    }

    public BooksResponse deleteBooksByIDs(final JSONArray bookIDs) {
        final BooksResponse booksResponse = new BooksResponse();
        boolean success = true;
        for (final Object bookIDObject : bookIDs) {
            final int bookID = (int) bookIDObject;
            final BookResult bookResult = deleteBookByID(bookID);
            success = success && bookResult.isSuccess();
            booksResponse.addBookResult(bookResult);
        }
        booksResponse.setSuccess(success);
        return booksResponse;
    }

    private BookResult deleteBookByID(final int bookID) {
        final BookResult bookResult = new BookResult();
        bookResult.setId(bookID);
        boolean bookExists = bookRepository.existsById(bookID);
        if (bookExists) {
            bookRepository.deleteById(bookID);
            bookResult.setMessage(DELETE_SUCCESS);
            bookResult.setSuccess(true);
        } else {
            bookResult.setError(BOOK_DOES_NOT_EXIST);
            bookResult.setSuccess(false);
        }
        return bookResult;
    }

    public boolean bookExists(final String title, final String author) {
        List<Book> books = bookRepository.findByTitleAndAuthor(title, author);
        return !books.isEmpty();
    }
    public boolean similarBookExists(final String title, final String author, final int id) {
        List<Book> books = bookRepository.findByTitleAndAuthorAndIdNot(title, author, id);
        return !books.isEmpty();
    }
}