package com.hoangnv97.moviedemo.presentation.main.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hoangnv97.moviedemo.R
import com.hoangnv97.moviedemo.databinding.FragmentSplashBinding
import com.hoangnv97.moviedemo.presentation.common.BaseFragment
import com.hoangnv97.moviedemo.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()
    override val layoutId: Int = R.layout.fragment_splash

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.hide()

        viewModel.isLogin()

        viewModel.isLoginValue.observe(viewLifecycleOwner) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (it) {
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                        )
                    } else {
                        findNavController().navigate(
                            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                        )
                    }
                },
                1500
            )
        }
    }
}
