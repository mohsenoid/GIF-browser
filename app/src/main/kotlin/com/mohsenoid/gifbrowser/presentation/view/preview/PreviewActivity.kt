package com.mohsenoid.gifbrowser.presentation.view.preview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import timber.log.Timber

class PreviewActivity : AppCompatActivity() {

    private lateinit var previewFragment: PreviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val gifModel: GifModel? = intent.extras?.getParcelable(ARG_GIF_MODEL)

        if (gifModel == null) {
            Timber.w("gifModel is null!")
            onBackPressed()
            return
        }

        if (savedInstanceState == null) {
            previewFragment = PreviewFragment.newInstance(gifModel)
            attachFragments()
        } else {
            previewFragment =
                supportFragmentManager.findFragmentByTag(TAG_PREVIEW_FRAGMENT) as PreviewFragment
        }

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = gifModel.title
        }
    }

    private fun attachFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.previewContainer, previewFragment, TAG_PREVIEW_FRAGMENT)
        }.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> false
        }
    }

    companion object {
        private const val TAG_PREVIEW_FRAGMENT: String = "previewFragment"
        private const val ARG_GIF_MODEL = "gifModel"

        fun newIntent(context: Context?, gifModel: GifModel): Intent {
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra(ARG_GIF_MODEL, gifModel)
            return intent
        }
    }
}
