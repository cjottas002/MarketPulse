package es.market.pulse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.market.pulse.R
import es.market.pulse.database.MarketPulseDatabase
import es.market.pulse.databinding.ActivityLoginBinding
import es.market.pulse.repository.UserRepository
import es.market.pulse.ui.viewmodels.LoginViewModel
import es.market.pulse.ui.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel.email.value = "admin@example.com"
        loginViewModel.password.value = "admin"

        binding.user = loginViewModel
        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                val isSuccess = loginViewModel.login(email, password)

                if (isSuccess) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                    // Navega a la pantalla principal
                } else {
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.registerTextView.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}