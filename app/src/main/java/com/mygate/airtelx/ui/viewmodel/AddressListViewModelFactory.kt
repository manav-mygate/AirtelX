package com.mygate.airtelx.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mygate.airtelx.engine.IEngine

class AddressListViewModelFactory(val engine: IEngine) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddressListViewModel::class.java) -> AddressListViewModel(
                engine
            ) as T
            else -> throw IllegalArgumentException("Unknown model class $modelClass")
        }
    }

}