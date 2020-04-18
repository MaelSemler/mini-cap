package com.soen390.conumap.IndoorNavigation

import com.soen390.conumap.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList


class AdapterClass: BaseAdapter {
    lateinit var mContext: Context
    lateinit var inflater: LayoutInflater
    lateinit var searchQueries: MutableList<SearchQuery>
    lateinit var arraylist: ArrayList<SearchQuery>

    constructor(
        context: Context,
        searchQueries: MutableList<SearchQuery>
    ) {
        mContext = context
        this.searchQueries = searchQueries
        inflater = LayoutInflater.from(mContext)
        arraylist = ArrayList()
        arraylist!!.addAll(searchQueries)
    }

    class ViewHolder{
        lateinit var name: TextView
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder = ViewHolder()
        var view = convertView
        if (convertView == null) {
            view =  inflater.inflate(R.layout.search_item, null)
            // Locate the TextViews in listview_item.xml
            holder.name = (view!!.findViewById(R.id.resultText) as TextView)!!
            if (view != null) {
                view.setTag(holder)
            }
        } else {
            holder = view!!.getTag() as ViewHolder
        }
        // Set the results into TextViews
        // Set the results into TextViews
        holder.name.text = searchQueries[position].getQuery()
        return view!!
    }

    override fun getItem(position: Int): Any {
        return searchQueries[position];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getCount(): Int {
        return searchQueries.size;
    }

    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        searchQueries.clear()
        if (charText.length == 0) {
            searchQueries.addAll(arraylist)
        } else {
            for (wp in arraylist) {
                if (wp.getQuery()!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                    searchQueries.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }

}
