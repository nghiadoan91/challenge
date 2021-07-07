package com.example.opnchallenge.screen.order_success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.opnchallenge.base.BaseFragment
import com.example.opnchallenge.databinding.FragmentOrderSuccessBinding
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class OrderSuccessFragment : BaseFragment() {

    private var _binding: FragmentOrderSuccessBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriptions += binding.buttonBack.clicks()
            .subscribeBy(
                onError = {},
                onNext = {
                    findNavController().popBackStack()
                }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}