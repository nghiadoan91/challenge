package com.example.opnchallenge.order_summary.view_model

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.model.ProductViewState
import com.example.domain.usecase.MakeOrderUseCase
import com.example.opnchallenge.base.SchedulersFacade
import com.example.opnchallenge.screen.order_summary.OrderSummaryFragment.Companion.PRODUCT_ARG
import com.example.opnchallenge.screen.order_summary.OrderSummaryViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O])
class OrderSummaryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: OrderSummaryViewModel

    private val makeOrderUseCase = mock<MakeOrderUseCase>()
    private val savedStateHandle = mock<SavedStateHandle>()
    private val schedulersFacade = mock<SchedulersFacade>()

    @Before
    fun setUp() {
        viewModel = OrderSummaryViewModel(
            makeOrderUseCase = makeOrderUseCase,
            savedStateHandle = savedStateHandle,
            schedulersFacade = schedulersFacade
        )
        given(schedulersFacade.computation).willReturn(
            Schedulers.trampoline()
        )
        given(schedulersFacade.io).willReturn(
            Schedulers.trampoline()
        )
        given(schedulersFacade.ui).willReturn(
            Schedulers.trampoline()
        )
    }

    @Test
    fun `should set value store in orderSummaryStateLiveData when loadInit`() {
        // Arrange
        val productList = arrayOf(
            ProductViewState(id = 1, addedQty = 0),
            ProductViewState(id = 2, addedQty = 0),
            ProductViewState(id = 3, addedQty = 0),
            ProductViewState(id = 4, addedQty = 0)
        )
        given(savedStateHandle.get<Array<ProductViewState>>(PRODUCT_ARG)).willReturn(
            productList
        )

        // Act
        viewModel.loadInit()

        // Assert
        assertEquals(productList.toList(), viewModel.orderSummaryStateLiveData.value?.productList)
    }

    @Test
    fun `should update address when update address`() {
        // Arrange
        val expectedAddress = UUID.randomUUID().toString()
        viewModel.updateAddress(expectedAddress)
        given(makeOrderUseCase.execute(any(), any())).willReturn(
            Completable.complete()
        )

        // Act
        viewModel.makeOrder()

        // Assert
        verify(makeOrderUseCase).execute(listOf(), expectedAddress)
        assertEquals(true, viewModel.orderSuccessLiveData.value)
    }

    @Test
    fun `should change nothing when make order fail`() {
        // Arrange
        given(makeOrderUseCase.execute(any(), any())).willReturn(
            Completable.error(Exception())
        )

        // Act
        viewModel.makeOrder()

        // Assert
        assertEquals(false, viewModel.orderSuccessLiveData.value)
    }

    @Test
    fun `should return false orderSuccessLiveData address when make order fail`() {
        // Arrange
        given(makeOrderUseCase.execute(any(), any())).willReturn(
            Completable.complete()
        )

        // Act
        viewModel.makeOrder()

        // Assert
        assertEquals(true, viewModel.orderSuccessLiveData.value)
    }
}