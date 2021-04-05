package ultratude.com.mzizi.uiactivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

public class MziziParentWebPortal extends AppCompatActivity implements AdvancedWebView.Listener  {

    //private WebViewSuite webViewSuite;

    private AdvancedWebView mWebView;
    private ImageButton back_parent_portal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mzizi_parent_web_portal_layout);

        back_parent_portal = findViewById(R.id.back_parent_portal);
        back_parent_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(MziziParentWebPortal.this);
                alert.setTitle("Confirm Exit");
                alert.setMessage("Are you sure you want to exit Parent Web Portal?");
                alert.setCancelable(false);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

       // webViewSuite = findViewById(R.id.webViewSuite);


        //to refresh the webview
        //webViewSuite.refresh();
        //webViewSuite.startLoading("https://mzizi.co.ke/mzizi_privacy_policy.pdf");

        mWebView = (AdvancedWebView) findViewById(R.id.advanced_webview);
        mWebView.setListener(this, this);

        new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {
                Map<String, GlobalSettings> map = (Map<String,GlobalSettings>)o;
                String BASEURL =  map.get(Constants.PORTAL_URL_ENABLED).getGlobalSettingsValue();


                mWebView.setMixedContentAllowed(false);
                mWebView.loadUrl(BASEURL);

                //webViewSuite.startLoading(BASEURL);
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(MziziParentWebPortal.this);
                Map<String, Object> map = new HashMap<>();

                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED, Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED,Integer.valueOf(studentid)).get(0));
                }
                return map;
            }
        }.execute();




//        webViewSuite.interfereWebViewSetup(new WebViewSuite.WebViewSetupInterference() {
//            @Override
//            public void interfereWebViewSetup(WebView webView) {
//                WebSettings webSettings = webView.getSettings();
//
//            }
//        });
//
//        webViewSuite.customizeClient(new WebViewSuite.WebViewSuiteCallback() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//        });
//
//        webViewSuite.setOpenPDFCallback(new WebViewSuite.WebViewOpenPDFCallback() {
//            @Override
//            public void onOpenPDF() {
//                finish();
//            }
//        });

//       Button advanced_webview_btn = findViewById(R.id.advanced_webview_btn);
//       advanced_webview_btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               webViewSuite.setVisibility(View.GONE);
//               mWebView.setVisibility(View.VISIBLE);
//           }
//       });

//       Button finest_webview_btn = findViewById(R.id.finest_webview_btn);
//       finest_webview_btn.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//            webViewSuite.setVisibility(View.GONE);
//
//           }
//       });

//      Button  old_webview_btn = findViewById(R.id.old_webview_btn);
//        old_webview_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webViewSuite.setVisibility(View.VISIBLE);
//            }
//        });

        ReportAnalytics.reportScreenChangeAnalytic(MziziParentWebPortal.this, "MziziParentWebPortal Activity");

    }

    @Override
    public void onBackPressed() {
//        if(!webViewSuite.goBackIfPossible())
//            super.onBackPressed();
        if (!mWebView.onBackPressed()) { return; }
    }


    //ADVANCED
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    //@SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }


    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }
}
