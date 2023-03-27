public class DatabaseConnector {
    private static Map<String, Book> bookMap = new HashMap<>();

    public static Map<String, Book> getBookMap() {
        return bookMap;
    }
}
