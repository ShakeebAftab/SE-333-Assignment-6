package org.example.Barnes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.mockito.Mockito.*;

class BarnesAndNobleTest {
    private BookDatabase bookDatabase;
    private BuyBookProcess process;
    private BarnesAndNoble barnesAndNoble;

    @BeforeEach
    void setUp() {
        bookDatabase = mock(BookDatabase.class);
        process = mock(BuyBookProcess.class);
        barnesAndNoble = new BarnesAndNoble(bookDatabase, process);
    }

    @Test
    @DisplayName("specification-based: Valid book purchase")
    void testValidBookPurchase() {
        Book book = new Book("12345", 10, 5);
        when(bookDatabase.findByISBN("12345")).thenReturn(book);

        PurchaseSummary summary = barnesAndNoble.getPriceForCart(Map.of("12345", 2));

        assertEquals(20, summary.getTotalPrice());
        assertTrue(summary.getUnavailable().isEmpty());
        verify(process).buyBook(book, 2);
    }

    @Test
    @DisplayName("specification-based: Insufficient stock handling")
    void testInsufficientStock() {
        Book book = new Book("12345", 10, 3);
        when(bookDatabase.findByISBN("12345")).thenReturn(book);

        PurchaseSummary summary = barnesAndNoble.getPriceForCart(Map.of("12345", 5));

        assertEquals(30, summary.getTotalPrice());
        assertEquals(2, summary.getUnavailable().get(book));
        verify(process).buyBook(book, 3);
    }

    @Test
    @DisplayName("specification-based: Book not found in database")
    void testBookNotFound() {
        when(bookDatabase.findByISBN("99999")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            barnesAndNoble.getPriceForCart(Map.of("99999", 1));
        });
    }

    @Test
    @DisplayName("specification-based: Empty order cart")
    void testEmptyOrder() {
        PurchaseSummary summary = barnesAndNoble.getPriceForCart(Map.of());

        assertEquals(0, summary.getTotalPrice());
        assertTrue(summary.getUnavailable().isEmpty());
        verifyNoInteractions(process);
    }

    @Test
    @DisplayName("structural-based: Null order handling")
    void testNullOrder() {
        assertNull(barnesAndNoble.getPriceForCart(null));
    }

    @Test
    @DisplayName("structural-based: Multiple books in order")
    void testMultipleBooksOrder() {
        Book book1 = new Book("12345", 10, 5);
        Book book2 = new Book("67890", 15, 2);
        when(bookDatabase.findByISBN("12345")).thenReturn(book1);
        when(bookDatabase.findByISBN("67890")).thenReturn(book2);

        PurchaseSummary summary = barnesAndNoble.getPriceForCart(Map.of("12345", 2, "67890", 1));

        assertEquals(35, summary.getTotalPrice());
        verify(process).buyBook(book1, 2);
        verify(process).buyBook(book2, 1);
    }

    @Test
    @DisplayName("structural-based: Exact stock match")
    void testExactStockMatch() {
        Book book = new Book("12345", 10, 3);
        when(bookDatabase.findByISBN("12345")).thenReturn(book);

        PurchaseSummary summary = barnesAndNoble.getPriceForCart(Map.of("12345", 3));

        assertEquals(30, summary.getTotalPrice());
        assertTrue(summary.getUnavailable().isEmpty());
        verify(process).buyBook(book, 3);
    }

    @Test
    @DisplayName("structural-based: Exception handling when book not found")
    void testExceptionHandlingForMissingBook() {
        when(bookDatabase.findByISBN("11111")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            barnesAndNoble.getPriceForCart(Map.of("11111", 2));
        });
    }
}