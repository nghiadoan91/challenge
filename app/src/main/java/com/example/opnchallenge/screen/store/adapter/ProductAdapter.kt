package com.example.opnchallenge.screen.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemProductBinding
import com.example.opnchallenge.screen.store.adapter.state.ProductViewState
import com.example.opnchallenge.screen.store.adapter.view_holder.StoreProductViewHolder
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxrelay3.Relay

class ProductAdapter(
    val productActionRelay: Relay<ProductActionRelay>
) :
    ListAdapter<ProductViewState, BaseViewHolder>(object :
            DiffUtil.ItemCallback<ProductViewState>() {
            override fun areItemsTheSame(
                oldItem: ProductViewState,
                newItem: ProductViewState
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductViewState,
                newItem: ProductViewState
            ): Boolean {
                return oldItem == newItem
            }
        }) {

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return StoreProductViewHolder(
            binding = ListItemProductBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent, false
            ),
            productActionRelay = productActionRelay
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}