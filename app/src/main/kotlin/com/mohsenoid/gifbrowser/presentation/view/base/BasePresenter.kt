package com.mohsenoid.gifbrowser.presentation.view.base

interface BasePresenter<V : BaseView> {

    fun bind(view: V)

    fun unbind()
}
