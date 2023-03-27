public class BookDao {
    private Map<String, Book> bookMap = DatabaseConnector.getBookMap();

    public void addBook(Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            throw new DatabaseException("Book with same ID already exists");
        }
        bookMap.put(book.getBookId(), book);
    }

    public void updateBook(String bookId, Book book) {
        if (!bookMap.containsKey(bookId)) {
            throw new DatabaseException("Book with given ID not found");
        }
        bookMap.put(bookId, book);
    }

    public void deleteBook(String bookId) {
        if (!bookMap.containsKey(bookId)) {
            throw new DatabaseException("Book with given ID not found");
        }
        bookMap.remove(bookId);
    }

    public Book getBook(String bookId) {
        Book book = bookMap.get(bookId);
        if (book == null) {
            throw new DatabaseException("Book with given ID not found");
        }
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookMap.values());
    }

    public List<Book> searchBooks(String authorName) {
        return bookMap.values().stream()
                .filter(book -> book.getAuthorName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }
}