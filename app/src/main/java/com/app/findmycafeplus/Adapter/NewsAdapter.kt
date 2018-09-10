package com.app.findmycafeplus.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Model.CafeNews

import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.layout_cafe_info_window.view.*
import kotlinx.android.synthetic.main.layout_news_item.view.*

import java.util.ArrayList

class NewsAdapter(context: Context,newsList: ArrayList<CafeNews?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var news = newsList
    private var mContext : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.layout_news_item,parent,false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cafeNew = news[position]
        val newsHolder : NewsViewHolder = holder as NewsViewHolder

        if(cafeNew != null){
            newsHolder.initData(cafeNew.title,cafeNew.content,cafeNew.date)
        }
    }

    override fun getItemCount(): Int {
        if(news.size == 0){
            return 0
        }else{
            return news.size
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mView : View = view

        fun initData(title:String,content : String,date:String){
            mView.textNewsTitle.text = title
            mView.textNewsContent.text = content
            mView.textNewsDate.text = date
        }
    }

    public fun updateNews(newsList : ArrayList<CafeNews?>){
        this.news = newsList
        notifyDataSetChanged()
    }
}
