package com.example.opnchallenge.screen.order_summary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.opnchallenge.base.BaseViewModel
import com.example.opnchallenge.base.SchedulersFacade
import com.example.opnchallenge.base.extension.toBundle
import com.example.domain.model.OrderSummaryState
import com.example.domain.usecase.MakeOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class OrderSummaryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val makeOrderUseCase: MakeOrderUseCase,
    private val schedulersFacade: SchedulersFacade
) : BaseViewModel() {

    private val orderSummaryFragmentArgs by lazy {
        OrderSummaryFragmentArgs.fromBundle(
            savedStateHandle.toBundle
        )
    }
    val orderSummaryStateLiveData by lazy { MutableLiveData(OrderSummaryState()) }
    private val addressLiveData by lazy { MutableLiveData("") }
    val orderSuccessLiveData by lazy { MutableLiveData(false) }

    init {
        orderSummaryStateLiveData.value =
            OrderSummaryState(orderSummaryFragmentArgs.productList.toList())
    }

    fun updateAddress(address: String) {
        addressLiveData.value = address
    }

    fun makeOrder() {
        disposables += makeOrderUseCase.execute(
            productList = orderSummaryStateLiveData.value?.productList ?: emptyList(),
            address = addressLiveData.value.toString()
        )
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {},
                onComplete = {
                    orderSuccessLiveData.value = true
                }
            )
    }
}