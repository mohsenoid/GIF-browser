package com.mohsenoid.gifbrowser.presentation.view.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mohsenoid.gifbrowser.R
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import kotlinx.android.synthetic.main.fragment_preview.gifPreview

class PreviewFragment : Fragment() {

    private var gifModel: GifModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gifModel = arguments?.getParcelable(ARG_GIF_MODEL)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
        Glide.with(context!!)
            .load(gifModel?.url)
            .placeholder(R.drawable.ic_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(gifPreview)
    }

    companion object {
        private const val ARG_GIF_MODEL = "gifModel"

        fun newInstance(gifModel: GifModel) =
            PreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_GIF_MODEL, gifModel)
                }
            }
    }
}
