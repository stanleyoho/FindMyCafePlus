package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Adapter.NewsAdapter
import com.app.findmycafeplus.Manager.RealtimeDatabaseManager
import com.app.findmycafeplus.Model.CafeNews
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.LogUtils
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : BaseFragment(){

    private lateinit var myView : View
    private var newsList : ArrayList<CafeNews?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = layoutInflater.inflate(R.layout.fragment_news,container,false)

        getNews()
        return myView
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun initRecycler(news : ArrayList<CafeNews?>){
        myView.recyclerNews.layoutManager = LinearLayoutManager(context)
        myView.recyclerNews.adapter = NewsAdapter(context!!,news)
    }

    fun getNews(){
        val reference  = RealtimeDatabaseManager.newsReference

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                LogUtils.e("onCancelled","onCancelled")
            }

            override fun onDataChange(p0: DataSnapshot) {
                for(item in p0.children){
                    val ddd = item.getValue(CafeNews::class.java)
                    newsList.add(ddd)
                }
                initRecycler(newsList)
            }
        })

    }

}