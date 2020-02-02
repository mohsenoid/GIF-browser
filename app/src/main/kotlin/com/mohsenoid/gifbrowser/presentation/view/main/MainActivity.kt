package com.mohsenoid.gifbrowser.presentation.view.main

import android.os.Bundle
import android.widget.Toast
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import com.mohsenoid.gifbrowser.presentation.view.base.BaseActivity
import com.mohsenoid.gifbrowser.presentation.view.preview.PreviewActivity
import kotlinx.coroutines.launch
import org.koin.androidx.scope.lifecycleScope

class MainActivity : BaseActivity(), MainContract.View,
    SearchFragment.OnSearchButtonClickListener,
    ResultFragment.OnResultInteractionListener {

    private val presenter: MainContract.Presenter by lifecycleScope.inject()

    private lateinit var searchFragment: SearchFragment
    private lateinit var resultFragment: ResultFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            searchFragment = SearchFragment.newInstance()
            resultFragment = ResultFragment.newInstance()
            attachFragments()
        } else {
            searchFragment =
                supportFragmentManager.findFragmentByTag(TAG_SEARCH_FRAGMENT) as SearchFragment
            resultFragment =
                supportFragmentManager.findFragmentByTag(TAG_RESULT_FRAGMENT) as ResultFragment
        }

        presenter.bind(this)
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.searchContainer, searchFragment, TAG_SEARCH_FRAGMENT)
            replace(R.id.resultContainer, resultFragment, TAG_RESULT_FRAGMENT)
        }.commit()
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun onOnSearchButtonClick(query: String) {
        launch {
            presenter.search(query)
        }
    }

    override fun onLoadMore(page: Int) {
        launch {
            presenter.searchMore(page)
        }
    }

    override fun onGifClick(gifModel: GifModel) {
        val intent = PreviewActivity.newIntent(this, gifModel)
        startActivity(intent)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showOfflineMessage(isCritical: Boolean) {
        Toast.makeText(this, R.string.offline_app, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        searchFragment.showLoading()
    }

    override fun hideLoading() {
        searchFragment.hideLoading()
    }

    override fun showLoadingMore() {
        resultFragment.showLoadingMore()
    }

    override fun hideLoadingMore() {
        resultFragment.hideLoadingMore()
    }

    override fun setResult(result: List<GifModel>) {
        resultFragment.setResult(result)
    }

    override fun updateResult(result: List<GifModel>) {
        resultFragment.addMoreResult(result)
    }

    override fun reachedEndOfList() {
        resultFragment.reachedEndOfList()
    }

    companion object {
        internal const val TAG_SEARCH_FRAGMENT: String = "searchFragment"
        internal const val TAG_RESULT_FRAGMENT: String = "resultFragment"
    }
}
