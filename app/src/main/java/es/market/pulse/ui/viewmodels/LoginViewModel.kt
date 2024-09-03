package es.market.pulse.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.market.pulse.repository.UserRepository
import es.market.pulse.utils.Helpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun performLogin(email: String, password: String) {
        viewModelScope.launch {
            val isSuccess = login(email, password)
            _loginResult.value = isSuccess
        }
    }

    private suspend fun login(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userRepository.getUserByEmail(email)
            val isValid = user != null && Helpers.verifyPassword(password, user.passwordHash)
            _loginResult.postValue(isValid)
            return@withContext isValid
        }
    }
}