package com.example.opnchallenge.store.adapter.view_holder

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.model.Store
import com.example.opnchallenge.TestApplication
import com.example.opnchallenge.databinding.ListItemStoreBinding
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel
import com.example.opnchallenge.screen.store.adapter.view_holder.StoreViewHolder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O], application = TestApplication::class)
class StoreStoreViewHolderTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var view: StoreViewHolder

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setUp() {

        view = StoreViewHolder(
            binding = ListItemStoreBinding.inflate(
                LayoutInflater.from(context)
            )
        )
    }

    @Test
    fun `should display store when bind`() {
        // Arrange
        val storeModel = StoreItemModel.StoreModel(
            Store(
                name = UUID.randomUUID().toString(),
                rating = 4.5,
                openingTime = UUID.randomUUID().toString(),
                closingTime = UUID.randomUUID().toString()
            )
        )

        // Act
        view.bind(storeModel, 0)

        // Assert
        assertEquals(storeModel.store.name, view.binding.textViewName.text)
        assertEquals(storeModel.store.rating.toFloat(), view.binding.ratingBar.rating)
        assertEquals(storeModel.store.openingTime, view.binding.textViewOpeningTime.text)
        assertEquals(storeModel.store.closingTime, view.binding.textViewClosingTime.text)
    }
}