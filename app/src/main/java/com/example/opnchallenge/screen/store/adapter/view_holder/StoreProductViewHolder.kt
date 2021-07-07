package com.example.opnchallenge.screen.store.adapter.view_holder

import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.base.extension.loadUrl
import com.example.opnchallenge.databinding.ListItemProductBinding
import com.example.domain.model.ProductViewState
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxrelay3.Relay
import io.reactivex.rxjava3.kotlin.subscribeBy

class StoreProductViewHolder(
    val binding: ListItemProductBinding,
    val productActionRelay: Relay<ProductActionRelay>
) : BaseViewHolder(binding) {
    override fun bind(itemData: Any, position: Int) {
        with(itemData as ProductViewState) {
            with(binding) {
                textViewName.text = itemData.name
                textViewPrice.text = itemData.price.toString()
                textViewQty.text = itemData.addedQty.toString()
                imageView.loadUrl(root.context, imageUrl)
                buttonMinus.isEnabled = addedQty > 0
                buttonAdd.clicks()
                    .map {
                        ProductActionRelay.Add(product = itemData)
                    }
                    .subscribeBy(
                        onError = {},
                        onNext = {
                            productActionRelay.accept(it)
                        }
                    )

                buttonMinus.clicks()
                    .map {
                        ProductActionRelay.Minus(product = itemData)
                    }
                    .subscribeBy(
                        onError = {},
                        onNext = {
                            productActionRelay.accept(it)
                        }
                    )
            }
        }
    }
}