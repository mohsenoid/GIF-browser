package com.mohsenoid.gifbrowser.injection

import android.os.Build
import com.mohsenoid.gifbrowser.BuildConfig
import com.mohsenoid.gifbrowser.injection.qualifier.QualifierName
import com.mohsenoid.gifbrowser.util.extension.plus
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.check.checkModules
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.HashMap

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DataModuleTest : ModuleTest() {

    @Test
    fun `check all definitions from dataModule`() {
        startKoin {
            val appProperties: HashMap<String, Any> = hashMapOf(
                QualifierName.IS_DEBUG to true,
                QualifierName.BASE_URL to BuildConfig.BASE_URL,
                QualifierName.API_KEY to BuildConfig.API_KEY
            )
            properties(appProperties)

            androidContext(application)
            modules(appModule + dataModule)
        }.checkModules()
    }
}
