package io.bascom.model;

import io.bascom.model.Book.BookField;
import io.bascom.util.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class BookFilter {

    private static final Pattern yearPattern = Pattern.compile("\\[[0-9]+ TO [0-9]+]");
    private String title;
    private boolean titleExactMatch;
    private String author;
    private boolean authorExactMatch;
    private String series;
    private boolean seriesExactMatch;
    private String genre;
    private boolean genreExactMatch;
    private String year;
    private int yearMinValue;
    private int yearMaxValue;
    private Integer rating;
    private boolean ratingExactMatch;
    private Integer limit;
    private Map<String, Boolean> sort = new LinkedHashMap<>();

    public BookFilter() {
    }

    public BookFilter addTitle(String input) {
        if (StringUtil.isNotEmpty(input)) {
            title = input.replace("~", "");
            titleExactMatch = !input.startsWith("~");
        }
        return this;
    }

    public String getTitle() {
        return title;
    }

    public boolean getTitleExactMatch() {
        return titleExactMatch;
    }

    public BookFilter addAuthor(String input) {
        if (StringUtil.isNotEmpty(input)) {
            author = input.replace("~", "");
            authorExactMatch = !input.startsWith("~");
        }
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public boolean getAuthorExactMatch() {
        return authorExactMatch;
    }

    public BookFilter addSeries(String input) {
        if (StringUtil.isNotEmpty(input)) {
            series = input.replace("~", "");
            seriesExactMatch = !input.startsWith("~");
        }
        return this;
    }

    public String getSeries() {
        return series;
    }

    public boolean getSeriesExactMatch() {
        return seriesExactMatch;
    }

    public BookFilter addGenre(String input) {
        if (StringUtil.isNotEmpty(input)) {
            genre = input.replace("~", "");
            genreExactMatch = !input.startsWith("~");
        }
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public boolean getGenreExactMatch() {
        return genreExactMatch;
    }

    public BookFilter addYear(String input) {
        if (StringUtil.isEmpty(input)) {
            year = null;
        } else {
            year = input;
            if (StringUtil.isNumber(input)) {
                yearMinValue = Integer.parseInt(input);
                yearMaxValue = yearMinValue;
            } else if (yearPattern.matcher(input).matches()) {
                String[] parts = input.replace("[", "").replace("]", "").split(" ");
                yearMinValue = Integer.parseInt(parts[0]);
                yearMaxValue = Integer.parseInt(parts[2]);
            }
        }
        return this;
    }

    public String getYear() {
        return year;
    }

    public int getYearMinValue() {
        return yearMinValue;
    }

    public int getYearMaxValue() {
        return yearMaxValue;
    }

    public BookFilter addRating(String input) {
        if (StringUtil.isNotEmpty(input)) {
            String temp = input.replace(">", "");
            if (StringUtil.isNumber(temp)) {
                rating = Integer.valueOf(temp);
                ratingExactMatch = !input.startsWith(">");
            }
        }
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public boolean getRatingExactMatch() {
        return ratingExactMatch;
    }

    public BookFilter addLimit(Integer input) {
        limit = input;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public BookFilter addSort(String input) {
        if (StringUtil.isNotEmpty(input)) {
            String[] fields = input.split(",");
            for (String field : fields) {
                String temp = field.replaceAll("^[-+]", "");
                if (BookField.isValidField(temp)) {
                    String bookField = BookField.valueOf(temp.toUpperCase()).value;
                    boolean isAscending = !field.startsWith("-");
                    sort.put(bookField, isAscending);
                }
            }
        }
        return this;
    }

    public Map<String, Boolean> getSort() {
        return sort;
    }
}
