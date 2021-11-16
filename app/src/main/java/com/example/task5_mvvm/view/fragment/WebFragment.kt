package com.example.task5_mvvm.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.task5_mvvm.R
import com.example.task5_mvvm.databinding.FragmentWebViewBinding

class WebFragment : Fragment(R.layout.fragment_web_view) {
    private var binding: FragmentWebViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebViewBinding.inflate(layoutInflater)
        initWebView()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun initWebView() {
        binding?.webView?.apply{
            settings.apply {
                javaScriptEnabled = true
                loadsImagesAutomatically = true
            }
            webViewClient = WebViewClient()
            loadUrl(getString(R.string.google_url))
        }
    }
}