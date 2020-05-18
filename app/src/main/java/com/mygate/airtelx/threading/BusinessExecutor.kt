package com.mygate.airtelx.threading

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future


class BusinessExecutor : IBusinessExecutor {
    private val mBusinessPoolExecutor: ExecutorService
    private val mResourcePoolExecutor: ExecutorService
    private val mSessionResourcePoolExecutor: ExecutorService

    override fun executeInBusinessThread(command: Runnable?) {
        command?.let {
            mBusinessPoolExecutor.execute(command)

        }
    }

    override fun executeInResourceThread(command: Runnable?) {
        command?.let {
            mResourcePoolExecutor.execute(command)
        }
    }

    override fun submitInResourceThread(command: Runnable?): Future<*> {
        return mResourcePoolExecutor.submit(command)
    }

    override fun submitInSessionResourceThread(command: Runnable?) {
        command?.let {
            mResourcePoolExecutor.execute(command)
        }
    }

    override val resourceExecutor: Executor
        get() = mResourcePoolExecutor

    override val businessExecutor: Executor
        get() = mBusinessPoolExecutor

    companion object {
        private val mBusinessExecutor = BusinessExecutor()
        private const val RESOURCE_THREAD_POOL_SIZE = 15
        val instance: IBusinessExecutor
            get() = mBusinessExecutor
    }

    init {
        mBusinessPoolExecutor = Executors.newSingleThreadExecutor()
        mResourcePoolExecutor =
            Executors.newFixedThreadPool(RESOURCE_THREAD_POOL_SIZE)
        mSessionResourcePoolExecutor = Executors.newSingleThreadExecutor()
    }
}