package es.market.pulse.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.market.pulse.repository.UserRepository
import es.market.pulse.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userRepository.getUserByEmail(email)
            user != null && Helpers.verifyPassword(password, user.passwordHash)
        }
    }
}