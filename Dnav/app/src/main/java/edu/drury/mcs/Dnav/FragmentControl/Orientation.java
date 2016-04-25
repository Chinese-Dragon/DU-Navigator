package edu.drury.mcs.Dnav.FragmentControl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Orientation extends Fragment {

    private View layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_orientation, container, false);


        WebView mWebView = (WebView) layout.findViewById(R.id.orientation);
        //enable javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //load up the FAQ web page
        mWebView.loadUrl("http://www.drury.edu/daycatalog/new-student-orientation/");
        //allow the webview to navigate to other pages
        mWebView.setWebViewClient(new WebViewClient());
        return layout;
    }

}
