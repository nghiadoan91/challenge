package com.example.opnchallenge.order_summary.adapter.view_holder

import android.content.Context
import android.os.Build
import android.os.Looper
import android.view.LayoutInflater
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.opnchallenge.TestApplication
import com.example.opnchallenge.databinding.ListItemOrderSummaryTotalBinding
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryTotalViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import kotlin.random.Random
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O], application = TestApplication::class)
class OrderSummaryTotalViewHolderTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var view: OrderSummaryTotalViewHolder

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {

        view = OrderSummaryTotalViewHolder(
            binding = ListItemOrderSummaryTotalBinding.inflate(
                LayoutInflater.from(context)
            )
        )
        Shadows.shadowOf(Looper.getMainLooper()).idle()
    }

    @Test
    fun `should display Total when bind`() {
        // Arrange
        val expectedResult = Random.nextDouble()

        // Act
        view.bind(OrderSummaryItemModel.TotalModel(expectedResult), 0)

        // Assert
        assertEquals(
            expectedResult.toString(),
            view.binding.textViewPrice.text
        )
    }
}