package io.bascom.repository;

import io.bascom.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {

    @Transactional
    List<Book> findByTitleAndAuthor(final String title, final String author);

    @Transactional
    List<Book> findByTitleAndAuthorAndIdNot(final String title, final String author, final int id);
}