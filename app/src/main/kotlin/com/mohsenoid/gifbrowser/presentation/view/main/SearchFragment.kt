package com.mohsenoid.gifbrowser.presentation.view.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.util.extension.hideKeyboard
import com.mohsenoid.gifbrowser.util.extension.onSearchAction
import kotlinx.android.synthetic.main.fragment_search.queryString
import kotlinx.android.synthetic.main.fragment_search.searchAction
import kotlinx.android.synthetic.main.fragment_search.searchProgress

class SearchFragment : Fragment() {

    private var listener: OnSearchButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        searchAction.setOnClickListener(::onSearchActionClicked)
        queryString.onSearchAction(::onSearchActionClicked)
    }

    private fun onSearchActionClicked(view: View) {
        val queryStringText = queryString.text
        if (queryStringText.isBlank()) {
            queryString.error = getString(R.string.search_empty_error)
            queryString.requestFocus()
        } else {
            queryString.error = null
            activity?.hideKeyboard()
            listener?.onOnSearchButtonClick(queryStringText.toString().trim())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchButtonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun showLoading() {
        searchProgress.visibility = View.VISIBLE
    }

    fun hideLoading() {
        searchProgress.visibility = View.INVISIBLE
    }

    interface OnSearchButtonClickListener {
        fun onOnSearchButtonClick(query: String)
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}
