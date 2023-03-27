import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private Map<String, Book> bookMap = new HashMap<>(); // In-memory store for books

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Book with same ID already exists
        }
        bookMap.put(book.getBookId(), book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Void> updateBook(@PathVariable("bookId") String bookId, @RequestBody Book book) {
        if (!bookMap.containsKey(bookId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Book with given ID not found
        }
        bookMap.put(bookId, book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable("bookId") String bookId) {
        if (!bookMap.containsKey(bookId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Book with given ID not found
        }
        bookMap.remove(bookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable("bookId") String bookId) {
        Book book = bookMap.get(bookId);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Book with given ID not found
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookList = new ArrayList<>(bookMap.values());
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam("author") String authorName) {
        List<Book> matchingBooks = bookMap.values().stream()
                .filter(book -> book.getAuthorName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
        return ResponseEntity.ok(matchingBooks);
    }
}