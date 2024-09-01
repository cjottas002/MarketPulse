package es.market.pulse.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.activity.result.contract.ActivityResultContracts
import dagger.hilt.android.AndroidEntryPoint
import es.market.pulse.databinding.FragmentLoginBinding
import es.market.pulse.ui.activities.MainActivity
import es.market.pulse.ui.activities.RegisterActivity
import es.market.pulse.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setupListeners()
    }

    private fun init() {
        loginViewModel.email.value = "admin@example.com"
        loginViewModel.password.value = "admin"

        binding.user = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setupListeners() {

        binding.bLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            lifecycleScope.launch {
                val isSuccess = loginViewModel.login(email, password)

                if (isSuccess) {
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val data = result.data
                val username = data?.getStringExtra("email") ?: ""
                val password = data?.getStringExtra("password") ?: ""

                binding.etEmail.setText(username)
                binding.etPassword.setText(password)
            }
        }

        binding.twRegister.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
