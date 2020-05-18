package com.mygate.airtelx.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mygate.airtelx.engine.IEngine
import com.mygate.airtelx.engine.INetworkStatus
import com.mygate.airtelx.engine.ISearchAddressList
import com.mygate.airtelx.model.AddressList
import com.mygate.airtelx.threading.BusinessExecutor
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddressListViewModel(val iEngine: IEngine) : ViewModel() {

    val searchData = MutableLiveData<List<AddressList?>>()

    val networkStatus = MutableLiveData<Boolean>()

    init {
        EventBus.getDefault().register(this)
    }

    fun fetchSearchData(query:String) {
        iEngine.getSearchList(query)
    }

    fun checkNetworkConection() {
        BusinessExecutor.instance.executeInBusinessThread(Runnable {
            iEngine.checkNetwork()
        })
    }


    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    fun onGetSearchData(event: ISearchAddressList) {
        searchData.postValue(event.getData())
    }

    @Subscribe
    fun onNetworkStatus(event: INetworkStatus) {
        networkStatus.postValue(event.isNetworkAvailable())
    }


}