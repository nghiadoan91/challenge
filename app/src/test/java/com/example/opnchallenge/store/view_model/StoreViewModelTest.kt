package com.example.opnchallenge.store.view_model

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.model.Store
import com.example.domain.model.ProductViewState
import com.example.domain.usecase.FetchProductUseCase
import com.example.domain.usecase.FetchStoreUseCase
import com.example.opnchallenge.base.SchedulersFacade
import com.example.opnchallenge.screen.store.StoreViewModel
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
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
class StoreViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StoreViewModel

    private val fetchStoreUseCase = mock<FetchStoreUseCase>()
    private val fetchProductUseCase = mock<FetchProductUseCase>()
    private val schedulersFacade = mock<SchedulersFacade>()

    @Before
    fun setUp() {
        viewModel = StoreViewModel(
            fetchStoreUseCase = fetchStoreUseCase,
            fetchProductUseCase = fetchProductUseCase,
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
    fun `should set value store in storeViewStateLiveData when fetchStore success`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = Store(
            name = UUID.randomUUID().toString()
        )
        given(fetchStoreUseCase.execute()).willReturn(
            Single.just(expectedResult)
        )

        // Act
        viewModel.fetchStore()

        // Assert
        assertEquals(expectedResult, viewModel.storeViewStateLiveData.value?.store)
    }

    @Test
    fun `should not set value store in storeViewStateLiveData when fetchStore fail`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = Store()
        given(fetchStoreUseCase.execute()).willReturn(
            Single.error(Exception())
        )

        // Act
        viewModel.fetchStore()

        // Assert
        assertEquals(expectedResult, viewModel.storeViewStateLiveData.value?.store)
    }

    @Test
    fun `should set product list in storeViewStateLiveData when fetchStore success`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = listOf(
            ProductViewState(),
            ProductViewState(),
            ProductViewState()
        )
        given(fetchProductUseCase.execute()).willReturn(
            Single.just(expectedResult)
        )

        // Act
        viewModel.fetchProduct()

        // Assert
        assertEquals(3, viewModel.storeViewStateLiveData.value?.productList?.size)
    }

    @Test
    fun `should return empty in storeViewStateLiveData product list when fetchProduct fail`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        given(fetchProductUseCase.execute()).willReturn(
            Single.error(Exception())
        )

        // Act
        viewModel.fetchProduct()

        // Assert
        assertEquals(true, viewModel.storeViewStateLiveData.value?.productList?.isEmpty())
    }

    @Test
    fun `should update addedQty of product in storeViewStateLiveData product list when add`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = 1
        val productToUpdate = ProductViewState(id = 1, addedQty = 0)
        val productList = listOf(
            productToUpdate,
            ProductViewState(id = 2, addedQty = 0),
            ProductViewState(id = 3, addedQty = 0)
        )
        val productActionRelay = ProductActionRelay.Add(productToUpdate)
        given(fetchProductUseCase.execute()).willReturn(
            Single.just(productList)
        )
        viewModel.fetchProduct()

        // Act
        viewModel.adjustQty(productActionRelay)

        // Assert
        val productAdded = viewModel.storeViewStateLiveData.value?.productList?.firstOrNull {
            it.id == 1
        } ?: ProductViewState()
        assertEquals(
            expectedResult,
            productAdded.addedQty
        )
    }

    @Test
    fun `should update addedQty of product in storeViewStateLiveData product list when minus`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = 0
        val productToUpdate = ProductViewState(id = 1, addedQty = 1)
        val productList = listOf(
            productToUpdate,
            ProductViewState(id = 2, addedQty = 0),
            ProductViewState(id = 3, addedQty = 0)
        )
        val productActionRelay = ProductActionRelay.Minus(productToUpdate)
        given(fetchProductUseCase.execute()).willReturn(
            Single.just(productList)
        )
        viewModel.fetchProduct()

        // Act
        viewModel.adjustQty(productActionRelay)

        // Assert
        val productAdded = viewModel.storeViewStateLiveData.value?.productList?.firstOrNull {
            it.id == 1
        } ?: ProductViewState()
        assertEquals(
            expectedResult,
            productAdded.addedQty
        )
    }

    @Test
    fun `should not update addedQty of product in storeViewStateLiveData product list when minus`() {
        // Arrange
        viewModel.storeViewStateLiveData.observeForever {}
        val expectedResult = 0
        val productToUpdate = ProductViewState(id = 1, addedQty = expectedResult)
        val productList = listOf(
            productToUpdate,
            ProductViewState(id = 2, addedQty = 0),
            ProductViewState(id = 3, addedQty = 0)
        )
        val productActionRelay = ProductActionRelay.Minus(productToUpdate)
        given(fetchProductUseCase.execute()).willReturn(
            Single.just(productList)
        )
        viewModel.fetchProduct()

        // Act
        viewModel.adjustQty(productActionRelay)

        // Assert
        val productAdded = viewModel.storeViewStateLiveData.value?.productList?.firstOrNull {
            it.id == 1
        } ?: ProductViewState()
        assertEquals(
            expectedResult,
            productAdded.addedQty
        )
    }
}