package es.market.pulse.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import es.market.pulse.R
import es.market.pulse.databinding.ActivityLoginBinding
import es.market.pulse.ui.fragments.LoginFragment

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(binding.flMainLogin.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initToolbar()
        loadFragment()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.tlLogin.root)
        supportActionBar?.title = "Login"

    }

    private fun loadFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main_login, LoginFragment())
        transaction.commit()
    }
}
