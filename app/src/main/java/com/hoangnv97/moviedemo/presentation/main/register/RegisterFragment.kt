package com.hoangnv97.moviedemo.presentation.main.register

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.common.hideKeyboard
import com.hoangnv97.moviedemo.databinding.FragmentRegisterBinding
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {

    override val viewModel: RegisterViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_register

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = this.viewModel

        binding.contentContainer.setOnClickListener {
            activity?.hideKeyboard()
        }
        binding.registerBtn.setOnClickListener {
            viewModel.register()
        }
        binding.passwordShow.setOnClickListener {
            toggleShowPassword()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        if (it) {
                            progress.showLoadingOnFragment(this@RegisterFragment)
                        } else {
                            progress.hideLoadingProgress()
                        }
                    }
                }
                launch {
                    viewModel.register.collect {
                        if (it) {
                            activity?.hideKeyboard()
                            Toast.makeText(
                                requireContext(),
                                "Sign up success.",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
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
