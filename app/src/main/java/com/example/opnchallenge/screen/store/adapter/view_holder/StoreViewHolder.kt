package com.example.opnchallenge.screen.store.adapter.view_holder

import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemStoreBinding
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel

class StoreViewHolder(val binding: ListItemStoreBinding) : BaseViewHolder(binding) {
    override fun bind(itemData: Any, position: Int) {
        (itemData as? StoreItemModel.StoreModel)?.run {
            with(binding) {
                textViewName.text = store.name
                ratingBar.rating = store.rating.toFloat()
                textViewOpeningTime.text = store.openingTime
                textViewClosingTime.text = store.closingTime
            }
        }
    }
}