package es.market.pulse.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.market.pulse.model.Product
import es.market.pulse.model.User
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import es.market.pulse.model.*
import es.market.pulse.utils.Constants
import es.market.pulse.utils.Converters
import es.market.pulse.utils.StringValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Product::class, Role::class, Cart::class, CartItem::class, Address::class, Order::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MarketPulseDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao
    abstract fun roleDao(): RoleDao
    abstract fun cartDao(): CartDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun orderDao(): OrderDao
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE: MarketPulseDatabase? = null

        fun getDatabase(context: Context): MarketPulseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarketPulseDatabase::class.java,
                    Constants.DATABASE_NAME
                ).addCallback(MarketPulseDatabaseCallback(context)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    class MarketPulseDatabaseCallback(private val context: Context) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            populateDatabase(
                getDatabase(context).roleDao(),
                getDatabase(context).userDao(),
                getDatabase(context).productDao(),
                getDatabase(context).addressDao()
            )

        }

        private fun populateDatabase(
            roleDao: RoleDao,
            userDao: UserDao,
            productDao: ProductDao,
            addressDao: AddressDao,
        ) {
            CoroutineScope(Dispatchers.IO).launch {

                // Pre-populate the roles table
                val adminRole = Role(roleName = "Admin")
                val userRole = Role(roleName = "User")
                val providerRole = Role(roleName = "Provider")

                roleDao.insertRole(adminRole)
                roleDao.insertRole(userRole)
                roleDao.insertRole(providerRole)

                // Get the IDs of the roles for use
                val adminRoleId = roleDao.getRoleByName("Admin").id
                val userRoleId = roleDao.getRoleByName("User").id
                val providerRoleId = roleDao.getRoleByName("Provider").id

                // Pre-populate the addresses table
                val addresses = listOf(
                    Address(
                        street = "123 Main St",
                        city = "Springfield",
                        postalCode = 12345,
                        country = "USA"
                    ),
                    Address(
                        street = "456 Elm St",
                        city = "Shelbyville",
                        postalCode = 67890,
                        country = "USA"
                    ),
                    Address(
                        street = "789 Maple St",
                        city = "Ogdenville",
                        postalCode = 11223,
                        country = "USA"
                    )
                )
                addresses.forEach { addressDao.insertAddress(it) }

                // Create example users
                val users = listOf(
                    User(
                        username = "admin",
                        email = "admin@example.com",
                        passwordHash = StringValidator.hashPassword("admin"),
                        addressId = 1,
                        roleId = adminRoleId
                    ),
                    User(
                        username = "usu1",
                        email = "usu1@example.com",
                        passwordHash = StringValidator.hashPassword("pass1"),
                        addressId = 2,
                        roleId = userRoleId
                    ),
                    User(
                        username = "usu2",
                        email = "usu2@example.com",
                        passwordHash = StringValidator.hashPassword("pass2"),
                        addressId = 3,
                        roleId = userRoleId
                    ),
                    User(
                        username = "usu3",
                        email = "usu3@example.com",
                        passwordHash = StringValidator.hashPassword("pass3"),
                        roleId = userRoleId
                    ),
                    User(
                        username = "pro1",
                        email = "pro1@example.com",
                        passwordHash = StringValidator.hashPassword("pro1"),
                        roleId = providerRoleId
                    )
                )
                users.forEach { userDao.insertUser(it) }

                // Create example products
                val products = listOf(
                    Product(
                        name = "Milk",
                        description = "1L whole milk",
                        price = 1.29,
                        imageUrl = "https://example.com/milk.jpg",
                        stockQuantity = 10
                    ),
                    Product(
                        name = "Bread",
                        description = "Whole wheat bread",
                        price = 0.99,
                        imageUrl = "https://example.com/bread.jpg",
                        stockQuantity = 5
                    ),
                    Product(
                        name = "Eggs",
                        description = "12 large eggs",
                        price = 2.49,
                        imageUrl = "https://example.com/eggs.jpg",
                        stockQuantity = 8
                    ),
                    Product(
                        name = "Butter",
                        description = "Unsalted butter 200g",
                        price = 2.99,
                        imageUrl = "https://example.com/butter.jpg",
                        stockQuantity = 3
                    ),
                    Product(
                        name = "Cheese",
                        description = "Cheddar cheese 250g",
                        price = 3.49,
                        imageUrl = "https://example.com/cheese.jpg",
                        stockQuantity = 6
                    ),
                    Product(
                        name = "Apples",
                        description = "1kg red apples",
                        price = 2.79,
                        imageUrl = "https://example.com/apples.jpg",
                        stockQuantity = 12
                    ),
                    Product(
                        name = "Bananas",
                        description = "1kg bananas",
                        price = 1.49,
                        imageUrl = "https://example.com/bananas.jpg",
                        stockQuantity = 7
                    ),
                    Product(
                        name = "Tomatoes",
                        description = "1kg fresh tomatoes",
                        price = 2.99,
                        imageUrl = "https://example.com/tomatoes.jpg",
                        stockQuantity = 9
                    ),
                    Product(
                        name = "Chicken Breast",
                        description = "500g chicken breast",
                        price = 4.99,
                        imageUrl = "https://example.com/chicken.jpg",
                        stockQuantity = 4
                    ),
                    Product(
                        name = "Orange Juice",
                        description = "1L fresh orange juice",
                        price = 3.99,
                        imageUrl = "https://example.com/juice.jpg",
                        stockQuantity = 11
                    )
                )
                products.forEach { productDao.insertProduct(it) }
            }
        }
    }
}
