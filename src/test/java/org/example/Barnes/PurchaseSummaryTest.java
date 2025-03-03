package org.example.Barnes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PurchaseSummaryTest {
    private PurchaseSummary purchaseSummary;
    private Book book;

    @BeforeEach
    void setUp() {
        purchaseSummary = new PurchaseSummary();
        book = new Book("12345", 10, 5);
    }

    @Test
    @DisplayName("specification-based: Test adding unavailable books")
    void testAddUnavailable() {
        purchaseSummary.addUnavailable(book, 2);
        assertEquals(2, purchaseSummary.getUnavailable().get(book));
    }

    @Test
    @DisplayName("specification-based: Test total price calculation")
    void testTotalPriceCalculation() {
        purchaseSummary.addToTotalPrice(50);
        assertEquals(50, purchaseSummary.getTotalPrice());
    }
}
