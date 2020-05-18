package com.mygate.airtelx.engine

import com.mygate.airtelx.Constants.CITY
import com.mygate.airtelx.model.AddressList
import com.mygate.airtelx.model.SearchResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress


class EngineImpl : IEngine {
    override fun getSearchList(query:String) {
        val apiInterface = APIClient.client?.create(ApiInterface::class.java)
        apiInterface?.let {
            it.getSearchList(query,CITY)?.enqueue(object : Callback<SearchResponse> {
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    call.cancel()
                    t.localizedMessage
                    t.message
                }

                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.body() != null) {
                        EventBus.getDefault().post(object : ISearchAddressList {
                            override fun getData(): List<AddressList?>? {
                                return response.body()?.data?.addressList
                            }
                        })
                    }
                }

            })
        }

    }

    override fun checkNetwork() {
        try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr: SocketAddress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(sockaddr, timeoutMs)
            sock.close()
            EventBus.getDefault().post(object : INetworkStatus {
                override fun isNetworkAvailable(): Boolean {
                    return true
                }

            })
        } catch (e: IOException) {
            EventBus.getDefault().post(object : INetworkStatus {
                override fun isNetworkAvailable(): Boolean {
                    return false
                }

            })
        }
    }

}