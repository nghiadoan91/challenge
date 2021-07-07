package com.example.opnchallenge.order_summary.adapter.view_holder

import android.content.Context
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.model.ProductViewState
import com.example.opnchallenge.TestApplication
import com.example.opnchallenge.databinding.ListItemOrderSummaryProductBinding
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryProductViewHolder
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
class OrderSummaryProductViewHolderTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var view: OrderSummaryProductViewHolder

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {

        view = OrderSummaryProductViewHolder(
            binding = ListItemOrderSummaryProductBinding.inflate(
                LayoutInflater.from(context)
            )
        )
        Shadows.shadowOf(Looper.getMainLooper()).idle()
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
        assertEquals(
            "${productViewState.name} X ${productViewState.addedQty}",
            view.binding.textViewName.text
        )
        assertEquals(
            (productViewState.price * productViewState.addedQty).toString(),
            view.binding.textViewPrice.text
        )
    }
}