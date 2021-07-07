package com.example.opnchallenge.store.adapter.view_holder

import android.content.Context
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.model.ProductViewState
import com.example.opnchallenge.TestApplication
import com.example.opnchallenge.databinding.ListItemProductBinding
import com.example.opnchallenge.screen.store.adapter.view_holder.StoreProductViewHolder
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxrelay3.PublishRelay
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import java.util.*
import kotlin.random.Random
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O], application = TestApplication::class)
class StoreProductViewHolderTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var view: StoreProductViewHolder

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {

        view = StoreProductViewHolder(
            binding = ListItemProductBinding.inflate(
                LayoutInflater.from(context)
            ),
            productActionRelay = PublishRelay.create()
        )
    }

    @Test
    fun `should display product when bind`() {
        // Arrange
        val productViewState = ProductViewState(
            name = UUID.randomUUID().toString(),
            price = Random.nextDouble(),
        )

        // Act
        view.bind(productViewState, 0)

        // Assert
        assertEquals(productViewState.name, view.binding.textViewName.text)
        assertEquals(productViewState.price.toString(), view.binding.textViewPrice.text)
        assertEquals(productViewState.addedQty.toString(), view.binding.textViewQty.text)
    }

    @Test
    fun `should accept product action add product when buttonAdd click`() {
        // Arrange
        val productViewState = ProductViewState(
            name = UUID.randomUUID().toString(),
            price = Random.nextDouble(),
        )
        val relay = view.productActionRelay.test()
        view.bind(productViewState, 0)

        // Act
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        view.binding.buttonAdd.performClick()

        // Assert
        relay.assertValue(
            ProductActionRelay.Add(productViewState)
        )
    }

    @Test
    fun `should accept product action minus product when buttonMinus click`() {
        // Arrange
        val productViewState = ProductViewState(
            name = UUID.randomUUID().toString(),
            price = Random.nextDouble(),
        )
        val relay = view.productActionRelay.test()
        view.bind(productViewState, 0)

        // Act
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        view.binding.buttonAdd.performClick()

        // Assert
        relay.assertValue(
            ProductActionRelay.Add(productViewState)
        )
    }
}