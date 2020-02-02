package com.mohsenoid.gifbrowser.presentation.view.main

import com.mohsenoid.gifbrowser.presentation.model.GifModel
import com.mohsenoid.gifbrowser.presentation.view.base.BasePresenter
import com.mohsenoid.gifbrowser.presentation.view.base.BaseView

interface MainContract {

    interface View : BaseView {
        fun showLoadingMore()
        fun hideLoadingMore()
        fun setResult(result: List<GifModel>)
        fun updateResult(result: List<GifModel>)
        fun reachedEndOfList()
    }

    interface Presenter : BasePresenter<View> {
        suspend fun search(query: String)
        suspend fun searchMore(page: Int)
    }
}
