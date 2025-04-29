package com.example.messageapp.widgtes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewWrapper {
    private final WebView webView;
    private WebSettings webSettings;
    private OnUrlChangeListener onUrlChangeListener;
    private OnPageStartedListener onPageStartedListener;
    private OnPageFinishedListener onPageFinishedListener;

    // Constructor that initializes WebView
    public WebViewWrapper(Context context, WebView webView) {
        this.webView = webView;
        initializeSettings();
    }

    // Listener interface for URL changes
    public interface OnUrlChangeListener {
        void onUrlChanged(String newUrl);
    }

    // New Interfaces
    public interface OnPageStartedListener {
        void onPageStarted();
    }

    public interface OnPageFinishedListener {
        void onPageFinished();
    }


    // Method to set the URL change listener (like onSetClick)
    public void setOnUrlChangedListener(OnUrlChangeListener listener) {
        this.onUrlChangeListener = listener;
    }

    public void setOnPageStarted(OnPageStartedListener listener) {
        this.onPageStartedListener = listener;
    }

    public void setOnPageFinished(OnPageFinishedListener listener) {
        this.onPageFinishedListener = listener;
    }

    public WebSettings getWebSettings() {
        return webSettings;
    }

    // Initializes the basic WebView settings
    @SuppressLint("SetJavaScriptEnabled")
    private void initializeSettings() {
        // Enable JavaScript
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set a custom WebViewClient to handle page load events and URL changes
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String newUrl = request.getUrl().toString();
                // Notify the listener when the URL changes
                if (onUrlChangeListener != null) {
                    onUrlChangeListener.onUrlChanged(newUrl);
                }
                // Allow the WebView to handle loading the URL
                return false; // Return false to let WebView handle the URL loading
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Notify listener when the page starts loading
                if (onUrlChangeListener != null) {
                    onUrlChangeListener.onUrlChanged(url);
                }
                if (onPageStartedListener != null) {
                    onPageStartedListener.onPageStarted();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Notify listener when the page finishes loading
                if (onUrlChangeListener != null) {
                    onUrlChangeListener.onUrlChanged(url);
                }
                if (onPageFinishedListener != null) {
                    onPageFinishedListener.onPageFinished();
                }
            }
        });
    }

    // Method to load a URL
    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    // Method to run JavaScript and get the response
    public void evaluateJavascript(String script, final JavascriptResponseCallback callback) {
        if (webView != null) {
            // Use evaluateJavascript to run the script and get the response asynchronously
            webView.evaluateJavascript(script, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String response) {
                    if (callback != null) {
                        callback.onJavascriptResponse(response);
                    }
                }
            });
        }
    }

    @SuppressLint("JavascriptInterface")
    public void setJavaScriptListener(String name, JavaScriptChannel channel) {
        webView.addJavascriptInterface(new Object() {
            public void sendMessage(String message) {
                if (channel != null) {
                    channel.onJavaScriptChannel(message);
                }
            }
        }, name);
    }

    public void setConsoleMessageListener(JavaScriptConsoleMessage message) {
        // Set custom WebChromeClient to capture console messages
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                message.onJavaScriptConsoleMessage(consoleMessage.message());
                return true; // Return true to indicate that we have handled the message
//                Log.d("WebViewConsole", "Message: " + consoleMessage.message() +
//                        " -- From line: " + consoleMessage.lineNumber() +
//                        " of " + consoleMessage.sourceId());
            }
        });
    }

    // Callback addJavaScriptChannel
    public interface JavaScriptChannel {
        void onJavaScriptChannel(String message);
    }

    // Callback addJavaScriptChannel
    public interface JavaScriptConsoleMessage {
        void onJavaScriptConsoleMessage(String message);
    }

    // Method to enable JavaScript
    public void enableJavaScript(boolean enable) {
        if (webView != null) {
            webView.getSettings().setJavaScriptEnabled(enable);
        }
    }

    // Method to clear cache
    public void clearCache() {
        if (webView != null) {
            webView.clearCache(true);
        }
    }

    // Callback interface to handle JavaScript response
    public interface JavascriptResponseCallback {
        void onJavascriptResponse(String response);
    }


    // Clean up the WebView
    public void destroyWebView() {
        if (webView != null) {
            webView.clearHistory();
            webView.removeAllViews();
            webView.destroy();
        }
    }

    // Method to check if the WebView can go back
    public boolean canGoBack() {
        return webView != null && webView.canGoBack();
    }

    // Method to navigate back in WebView
    public void goBack() {
        if (webView != null && canGoBack()) {
            webView.goBack();
        }
    }

}
