package com.torrober.colomboradios;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        WebView webView = findViewById(R.id.player);
        Intent intent = getIntent();
        String stream = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        String url = "file:///android_asset/index.html?url="+stream+"&title="+title;
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}