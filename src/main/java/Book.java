import java.math.BigDecimal;

public class Book {
    private String bookId;
    private String authorName;
    private String bookTitle;
    private BigDecimal bookPrice;

    public Book() {
    }

    public Book(String bookId, String authorName, String bookTitle, BigDecimal bookPrice) {
        this.bookId = bookId;
        this.authorName = authorName;
        this.bookTitle = bookTitle;
        this.bookPrice = bookPrice;
    }

    // getters and setters
}