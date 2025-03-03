package org.example.Amazon;

import org.example.Amazon.Cost.ItemType;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AmazonIntegrationTest {
    private Database database;
    private ShoppingCartAdaptor cart;
    private Connection connection;

    @BeforeAll
    void setupDatabase() {
        database = new Database();
        cart = new ShoppingCartAdaptor(database);
        connection = database.getConnection();
    }

    @BeforeEach
    void resetDatabase() {
        database.resetDatabase();
    }

    @Test
    @DisplayName("specification-based - Add item to cart and verify database")
    void testAddItemToCart() throws SQLException {
        Item item = new Item(ItemType.ELECTRONIC, "Test Book", 2, 15.0);
        cart.add(item);

        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM shoppingcart");
        var rs = ps.executeQuery();
        rs.next();
        assertEquals(1, rs.getInt(1));
    }

    @AfterAll
    void closeDatabase() {
        database.close();
    }
}
