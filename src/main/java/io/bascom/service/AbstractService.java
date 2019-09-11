package io.bascom.service;

import io.bascom.model.Book;
import io.bascom.model.BookFilter;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> getBooks(final BookFilter filter) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Book> query = cb.createQuery(Book.class);
        final Root<Book> book = query.from(Book.class);
        final Predicate[] predicates = genereateWhereCriteria(book, cb, filter);
        query.select(book).where(cb.and(predicates));
        final OrderImpl orderImpl = generateOrderBy(book, filter);
        query.orderBy(orderImpl);
        return entityManager.createQuery(query).getResultList();
    }

    private Predicate[] genereateWhereCriteria(final Root<Book> book, final CriteriaBuilder cb, final BookFilter filter) {
        final List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(filter.getTitle())) {
            Path<String> titleField = book.get("title");
            predicates.add(cb.like(titleField, "%" + filter.getTitle() + "%"));
        }
        if (!StringUtils.isEmpty(filter.getAuthor())) {
            Path<String> authorField = book.get("author");
            predicates.add(cb.like(authorField, "%" + filter.getAuthor() + "%"));
        }
        if (filter.getYear() != null) {
            Path<String> yearField = book.get("year");
            predicates.add(cb.equal(yearField, filter.getYear()));
        }
        if (!StringUtils.isEmpty(filter.getSeries())) {
            Path<String> seriesField = book.get("series");
            predicates.add(cb.like(seriesField, "%" + filter.getSeries() + "%"));
        }
        if (!StringUtils.isEmpty(filter.getGenre())) {
            Path<String> genreField = book.get("genre");
            predicates.add(cb.like(genreField, "%" + filter.getGenre() + "%"));
        }
        if (filter.getRating() != null) {
            Path<String> ratingField = book.get("rating");
            predicates.add(cb.equal(ratingField, filter.getRating()));
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private OrderImpl generateOrderBy(final Root<Book> book, final BookFilter filter) {
        String orderBy = "title";
        boolean ascending = true;
        if (!filter.getSort().isEmpty()) {
            orderBy = filter.getSort().keySet().stream().findFirst().orElse("title");
            ascending = filter.getSort().getOrDefault(orderBy, true);
        }
        Path<String> field = book.get(orderBy);
        return new OrderImpl(field, ascending);
    }

}
