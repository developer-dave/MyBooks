package io.bascom.controller;

import io.bascom.model.Book;
import io.bascom.model.BookFilter;
import io.bascom.model.BooksResponse;
import io.bascom.service.BookService;
import io.bascom.util.JSONUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

import static io.bascom.util.Constants.JSON_UTF8;

@RestController
@RequestMapping("/v1")
class BookController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/books", produces = JSON_UTF8)
    public ResponseEntity<String> getBooks(
            @QueryParam("title") final String title,
            @QueryParam("author") final String author,
            @QueryParam("series") final String series,
            @QueryParam("genre") final String genre,
            @QueryParam("year") final String year,
            @QueryParam("rating") final String rating,
            @QueryParam("sort") final String sort,
            @QueryParam("limit") final Integer limit
    ) {
        final BookFilter filter = new BookFilter()
                .addTitle(title)
                .addAuthor(author)
                .addSeries(series)
                .addGenre(genre)
                .addYear(year)
                .addRating(rating)
                .addSort(sort)
                .addLimit(limit);
        final List<Book> books = bookService.getBooks(filter);
        return ResponseEntity
                .status(books.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONUtil.generateResponse(books));
    }

    @PostMapping(value = "/books", consumes = JSON_UTF8, produces = JSON_UTF8)
    public ResponseEntity createBooks(@RequestBody final List<Book> books) {
        final BooksResponse booksResponse = bookService.createBooks(books);
        return ResponseEntity
                .status(booksResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONUtil.generateResponse(booksResponse.getBookResults()));
    }

    @PutMapping(value = "/books", consumes = JSON_UTF8, produces = JSON_UTF8)
    public ResponseEntity updateBooks(@RequestBody final List<Book> books) {
        final BooksResponse booksResponse = bookService.updateBooks(books);
        return ResponseEntity
                .status(booksResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONUtil.generateResponse(booksResponse.getBookResults()));
    }

    @DeleteMapping(value = "/books", consumes = JSON_UTF8, produces = JSON_UTF8)
    public ResponseEntity deleteBooks(@RequestBody final String jsonArray) {
        final JSONArray bookIDs = new JSONArray(jsonArray);
        if (bookIDs.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JSONUtil.generateResponse("Please provide at least one book id."));
        }
        final BooksResponse booksResponse = bookService.deleteBooksByIDs(bookIDs);
        return ResponseEntity
                .status(booksResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONUtil.generateResponse(booksResponse.getBookResults()));
    }
}