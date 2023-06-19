package com.hoangnv97.moviedemo.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.hoangnv97.moviedemo.BR
import com.hoangnv97.moviedemo.common.ApiErrorHandler
import com.hoangnv97.moviedemo.infra.ApiEnum
import com.hoangnv97.moviedemo.presentation.main.dialog.LoadingProgress
import javax.inject.Inject
import kotlinx.coroutines.launch

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    @Inject
    lateinit var apiErrorHandler: ApiErrorHandler
    protected lateinit var binding: ViewBinding
    protected abstract val viewModel: ViewModel
    val progress by lazy {
        LoadingProgress()
    }
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.apply {
            setVariable(BR.viewModel, viewModel)
            binding.lifecycleOwner = viewLifecycleOwner
            root.isClickable = true
            executePendingBindings()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.failed.collect {
                    it?.let {
                        if (it.api == ApiEnum.API_LOCAL) {
                            apiErrorHandler.showLocalError(this@BaseFragment, it)
                        } else {
                            apiErrorHandler.show(this@BaseFragment, it)
                        }
                    }
                }
            }
        }
    }
}
