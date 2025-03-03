package org.example.Amazon;

import org.example.Amazon.Cost.PriceRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AmazonUnitTest {
    private ShoppingCart mockCart;
    private PriceRule mockRule;
    private Amazon amazon;

    @BeforeEach
    void setUp() {
        mockCart = mock(ShoppingCart.class);
        mockRule = mock(PriceRule.class);
        amazon = new Amazon(mockCart, List.of(mockRule));
    }

    @Test
    @DisplayName("specification-based - Test calculate method with mock rules")
    void testCalculate() {
        when(mockCart.getItems()).thenReturn(List.of());
        when(mockRule.priceToAggregate(anyList())).thenReturn(100.0);

        double result = amazon.calculate();
        assertEquals(100.0, result);
    }

    @Test
    @DisplayName("specification-based - Test addToCart method")
    void testAddToCart() {
        Item mockItem = mock(Item.class);
        amazon.addToCart(mockItem);
        verify(mockCart, times(1)).add(mockItem);
    }
}