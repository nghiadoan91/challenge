package com.example.opnchallenge.screen.store

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opnchallenge.base.BaseFragment
import com.example.opnchallenge.databinding.FragmentStoreBinding
import com.example.opnchallenge.screen.store.adapter.StoreAdapter
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy

@AndroidEntryPoint
class StoreFragment : BaseFragment() {

    private var _binding: FragmentStoreBinding? = null

    private val binding get() = _binding!!

    private val mViewModel by viewModels<StoreViewModel>()

    private val storeAdapter = StoreAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = storeAdapter
            layoutManager = LinearLayoutManager(context)
        }

        subscriptions += storeAdapter.bindProductActionRelay()
            .observeOn(AndroidSchedulers.from(Looper.getMainLooper(), true))
            .subscribeBy(
                onError = {},
                onNext = {
                    mViewModel.adjustQty(it)
                }
            )

        subscriptions += binding.buttonOrder.clicks()
            .subscribeBy(
                onError = {},
                onNext = {
                    mViewModel.selectedList.takeIf { it.isNotEmpty() }
                        ?.let {
                            findNavController().navigate(
                                StoreFragmentDirections.actionStoreFragmentToOrderSummaryFragment(
                                    it.toTypedArray()
                                )
                            )
                        }
                }
            )

        mViewModel.storeViewStateLiveData.observe(viewLifecycleOwner) {
            storeAdapter.submitList(StoreItemModel.fromStoreViewState(it))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}