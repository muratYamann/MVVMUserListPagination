package com.murat.international.scorpcase.core

import androidx.recyclerview.widget.RecyclerView

abstract class ScrollListener : RecyclerView.OnScrollListener() {

    abstract fun onScrollLimit()

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val offset = recyclerView.computeVerticalScrollOffset()
            val extent = recyclerView.computeVerticalScrollExtent()
            val range = recyclerView.computeVerticalScrollRange()
            if (offset + extent == range) {
                    onScrollLimit()
            }
        }
    }
}