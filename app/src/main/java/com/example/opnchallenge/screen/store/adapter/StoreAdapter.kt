package com.example.opnchallenge.screen.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemProductListBinding
import com.example.opnchallenge.databinding.ListItemStoreBinding
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel
import com.example.opnchallenge.screen.store.adapter.view_holder.StoreProductListViewHolder
import com.example.opnchallenge.screen.store.adapter.view_holder.StoreViewHolder
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxrelay3.PublishRelay

class StoreAdapter :
    ListAdapter<StoreItemModel, BaseViewHolder>(object : DiffUtil.ItemCallback<StoreItemModel>() {
        override fun areItemsTheSame(oldItem: StoreItemModel, newItem: StoreItemModel): Boolean {
            return oldItem.viewId == newItem.viewId
        }

        override fun areContentsTheSame(oldItem: StoreItemModel, newItem: StoreItemModel): Boolean {
            return oldItem == newItem
        }
    }) {

    private val productActionRelay by lazy { PublishRelay.create<ProductActionRelay>() }

    companion object {
        private const val TYPE_STORE = 0
        private const val TYPE_PRODUCT = 1
    }

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_STORE -> StoreViewHolder(
                ListItemStoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_PRODUCT -> StoreProductListViewHolder(
                ListItemProductListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent, false
                ),
                productActionRelay
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        currentList.getOrNull(position)?.let {
            return when (it) {
                is StoreItemModel.StoreModel -> TYPE_STORE
                is StoreItemModel.ProductListModel -> TYPE_PRODUCT
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }

    // Relay
    fun bindProductActionRelay() = productActionRelay
}