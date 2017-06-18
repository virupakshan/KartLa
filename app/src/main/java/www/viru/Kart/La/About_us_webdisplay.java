package www.viru.Kart.La;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import www.viru.Kart.La.R;

public class About_us_webdisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_webdisplay);


        WebView browser = (WebView) findViewById(R.id.webview);

        browser.loadUrl("https://kart.la/aboutus");

        class MyBrowser extends WebViewClient {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        }



    }
}
