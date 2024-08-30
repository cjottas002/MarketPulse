package es.market.pulse.repository

import es.market.pulse.database.ProductDao
import es.market.pulse.model.Product

class ProductRepository(private val productDao: ProductDao) {

    fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    fun getProductById(productId: Int): Product {
        return productDao.getProductById(productId)
    }

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun addProduct(product: Product) {
        productDao.insertProduct(product)
    }
}