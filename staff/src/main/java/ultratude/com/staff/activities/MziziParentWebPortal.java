package ultratude.com.staff.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.BuildConfig;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;


public class MziziParentWebPortal extends AppCompatActivity implements AdvancedWebView.Listener  {

    //private WebViewSuite webViewSuite;

    private AdvancedWebView mWebView;
    private ImageButton back_parent_portal;


    private void sendRequestForGlobalSettings(Context context){

        Staff staff = new StaffDao(context).getUserThatSignedUp();
        Map<String,String> request = new HashMap<>();
        request.put("settingName", "PORTAL_URL");
        request.put("organizationID",staff.getOrganizationID());
        request.put("appCode", staff.getAppcode());
        request.put("AppVersion", String.valueOf(BuildConfig.VERSION_CODE));


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<Map<String,String>> userCall = apiInterface.getGlobalSettings(request);
       userCall.enqueue(new Callback<Map<String, String>>() {
           @Override
           public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
               if(response.isSuccessful()){
                   if(response.code() == 200){
                       Map<String, String> map = (Map<String,String>)response.body();
                       String BASEURL =  map.get("GlobalSettingValue");


                       mWebView.setMixedContentAllowed(false);
                       mWebView.loadUrl(BASEURL);
                   }
               }
           }

           @Override
           public void onFailure(Call<Map<String, String>> call, Throwable t) {

           }
       });

    }

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


        sendRequestForGlobalSettings(MziziParentWebPortal.this);



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
