package edu.drury.mcs.Dnav.Activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.drury.mcs.Dnav.JavaClass.MyFAQAdapter;
import edu.drury.mcs.Dnav.R;

public class FAQ_page extends AppCompatActivity {
    private String link;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_page);

        link = getIntent().getExtras().getString(MyFAQAdapter.EXTRA_LINK);
        name = getIntent().getExtras().getString(MyFAQAdapter.EXTRA_FAQNAME);

        Toolbar toolbar = (Toolbar) findViewById(edu.drury.mcs.Dnav.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView mWebView = (WebView) findViewById(R.id.web_view);
        //enable javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //load up the FAQ web page
        mWebView.loadUrl(link);
        //allow the webview to navigate to other pages
        mWebView.setWebViewClient(new WebViewClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        System.out.println("touched a menu button with id of: " + id);
        System.out.println("home id is: " + R.id.home);
        //noinspection SimplifiableIfStatement
        if (id == edu.drury.mcs.Dnav.R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            System.out.println("back button before pressed");
            NavUtils.navigateUpFromSameTask(this);
            System.out.println("back button is pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
