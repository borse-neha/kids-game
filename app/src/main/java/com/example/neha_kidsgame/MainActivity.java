package com.example.neha_kidsgame;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
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
    private MediaPlayer bgMusicPlayer;
    private MediaPlayer sfxPlayer;

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

        // Initialize Background Music Player with soft volume attenuation
        try {
            bgMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
            if (bgMusicPlayer != null) {
                bgMusicPlayer.setLooping(true);
                bgMusicPlayer.setVolume(0.25f, 0.25f); // Soft background level
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up and start the intro video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.game_intro;
        introVideo.setVideoURI(Uri.parse(videoPath));
        
        introVideo.setOnCompletionListener(mp -> {
            // Once the video finishes, hide it and show the webview
            introVideo.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        });
        
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgMusicPlayer != null && bgMusicPlayer.isPlaying()) {
            bgMusicPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgMusicPlayer != null) {
            try {
                if (bgMusicPlayer.isPlaying()) {
                    bgMusicPlayer.stop();
                }
                bgMusicPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            bgMusicPlayer = null;
        }
        if (sfxPlayer != null) {
            try {
                if (sfxPlayer.isPlaying()) {
                    sfxPlayer.stop();
                }
                sfxPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sfxPlayer = null;
        }
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

        @JavascriptInterface
        public void playBackgroundMusic() {
            if (bgMusicPlayer != null && !bgMusicPlayer.isPlaying()) {
                bgMusicPlayer.start();
            }
        }

        @JavascriptInterface
        public void pauseBackgroundMusic() {
            if (bgMusicPlayer != null && bgMusicPlayer.isPlaying()) {
                bgMusicPlayer.pause();
            }
        }

        @JavascriptInterface
        public void duckBackgroundMusic() {
            if (bgMusicPlayer != null) {
                bgMusicPlayer.setVolume(0.1f, 0.1f);
            }
        }

        @JavascriptInterface
        public void restoreBackgroundMusic() {
            if (bgMusicPlayer != null) {
                bgMusicPlayer.setVolume(0.25f, 0.25f);
            }
        }

        @JavascriptInterface
        public void playAnimalSound(String animalName) {
            try {
                int soundResId = 0;
                if (animalName != null) {
                    switch (animalName.trim().toUpperCase()) {
                        case "DOG":
                            soundResId = R.raw.sound_dog;
                            break;
                        case "CAT":
                            soundResId = R.raw.sound_cat;
                            break;
                        case "COW":
                            soundResId = R.raw.sound_cow;
                            break;
                        case "LION":
                            soundResId = R.raw.sound_lion;
                            break;
                        case "HEN":
                            soundResId = R.raw.sound_hen;
                            break;
                        case "SHEEP":
                            soundResId = R.raw.sound_sheep;
                            break;
                        case "HORSE":
                            soundResId = R.raw.sound_horse;
                            break;
                        case "ELEPHANT":
                            soundResId = R.raw.sound_elephant;
                            break;
                        case "MONKEY":
                            soundResId = R.raw.sound_monkey;
                            break;
                        case "BEAR":
                            soundResId = R.raw.sound_bear;
                            break;
                        case "TIGER":
                            soundResId = R.raw.sound_tiger;
                            break;
                        case "GIRAFFE":
                            soundResId = R.raw.sound_giraffe;
                            break;
                        case "ZEBRA":
                            soundResId = R.raw.sound_zebra;
                            break;
                        case "RABBIT":
                            soundResId = R.raw.sound_rabbit;
                            break;
                        default:
                            soundResId = 0;
                            break;
                    }
                }
                if (soundResId != 0) {
                    if (sfxPlayer != null) {
                        try {
                            if (sfxPlayer.isPlaying()) {
                                sfxPlayer.stop();
                            }
                            sfxPlayer.release();
                        } catch (Exception ignored) {}
                        sfxPlayer = null;
                    }
                    sfxPlayer = MediaPlayer.create(MainActivity.this, soundResId);
                    if (sfxPlayer != null) {
                        sfxPlayer.setVolume(1.0f, 1.0f);
                        sfxPlayer.start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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