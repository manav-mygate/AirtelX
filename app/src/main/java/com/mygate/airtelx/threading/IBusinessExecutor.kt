package com.mygate.airtelx.threading

import java.util.concurrent.Executor
import java.util.concurrent.Future

interface IBusinessExecutor {
    /**
     * Creates one-shot action and executes it ASAP on business thread.
     * Passed command may have to wait for other tasks to complete before
     * running.
     * @pre `null != command`
     * @param command command to run on business thread
     * @throws NullPointerException if passed command is `null`
     */
    @Throws(NullPointerException::class)
    fun executeInBusinessThread(command: Runnable?)

    /**
     * Executes tasks on Resource thread pool. Pool Size = 15
     * Intended only for content provider, file, database operations within app
     * @param command
     * @throws NullPointerException
     */
    @Throws(NullPointerException::class)
    fun executeInResourceThread(command: Runnable?)

    @Throws(NullPointerException::class)
    fun submitInResourceThread(command: Runnable?): Future<*>?

    @Throws(NullPointerException::class)
    fun submitInSessionResourceThread(command: Runnable?)

    val resourceExecutor: Executor
    val businessExecutor: Executor
}