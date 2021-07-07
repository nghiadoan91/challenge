package com.example.opnchallenge.screen.store

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.Store
import com.example.domain.usecase.FetchProductUseCase
import com.example.domain.usecase.FetchStoreUseCase
import com.example.opnchallenge.base.BaseViewModel
import com.example.opnchallenge.base.SchedulersFacade
import com.example.domain.model.ProductViewState
import com.example.domain.model.StoreViewState
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val fetchStoreUseCase: FetchStoreUseCase,
    private val fetchProductUseCase: FetchProductUseCase,
    private val schedulersFacade: SchedulersFacade
) : BaseViewModel() {
    init {
        loadInit()
    }
    val storeViewStateLiveData: MediatorLiveData<StoreViewState> by lazy {
        MediatorLiveData<StoreViewState>().apply {
            addSource(storeLiveData) { source ->
                storeViewStateLiveData.value = storeViewStateLiveData.value?.copy(
                    store = source
                ) ?: StoreViewState(store = source)
            }
            addSource(productListLiveData) { source ->
                storeViewStateLiveData.value = storeViewStateLiveData.value?.copy(
                    productList = source
                ) ?: StoreViewState(productList = source)
            }
        }
    }

    private val storeLiveData by lazy { MutableLiveData(Store()) }
    private val productListLiveData by lazy { MutableLiveData<List<ProductViewState>>(emptyList()) }

    fun loadInit() {
        fetchStore()
        fetchProduct()
    }

    fun fetchStore() {
        disposables += fetchStoreUseCase.execute()
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {},
                onSuccess = {
                    storeLiveData.value = it
                }
            )
    }

    fun fetchProduct() {
        disposables += fetchProductUseCase.execute()
            .subscribeOn(schedulersFacade.io)
            .observeOn(schedulersFacade.ui)
            .subscribeBy(
                onError = {},
                onSuccess = {
                    productListLiveData.value = it
                }
            )
    }

    fun adjustQty(productActionRelay: ProductActionRelay) {
        if (productActionRelay !is ProductActionRelay.Minus || productActionRelay.product.addedQty > 0) {
            productListLiveData.value = productListLiveData.value?.map {
                when (it.id) {
                    productActionRelay.product.id -> when (productActionRelay) {
                        is ProductActionRelay.Add -> it.copy(addedQty = it.addedQty + 1)
                        is ProductActionRelay.Minus -> it.copy(addedQty = it.addedQty - 1)
                    }
                    else -> it
                }
            }
        }
    }

    val selectedList: List<ProductViewState>
        get() = productListLiveData.value?.filter { it.addedQty > 0 } ?: emptyList()
}