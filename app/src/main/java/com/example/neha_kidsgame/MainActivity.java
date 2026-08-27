package com.example.neha_kidsgame;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private VideoView introVideo;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Enable EdgeToEdge and Keep Screen On
        EdgeToEdge.enable(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.activity_main);
        
        // Hide System Bars (Immersive Mode)
        hideSystemBars();

        webView = findViewById(R.id.webview);
        introVideo = findViewById(R.id.introVideo);
        
        // Configure WebView settings for Performance and UX
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        
        // Prevent accidental text selection (long press)
        webView.setOnLongClickListener(v -> true);
        webView.setLongClickable(false);

        // JavaScript Interface
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        // Ensure links open inside the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load the local HTML file (but keep webview hidden for now)
        webView.loadUrl("file:///android_asset/fun.html");

        // Set up and start the intro video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.game_intro;
        introVideo.setVideoURI(Uri.parse(videoPath));
        
        introVideo.setOnCompletionListener(mp -> {
            // Once the video finishes, hide it and show the webview
            introVideo.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        });

        // Remove skip option by NOT setting an OnClickListener on the VideoView
        // This ensures the video plays until completion.
        
        introVideo.start();

        // Handle Back Press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {
                    webView.goBack();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    /**
     * JavaScript interface to allow the game to call native Android functions.
     */
    public class WebAppInterface {
        @JavascriptInterface
        public void exitGame() {
            finish();
        }

        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemBars();
        }
    }
}