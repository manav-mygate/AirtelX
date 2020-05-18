package com.mygate.airtelx.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding.widget.RxTextView
import com.mygate.airtelx.R
import com.mygate.airtelx.model.AddressList
import com.mygate.airtelx.ui.adapter.AddressListAdapter
import com.mygate.airtelx.ui.viewmodel.AddressListViewModel
import com.mygate.airtelx.ui.viewmodel.AddressListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


class FeedActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    val factory: AddressListViewModelFactory by instance()

    private val viewModel: AddressListViewModel by lazy {
        ViewModelProviders.of(this, factory).get(AddressListViewModel::class.java)
    }

    var adapter: AddressListAdapter? = null

    var dataList = ArrayList<AddressList>()
    var layoutManager: LinearLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null)
            supportActionBar!!.hide()

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rView.layoutManager = layoutManager
        adapter = AddressListAdapter(this, dataList)
        rView.adapter = adapter


        viewModel.searchData.observe(this, searchDataObserver)
        viewModel.networkStatus.observe(this, networkStatusObserver)


        tvRetry.setOnClickListener {
            llSomethingWentWrong.visibility = View.GONE
            tvRetry.visibility = View.GONE
            imvNoInternet.visibility = View.GONE
            viewModel.checkNetworkConection()

        }


        RxTextView.textChanges(searchInputView)
            .debounce(500, TimeUnit.MILLISECONDS,AndroidSchedulers.mainThread())
            .subscribe { textChanged: CharSequence? ->
                if (!TextUtils.isEmpty(textChanged.toString())) {
                    viewModel.fetchSearchData(textChanged.toString())
                }else{
                    dataList.clear()
                    adapter?.notifyDataSetChanged()
                }
            }



    }


    private val searchDataObserver = Observer<List<AddressList?>> {
        if (it != null && it.size > 0) {
            dataList.clear()
            it.forEach {
                dataList.add(it!!)
            }
            rView.visibility = VISIBLE
            adapter?.notifyDataSetChanged()
        }
    }

    private val networkStatusObserver = Observer<Boolean> {
        if (it == null) {
            return@Observer
        }

        if (it) {
            llSomethingWentWrong.visibility = View.GONE
            tvRetry.visibility = View.GONE
            imvNoInternet.visibility = View.GONE
        } else {
            rView.visibility = GONE
            llSomethingWentWrong.visibility = View.VISIBLE
            tvRetry.visibility = View.VISIBLE
            imvNoInternet.visibility = View.VISIBLE
        }
    }
}
