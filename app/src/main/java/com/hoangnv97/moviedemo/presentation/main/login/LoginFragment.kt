package com.hoangnv97.moviedemo.presentation.main.login

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.common.hideKeyboard
import com.hoangnv97.moviedemo.databinding.FragmentLoginBinding
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = this.viewModel

        binding.contentContainer.setOnClickListener {
            activity?.hideKeyboard()
        }
        binding.passwordShow.setOnClickListener {
            toggleShowPassword()
        }
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }

        binding.registerButton.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@LoginFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
                launch {
                    viewModel.login.collect {
                        if (it) {
                            activity?.hideKeyboard()
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            )
                        }
                    }
                }
            }
        }
    }
    private fun toggleShowPassword() {
        InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.passwordEdt.apply {
            if (
                inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            ) {
                inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordShow.setImageResource(
                    R.drawable.ic_show_password
                )
            } else {
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordShow.setImageResource(
                    R.drawable.ic_cover_password
                )
            }
            setSelection(text?.length ?: 0)
        }
    }
}
