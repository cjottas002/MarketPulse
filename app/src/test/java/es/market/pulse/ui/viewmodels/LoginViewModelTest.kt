package es.market.pulse.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import es.market.pulse.model.User
import es.market.pulse.repository.UserRepository
import es.market.pulse.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var observer: Observer<Boolean>

    private lateinit var loginViewModel: LoginViewModel

    // Para pruebas con corrutinas
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher) reemplaza temporalmente el Dispatcher.Main con un TestDispatcher (en este caso, StandardTestDispatcher),
//        permitiendo que las corrutinas que normalmente se ejecutarían en el hilo principal sean controladas dentro de un entorno de prueba.
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Restablece el dispatcher principal a su valor original
    }

    @Test
    fun `login with correct credentials returns true`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val hashedPassword = Helpers.hashPassword(password)

        val user = User(
            id = 1,
            username = "testUser",
            email = email,
            passwordHash = hashedPassword,
            addressId = null,
            roleId = 1
        )

        `when`(userRepository.getUserByEmail(email)).thenReturn(user)

        loginViewModel.loginResult.observeForever(observer)

        // Act
        loginViewModel.performLogin(email, password)

        // Avanza el tiempo para que las corrutinas se ejecuten
        testDispatcher.scheduler.advanceUntilIdle()

        // Agrega un pequeño retraso para asegurar que todo se sincroniza correctamente (esto es más una medida de seguridad)
        Thread.sleep(500)

        // Assert
        verify(observer).onChanged(true)
    }

    @Test
    fun `login with incorrect credentials returns false`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "wrongPassword"
        val hashedPassword = Helpers.hashPassword("password")

        val user = User(
            id = 1,
            username = "testUser",
            email = email,
            passwordHash = hashedPassword,
            addressId = null,
            roleId = 1
        )

        `when`(userRepository.getUserByEmail(email)).thenReturn(user)

        loginViewModel.loginResult.observeForever(observer)

        // Act
        loginViewModel.performLogin(email, password)

        // Avanza el tiempo para que las corrutinas se ejecuten
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        Thread.sleep(500)
        verify(observer).onChanged(false)
    }

    @Test
    fun `login with non-existent user returns false`() = runTest {
        // Arrange
        val email = "nonexistent@example.com"
        val password = "password"

        `when`(userRepository.getUserByEmail(email)).thenReturn(null)

        loginViewModel.loginResult.observeForever(observer)

        // Act
        loginViewModel.performLogin(email, password)

        // Avanza el tiempo para que las corrutinas se ejecuten
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        Thread.sleep(500)
        verify(observer).onChanged(false)
    }
}
