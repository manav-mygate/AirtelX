package com.mygate.airtelx.engine

import com.mygate.airtelx.model.AddressList

interface ISearchAddressList {
    fun getData(): List<AddressList?>?
}