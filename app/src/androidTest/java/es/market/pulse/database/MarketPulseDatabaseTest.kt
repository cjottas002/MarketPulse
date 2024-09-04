package es.market.pulse.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import es.market.pulse.model.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MarketPulseDatabaseTest {

    private lateinit var db: MarketPulseDatabase
    private lateinit var productDao: ProductDao
    private lateinit var userDao: UserDao
    private lateinit var roleDao: RoleDao
    private lateinit var cartDao: CartDao
    private lateinit var cartItemDao: CartItemDao
    private lateinit var addressDao: AddressDao
    private lateinit var orderDao: OrderDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MarketPulseDatabase::class.java)
            .allowMainThreadQueries() // Permite consultas en el hilo principal para pruebas
            .build()

        productDao = db.productDao()
        userDao = db.userDao()
        roleDao = db.roleDao()
        cartDao = db.cartDao()
        cartItemDao = db.cartItemDao()
        addressDao = db.addressDao()
        orderDao = db.orderDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    // ----------------------------------------
    // Pruebas para ProductDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveProduct() = runBlocking {
        val product = Product(
            name = "Milk",
            description = "1L whole milk",
            price = 1.29,
            imageUrl = "https://example.com/milk.jpg",
            stockQuantity = 10
        )
        productDao.insertProduct(product)

        val products = productDao.getAllProducts()
        assertEquals(1, products.size)
        assertEquals("Milk", products[0].name)
        assertEquals(1.29, products[0].price, 0.0)
    }

    @Test
    fun testUpdateProduct() = runBlocking {
        val product = Product(
            name = "Milk",
            description = "1L whole milk",
            price = 1.29,
            imageUrl = "https://example.com/milk.jpg",
            stockQuantity = 10
        )
        productDao.insertProduct(product)

        val updatedProduct = product.copy(
            id = 1,
            name = "Updated Milk",
            price = 1.50
        )
        productDao.updateProduct(updatedProduct)

        val products = productDao.getAllProducts()
        assertEquals(1, products.size)
        assertEquals("Updated Milk", products[0].name)
        assertEquals(1.50, products[0].price, 0.0)
    }

    @Test
    fun testDeleteProduct() = runBlocking {
        val product = Product(
            name = "Milk",
            description = "1L whole milk",
            price = 1.29,
            imageUrl = "https://example.com/milk.jpg",
            stockQuantity = 10
        )
        productDao.insertProduct(product)

        val productsBeforeDelete = productDao.getAllProducts()
        assertEquals(1, productsBeforeDelete.size)

        productDao.deleteProduct(productsBeforeDelete[0])

        val productsAfterDelete = productDao.getAllProducts()
        assertEquals(0, productsAfterDelete.size)
    }

    // ----------------------------------------
    // Pruebas para UserDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveUser() = runBlocking {
        val role = Role(roleName = "User")
        roleDao.insertRole(role)
        val roleId = roleDao.getRoleByName("User").id

        val user = User(
            username = "testuser",
            email = "testuser@example.com",
            passwordHash = "hashedpassword",
            roleId = roleId
        )
        userDao.insertUser(user)

        val users = userDao.getAllUsers()
        assertEquals(1, users.size)
        assertEquals("testuser", users[0].username)
    }

    @Test
    fun testUpdateUser() = runBlocking {
        val role = Role(roleName = "User")
        roleDao.insertRole(role)
        val roleId = roleDao.getRoleByName("User").id

        val user = User(
            username = "testuser",
            email = "testuser@example.com",
            passwordHash = "hashedpassword",
            roleId = roleId
        )
        userDao.insertUser(user)

        val updatedUser = user.copy(
            id = 1,
            email = "updateduser@example.com"
        )
        userDao.updateUser(updatedUser)

        val users = userDao.getAllUsers()
        assertEquals(1, users.size)
        assertEquals("updateduser@example.com", users[0].email)
    }

    @Test
    fun testDeleteUser() = runBlocking {
        val role = Role(roleName = "User")
        roleDao.insertRole(role)
        val roleId = roleDao.getRoleByName("User").id

        val user = User(
            username = "testuser",
            email = "testuser@example.com",
            passwordHash = "hashedpassword",
            roleId = roleId
        )
        userDao.insertUser(user)

        val usersBeforeDelete = userDao.getAllUsers()
        assertEquals(1, usersBeforeDelete.size)

        userDao.deleteUser(usersBeforeDelete[0])

        val usersAfterDelete = userDao.getAllUsers()
        assertEquals(0, usersAfterDelete.size)
    }

    // ----------------------------------------
    // Pruebas para RoleDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveRole() = runBlocking {
        val role = Role(roleName = "Admin")
        roleDao.insertRole(role)

        val roles = roleDao.getAllRoles()
        assertEquals(1, roles.size)
        assertEquals("Admin", roles[0].roleName)
    }

    // ----------------------------------------
    // Pruebas para CartDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveCart() = runBlocking {
        val role = Role(roleName = "User")
        roleDao.insertRole(role)
        val roleId = roleDao.getRoleByName("User").id

        val user = User(
            username = "user1",
            email = "user1@example.com",
            passwordHash = "hashedpassword",
            roleId = roleId
        )
        userDao.insertUser(user)

        val cart = Cart(userId = userDao.getAllUsers()[0].id)
        cartDao.insertCart(cart)

        val carts = cartDao.getAllCarts()
        assertEquals(1, carts.size)
        assertEquals(userDao.getAllUsers()[0].id, carts[0].userId)
    }

    @Test
    fun testDeleteCartWithCascadeDeleteCartItems() = runBlocking {
        // Insertar un usuario y carrito
        val role = Role(roleName = "User")
        roleDao.insertRole(role)
        val roleId = roleDao.getRoleByName("User").id

        val user = User(username = "user1", email = "user1@example.com", passwordHash = "hashedpassword", roleId = roleId)
        userDao.insertUser(user)
        val cart = Cart(userId = userDao.getAllUsers()[0].id)
        cartDao.insertCart(cart)
        val cartId = cartDao.getAllCarts()[0].id

        // Insertar productos e ítems en el carrito
        val product = Product(name = "Product 1", description = "Desc 1", price = 10.0, imageUrl = "http://example.com/product1.jpg", stockQuantity = 5)
        productDao.insertProduct(product)

        val cartItem = CartItem(cartId = cartId, productId = productDao.getAllProducts()[0].id, quantity = 2)
        cartItemDao.insertCartItem(cartItem)

        // Verificar que el ítem esté presente
        val cartItemsBeforeDelete = cartItemDao.getAllCartItems()
        assertEquals(1, cartItemsBeforeDelete.size)

        // Eliminar el carrito y verificar que los ítems en cascada se eliminen
        cartDao.deleteCart(cartDao.getAllCarts()[0].id)
        val cartItemsAfterDelete = cartItemDao.getAllCartItems()
        assertEquals(0, cartItemsAfterDelete.size)
    }

    // ----------------------------------------
    // Pruebas para AddressDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveAddress() = runBlocking {
        val address = Address(
            street = "123 Main St",
            city = "Springfield",
            postalCode = 12345,
            country = "USA"
        )
        addressDao.insertAddress(address)

        val addresses = addressDao.getAllAddresses()
        assertEquals(1, addresses.size)
        assertEquals("123 Main St", addresses[0].street)
    }

    // ----------------------------------------
    // Pruebas para OrderDao
    // ----------------------------------------
    @Test
    fun testInsertAndRetrieveOrder() = runBlocking {
        val address = Address(
            street = "123 Main St",
            city = "Springfield",
            postalCode = 12345,
            country = "USA"
        )
        addressDao.insertAddress(address)

        val order = Order(
            userId = 1,
            shippingAddressId = addressDao.getAllAddresses()[0].id,
            orderDate = System.currentTimeMillis(),
            status = "Pending",
            productList = listOf()
        )
        orderDao.insertOrder(order)

        val orders = orderDao.getAllOrders()
        assertEquals(1, orders.size)
        assertEquals("Pending", orders[0].status)
    }
}
