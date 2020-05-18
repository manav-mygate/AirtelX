package com.mygate.airtelx.engine

import com.mygate.airtelx.model.AddressList
import com.mygate.airtelx.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/compassLocation/rest/address/autocomplete")
    fun getSearchList(@Query("queryString") queryString:String, @Query("city") city:String): Call<SearchResponse>?

}