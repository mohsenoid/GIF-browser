package com.mohsenoid.gifbrowser.injection

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.KoinComponent
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
abstract class ModuleTest : KoinComponent {

    internal lateinit var application: Application

    @Before
    fun setUp() {
        application = ApplicationProvider.getApplicationContext()
        stopKoin()
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
