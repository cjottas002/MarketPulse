package es.market.pulse.repository

import es.market.pulse.database.ProductDao
import es.market.pulse.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ProductRepositoryTest {

    // Mock del ProductDao
    private lateinit var productDao: ProductDao

    // Repositorio a testear
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this)
        productDao = mock(ProductDao::class.java)

        // Crear la instancia del repositorio usando el mock del ProductDao
        productRepository = ProductRepository(productDao)
    }

    @Test
    fun `getAllProducts should return list of products from dao`() {
        // Given: una lista simulada de productos en la base de datos
        val mockProducts = listOf(
            Product(id = 1, name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 100),
            Product(id = 2, name = "Product 2", description = "Desc 2", price = 20.0, imageUrl = "http://example.com/product2.jpg", stockQuantity = 200)
        )
        `when`(productDao.getAllProducts()).thenReturn(mockProducts)

        // When: Llamas al repositorio para obtener todos los productos
        val products = productRepository.getAllProducts()

        // Then: Verificas que la lista retornada es la lista simulada
        assertEquals(mockProducts, products)
        verify(productDao).getAllProducts() // Verificar que se haya llamado al método del DAO
    }

    @Test
    fun `getProductById should return product from dao`() {
        // Given: Un producto simulado en la base de datos
        val mockProduct = Product(id = 1, name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 100)
        `when`(productDao.getProductById(1)).thenReturn(mockProduct)

        // When: Llamas al repositorio para obtener un producto por ID
        val product = productRepository.getProductById(1)

        // Then: Verificas que el producto devuelto sea el simulado
        assertEquals(mockProduct, product)
        verify(productDao).getProductById(1)
    }

    @Test
    fun `getProductById should return null if product not found`() {
        // Given: Ningún producto en la base de datos para ese ID
        `when`(productDao.getProductById(1)).thenReturn(null)

        // When: Llamas al repositorio para obtener un producto por ID
        val product = productRepository.getProductById(1)

        // Then: Verificas que el resultado sea null
        assertNull(product)
        verify(productDao).getProductById(1)
    }

    @Test
    fun `insertProduct should call insertProduct on dao`() = runBlocking {
        // Given: Un producto simulado
        val mockProduct = Product(id = 1, name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 100)

        // When: Llamas al repositorio para agregar un producto
        productRepository.insertProduct(mockProduct)

        // Then: Verificas que se haya llamado al método insertProduct del DAO
        verify(productDao).insertProduct(mockProduct)
    }

    @Test
    fun `updateProduct should call updateProduct on dao`() = runBlocking {
        // Given: Un producto simulado
        val mockProduct = Product(id = 1, name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 100)

        // When: Llamas al repositorio para actualizar un producto
        productRepository.updateProduct(mockProduct)

        // Then: Verificas que se haya llamado al método updateProduct del DAO
        verify(productDao).updateProduct(mockProduct)
    }

    @Test
    fun `deleteProduct should call deleteProduct on dao`() = runBlocking {
        // Given: Un producto simulado
        val mockProduct = Product(id = 1, name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 100)

        // When: Llamas al repositorio para eliminar un producto
        productRepository.deleteProduct(mockProduct)

        // Then: Verificas que se haya llamado al método deleteProduct del DAO
        verify(productDao).deleteProduct(mockProduct)
    }
}
