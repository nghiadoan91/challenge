package com.example.opnchallenge.screen.order_summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opnchallenge.base.BaseFragment
import com.example.opnchallenge.databinding.FragmentOrderSummaryBinding
import com.example.opnchallenge.screen.order_summary.adapter.OrderSummaryAdapter
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class OrderSummaryFragment : BaseFragment() {

    private var _binding: FragmentOrderSummaryBinding? = null
    private val binding get() = _binding!!

    private val mViewModel by viewModels<OrderSummaryViewModel>()

    private val orderSummaryAdapter = OrderSummaryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = orderSummaryAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mViewModel.orderSummaryStateLiveData.observe(viewLifecycleOwner) {
            orderSummaryAdapter.submitList(OrderSummaryItemModel.fromOrderSummaryState(it))
        }

        mViewModel.orderSuccessLiveData.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(OrderSummaryFragmentDirections.actionOrderSummaryFragmentToOrderSuccessFragment())
            }
        }

        subscriptions += binding.editTextAddress.textChanges()
            .debounce(200, TimeUnit.MILLISECONDS)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {},
                onNext = {
                    mViewModel.updateAddress(it.toString())
                }
            )

        subscriptions += binding.buttonConfirm.clicks()
            .subscribeBy(
                onError = {},
                onNext = {
                    mViewModel.makeOrder()
                }
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}