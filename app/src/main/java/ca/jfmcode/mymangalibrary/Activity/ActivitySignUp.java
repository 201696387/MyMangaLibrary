package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ca.jfmcode.mymangalibrary.R;

public class ActivitySignUp extends AppCompatActivity {

    private WebView myanimelist;
    private final String URL = "https://myanimelist.net/register.php?from=%2F";

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(URL)) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myanimelist = (WebView)findViewById(R.id.act_signup_myanimelistWV);
        WebSettings webSettings = myanimelist.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myanimelist.setWebViewClient(new MyWebViewClient());
        myanimelist.loadUrl(URL);
    }
}
