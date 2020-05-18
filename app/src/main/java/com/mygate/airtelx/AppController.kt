package com.mygate.airtelx

import android.app.Application
import com.mygate.airtelx.engine.EngineImpl
import com.mygate.airtelx.engine.IEngine
import com.mygate.airtelx.ui.viewmodel.AddressListViewModelFactory
import org.greenrobot.eventbus.EventBus
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppController : Application(), KodeinAware {


    companion object {
        private var getInstance: AppController? = null
        fun applicationContext(): AppController {
            return getInstance as AppController
        }
    }

    init {
        getInstance = this
    }

    override fun onCreate() {
        super.onCreate()
        EventBus.builder()
            .sendNoSubscriberEvent(false)
            .throwSubscriberException(true)
            .logNoSubscriberMessages(false).build()
    }

    override val kodein = Kodein.lazy {
        bind<IEngine>() with singleton { EngineImpl() }
        bind<AddressListViewModelFactory>() with provider { AddressListViewModelFactory(instance()) }
    }


}