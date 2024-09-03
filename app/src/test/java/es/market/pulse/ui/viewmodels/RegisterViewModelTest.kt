import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.market.pulse.model.User
import es.market.pulse.repository.UserRepository
import es.market.pulse.ui.viewmodels.RegisterViewModel
import es.market.pulse.utils.Helpers
import es.market.pulse.utils.RoleType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var registerViewModel: RegisterViewModel

    @Captor
    private lateinit var userCaptor: ArgumentCaptor<User>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        registerViewModel = RegisterViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Restablece el dispatcher principal a su valor original
    }

    @Test
    fun `register with valid data returns true`() = runTest {
        // Arrange
        val name = "testUser"
        val email = "test@example.com"
        val password = "password"
        val hashedPassword = Helpers.hashPassword(password)

        val user = User(
            id = 0,  // Asume que el ID es generado automáticamente por la base de datos
            username = name,
            email = email,
            passwordHash = hashedPassword,
            addressId = null,
            roleId = RoleType.USER.ordinal
        )

        // Simula la adición exitosa del usuario en el repositorio
        doAnswer { }.`when`(userRepository).addUser(user)

        // Act
        val result = registerViewModel.register(name, email, password)

        // Avanza el tiempo para que las corrutinas se ejecuten completamente
        advanceUntilIdle()

        // Agrega un pequeño retraso para asegurar que todo se sincroniza correctamente (esto es más una medida de seguridad)
        Thread.sleep(500)

        // Assert
        assertTrue(result)
        verify(userRepository).addUser(user)
        assertEquals(user.email, email)
    }

}
