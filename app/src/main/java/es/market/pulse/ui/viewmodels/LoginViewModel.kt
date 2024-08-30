package es.market.pulse.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.market.pulse.repository.UserRepository
import es.market.pulse.utils.StringValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userRepository.getUserByEmail(email)
            user != null && StringValidator.verifyPassword(password, user.passwordHash)
        }
    }
}