package org.example.Barnes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookTest {
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book("12345", 20, 5);
        book2 = new Book("12345", 20, 5);
    }

    @Test
    @DisplayName("specification-based: Test Book equality")
    void testBookEquality() {
        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    @Test
    @DisplayName("structural-based: Test getters")
    void testGetters() {
        assertEquals(20, book1.getPrice());
        assertEquals(5, book1.getQuantity());
    }
}