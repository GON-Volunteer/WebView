package com.example.gon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.gon.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()

{
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        //CLA
//        binding.webview.loadUrl("http://13.235.225.168:8080/")

        //GON(개발용)
        binding.webview.loadUrl("http://15.165.137.38:8080")


    }

    override fun onBackPressed() {
        if (binding.webview.canGoBack())
        {
            binding.webview.goBack()
        }
        else
        {
            finish()
        }
    }

}