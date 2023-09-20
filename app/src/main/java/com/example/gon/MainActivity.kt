package com.example.gon

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.gon.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity()

{
    private lateinit var binding : ActivityMainBinding
    val REQ_SELECT_IMAGE = 2001
    var filePathCallback: ValueCallback<Array<Uri>>? = null

    //java
    val IMAGE_SELECTOR_REQ = 1




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
        binding.webview.loadUrl("http://13.235.225.168:8080/")


        //java
        setmWebViewFileUploadPossible()




    }

    //java
    protected fun setmWebViewFileUploadPossible() {
        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                FilePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams
            ): Boolean {
                filePathCallback = FilePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT)

                // 여러장의 사진을 선택하는 경우
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"

                startActivityForResult(Intent.createChooser(intent, "Select picture"), IMAGE_SELECTOR_REQ)
                return true
            }
        }
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

    //java
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_SELECTOR_REQ) {
            if (resultCode == Activity.RESULT_OK) {
                if (data?.clipData != null) {
                    val count = data.clipData!!.itemCount
                    val uris = arrayOfNulls<Uri>(count)
                    for (i in 0 until count) {
                        uris[i] = data.clipData!!.getItemAt(i).uri
                    }
                    filePathCallback?.onReceiveValue(uris as Array<Uri>)
                } else if (data?.data != null) {
                    filePathCallback?.onReceiveValue(arrayOf(data.data!!))
                }
            }
        }
    }


//    private lateinit var binding : ActivityMainBinding
//    override fun onCreate(savedInstanceState: Bundle?)
//    {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        setContentView(view)
//
//        binding.webview.apply {
//            webViewClient = WebViewClient()
//            settings.javaScriptEnabled = true
//        }
//
//        //CLA
////        binding.webview.loadUrl("http://13.235.225.168:8080/")
//
//        //GON(개발용)
//        binding.webview.loadUrl("http://15.165.137.38:8080")
//
//
//    }
//
//    override fun onBackPressed() {
//        if (binding.webview.canGoBack())
//        {
//            binding.webview.goBack()
//        }
//        else
//        {
//            finish()
//        }
//    }

}