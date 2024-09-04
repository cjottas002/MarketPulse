package es.market.pulse.utils

import com.google.gson.Gson
import es.market.pulse.model.Product
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {

    private val converters = Converters()

    @Test
    fun testFromProductList() {
        // Given
        val productList = listOf(
            Product(
                id = 1,
                name = "Product 1",
                price = 10.0,
                description = "Description 1",
                imageUrl = "http://example.com/product1.jpg",
                stockQuantity = 100
            ),
            Product(
                id = 2,
                name = "Product 2",
                price = 20.0,
                description = "Description 2",
                imageUrl = "http://example.com/product2.jpg",
                stockQuantity = 200
            )
        )

        // When
        val json = converters.fromProductList(productList)

        // Then
        val expectedJson = Gson().toJson(productList) // Compare with expected JSON
        assertEquals(expectedJson, json)
    }

    @Test
    fun testToProductList() {
        // Given
        val json = """[
            {"id":1,"name":"Product 1","price":10.0,"description":"Description 1","imageUrl":"http://example.com/product1.jpg","stockQuantity":100},
            {"id":2,"name":"Product 2","price":20.0,"description":"Description 2","imageUrl":"http://example.com/product2.jpg","stockQuantity":200}
        ]"""

        // When
        val productList = converters.toProductList(json)

        // Then
        val expectedProductList = listOf(
            Product(
                id = 1,
                name = "Product 1",
                price = 10.0,
                description = "Description 1",
                imageUrl = "http://example.com/product1.jpg",
                stockQuantity = 100
            ),
            Product(
                id = 2,
                name = "Product 2",
                price = 20.0,
                description = "Description 2",
                imageUrl = "http://example.com/product2.jpg",
                stockQuantity = 200
            )
        )
        assertEquals(expectedProductList, productList)
    }
}
