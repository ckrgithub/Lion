package com.ruzhan.lion.helper

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

import com.ruzhan.common.R

/**
 * Created by ruzhan123 on 2018/7/12.
 */
object OnRefreshHelper {

    fun setOnRefreshStatusListener(swipeRefresh: SwipeRefreshLayout, recyclerView: RecyclerView,
                                   listener: OnRefreshStatusListener) {
        swipeRefresh.setOnRefreshListener { listener.onRefresh() }
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(recyclerView.context,
                R.color.colorAccent))

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isLoadMore(recyclerView!!, newState)) {
                    listener.onLoadMore()
                }
            }
        })
    }


    fun isLoadMore(recyclerView: RecyclerView, newState: Int): Boolean {
        val adapter = recyclerView.adapter
        if (adapter != null) {
            val layoutManager = recyclerView.layoutManager
            val lastPosition = layoutManagerToLastPosition(layoutManager)
            val adapterCount = adapter.itemCount
            val refreshPosition = adapterCount - 1
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                    (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING)
        }
        return false
    }

    private fun layoutManagerToLastPosition(layoutManager: RecyclerView.LayoutManager): Int {
        var lastPosition = 0
        if (layoutManager is LinearLayoutManager) {
            lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        } else if (layoutManager is StaggeredGridLayoutManager) {
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(lastPositions)
            lastPosition = findMax(lastPositions)
        }
        return lastPosition
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    interface OnRefreshStatusListener {

        fun onRefresh()

        fun onLoadMore()
    }
}
