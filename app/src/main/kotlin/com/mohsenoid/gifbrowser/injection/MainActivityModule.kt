package com.mohsenoid.gifbrowser.injection

import com.mohsenoid.gifbrowser.presentation.mapper.GifModelMapper
import com.mohsenoid.gifbrowser.presentation.view.main.MainActivity
import com.mohsenoid.gifbrowser.presentation.view.main.MainContract
import com.mohsenoid.gifbrowser.presentation.view.main.MainPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainActivityModule = module {

    scope(named<MainActivity>()) {

        scoped<MainContract.Presenter> {
            MainPresenter(
                configProvider = get(),
                repository = get(),
                gifModelMapper = get(named<GifModelMapper>())
            )
        }
    }
}
