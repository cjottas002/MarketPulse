package es.market.pulse.repository

import es.market.pulse.database.UserDao
import es.market.pulse.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class UserRepositoryTest {

    // Mock del UserDao
    private lateinit var userDao: UserDao

    // Repositorio a testear
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this)
        userDao = mock(UserDao::class.java)

        // Crear la instancia del repositorio usando el mock del UserDao
        userRepository = UserRepository(userDao)
    }

    @Test
    fun `getAllUsers should return list of users from dao`() {
        // Given: una lista simulada de usuarios en la base de datos
        val mockUsers = listOf(
            User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1),
            User(id = 2, username = "User2", email = "user2@example.com", passwordHash = "hash2", roleId = 2)
        )
        `when`(userDao.getAllUsers()).thenReturn(mockUsers)

        // When: Llamas al repositorio para obtener todos los usuarios
        val users = userRepository.getAllUsers()

        // Then: Verificas que la lista retornada es la lista simulada
        assertEquals(mockUsers, users)
        verify(userDao).getAllUsers() // Verificar que se haya llamado al método del DAO
    }

    @Test
    fun `getUserById should return user from dao`() {
        // Given: Un usuario simulado en la base de datos
        val mockUser = User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1)
        `when`(userDao.getUserById(1)).thenReturn(mockUser)

        // When: Llamas al repositorio para obtener un usuario por ID
        val user = userRepository.getUserById(1)

        // Then: Verificas que el usuario devuelto sea el simulado
        assertEquals(mockUser, user)
        verify(userDao).getUserById(1)
    }

    @Test
    fun `getUserById should return null if user not found`() {
        // Given: Ningún usuario en la base de datos para ese ID
        `when`(userDao.getUserById(1)).thenReturn(null)

        // When: Llamas al repositorio para obtener un usuario por ID
        val user = userRepository.getUserById(1)

        // Then: Verificas que el resultado sea null
        assertNull(user)
        verify(userDao).getUserById(1)
    }

    @Test
    fun `getUserByEmail should return user from dao`() {
        // Given: Un usuario simulado en la base de datos
        val mockUser = User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1)
        `when`(userDao.getUserByEmail("user1@example.com")).thenReturn(mockUser)

        // When: Llamas al repositorio para obtener un usuario por email
        val user = userRepository.getUserByEmail("user1@example.com")

        // Then: Verificas que el usuario devuelto sea el simulado
        assertEquals(mockUser, user)
        verify(userDao).getUserByEmail("user1@example.com")
    }

    @Test
    fun `getUserByEmail should return null if user not found`() {
        // Given: Ningún usuario en la base de datos para ese email
        `when`(userDao.getUserByEmail("user1@example.com")).thenReturn(null)

        // When: Llamas al repositorio para obtener un usuario por email
        val user = userRepository.getUserByEmail("user1@example.com")

        // Then: Verificas que el resultado sea null
        assertNull(user)
        verify(userDao).getUserByEmail("user1@example.com")
    }

    @Test
    fun `addUser should call insertUser on dao`() = runBlocking {
        // Given: Un usuario simulado
        val mockUser = User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1)

        // When: Llamas al repositorio para agregar un usuario
        userRepository.addUser(mockUser)

        // Then: Verificas que se haya llamado al método insertUser del DAO
        verify(userDao).insertUser(mockUser)
    }

    @Test
    fun `updateUser should call updateUser on dao`() = runBlocking {
        // Given: Un usuario simulado
        val mockUser = User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1)

        // When: Llamas al repositorio para actualizar un usuario
        userRepository.updateUser(mockUser)

        // Then: Verificas que se haya llamado al método updateUser del DAO
        verify(userDao).updateUser(mockUser)
    }

    @Test
    fun `deleteUser should call deleteUser on dao`() = runBlocking {
        // Given: Un usuario simulado
        val mockUser = User(id = 1, username = "User1", email = "user1@example.com", passwordHash = "hash1", roleId = 1)

        // When: Llamas al repositorio para eliminar un usuario
        userRepository.deleteUser(mockUser)

        // Then: Verificas que se haya llamado al método deleteUser del DAO
        verify(userDao).deleteUser(mockUser)
    }
}
