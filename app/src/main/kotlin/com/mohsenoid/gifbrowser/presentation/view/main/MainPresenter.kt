package com.mohsenoid.gifbrowser.presentation.view.main

import com.mohsenoid.gifbrowser.data.exception.EndOfListException
import com.mohsenoid.gifbrowser.domain.Repository
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.presentation.mapper.Mapper
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import com.mohsenoid.gifbrowser.util.config.ConfigProvider
import timber.log.Timber

class MainPresenter(
    private val configProvider: ConfigProvider,
    private val repository: Repository,
    private val gifModelMapper: Mapper<GifEntity, GifModel>
) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun bind(view: MainContract.View) {
        this.view = view

        repository.getLastSearchResult()?.let { view.setResult(it.map(gifModelMapper::map)) }
    }

    override fun unbind() {
        this.view = null
    }

    private var query: String = ""

    override suspend fun search(query: String) {
        view?.showLoading()
        this.query = query
        fetchSearchResult()
    }

    override suspend fun searchMore(page: Int) {
        view?.showLoadingMore()
        fetchSearchResult(page)
    }

    private suspend fun fetchSearchResult(page: Int = 0) {

        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        try {
            val resultEntities: List<GifEntity> = repository.search(query, page)
            val resultModels: List<GifModel> = resultEntities.map(gifModelMapper::map)

            if (page == 0) {
                view?.setResult(resultModels)
            } else {
                view?.updateResult(resultModels)
            }
        } catch (e: Exception) {
            Timber.w(e)

            if (e is EndOfListException) {
                view?.reachedEndOfList()
            } else {
                view?.showMessage(e.message ?: e.toString())
            }
        } finally {
            view?.hideLoading()
            view?.hideLoadingMore()
        }
    }
}
