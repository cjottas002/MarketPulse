package es.market.pulse.ui.viewmodels


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.market.pulse.model.User
import es.market.pulse.repository.UserRepository
import es.market.pulse.utils.RoleType
import es.market.pulse.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun register(name: String, email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val user = User(
                    username = name,
                    email = email,
                    passwordHash = Helpers.hashPassword(password),
                    roleId = RoleType.USER.ordinal
                )
                userRepository.addUser(user)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}
