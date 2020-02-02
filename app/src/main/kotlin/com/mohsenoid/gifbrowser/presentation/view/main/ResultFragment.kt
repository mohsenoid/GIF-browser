package com.mohsenoid.gifbrowser.presentation.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import com.mohsenoid.gifbrowser.presentation.view.base.BaseFragment
import com.mohsenoid.gifbrowser.presentation.view.util.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.fragment_result_list.resultList
import kotlinx.android.synthetic.main.fragment_result_list.resultListProgress

class ResultFragment : BaseFragment(), ResultAdapter.ClickListener {

    private var listener: OnResultInteractionListener? = null

    private val adapter: ResultAdapter = ResultAdapter(listener = this)

    private val layoutManager = GridLayoutManager(context, SPAN_COUNT)

    private var endlessScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
        override fun onLoadMore(
            page: Int,
            totalItemsCount: Int,
            view: RecyclerView
        ) {
            listener?.onLoadMore(page)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        resultList.layoutManager = layoutManager
        resultList.addOnScrollListener(endlessScrollListener)

        resultList.adapter = adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnResultInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun showLoadingMore() {
        resultListProgress.visibility = View.VISIBLE
    }

    fun hideLoadingMore() {
        resultListProgress.visibility = View.GONE
    }

    fun setResult(result: List<GifModel>) {
        adapter.setResult(result)
        endlessScrollListener.resetState()
        adapter.notifyDataSetChanged()
    }

    fun addMoreResult(result: List<GifModel>) {
        adapter.addMoreResult(result)
        adapter.notifyDataSetChanged()
    }

    fun reachedEndOfList() {
        resultListProgress.visibility = View.GONE
    }

    override fun onResultClick(gifModel: GifModel) {
        listener?.onGifClick(gifModel)
    }

    interface OnResultInteractionListener {
        fun onLoadMore(page: Int)
        fun onGifClick(gifModel: GifModel)
    }

    companion object {
        private const val SPAN_COUNT = 3

        fun newInstance(): ResultFragment {
            return ResultFragment()
        }
    }
}
