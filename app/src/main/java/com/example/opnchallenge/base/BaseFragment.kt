package com.example.opnchallenge.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment : Fragment() {
    @Inject
    lateinit var schedulersFacade: SchedulersFacade

    protected var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions = CompositeDisposable()
    }

    override fun onDestroyView() {
        subscriptions.clear()
        super.onDestroyView()
    }
}