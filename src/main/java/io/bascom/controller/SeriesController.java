package io.bascom.controller;

import io.bascom.model.Book;
import io.bascom.model.BookFilter;
import io.bascom.service.SeriesService;
import io.bascom.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.List;

import static io.bascom.util.Constants.JSON_UTF8;

@RestController
@RequestMapping("/v1")
class SeriesController {

    @Autowired
    SeriesService seriesService;

    @GetMapping(value = "/series/{name}/books", produces = JSON_UTF8)
    public ResponseEntity<String> getBooksBySeries
            (@PathVariable("name") final String name,
             @QueryParam("sort") final String sort,
             @QueryParam("limit") final Integer limit
            ) {
        final BookFilter filter = new BookFilter()
                .addSeries(name)
                .addSort(sort)
                .addLimit(limit);
        final List<Book> books = seriesService.getBooks(filter);
        return ResponseEntity
                .status(books.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONUtil.generateResponse(books));
    }
}