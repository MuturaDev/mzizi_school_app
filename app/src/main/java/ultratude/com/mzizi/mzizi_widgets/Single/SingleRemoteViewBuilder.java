package ultratude.com.mzizi.mzizi_widgets.Single;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;


import heyalex.widgethelper.WidgetUpdateService;
import ultratude.com.mzizi.R;


/*You can have an app widget configuration activity.
* -This is an optional activity that launches when the user adds your app widget and allows them
* to modify app widget settings at create-time.
* -set for this components, visit, https://developer.android.com/guide/topics/appwidgets*/




public class SingleRemoteViewBuilder {

    private RemoteViews remoteViews;
    private Context context;
    private int widgetId;
    public static final String NEXT_TEXT = "EXAMPLE NEXT TEXT";
    public static final String PREVIOUS_TEXT = "EXAMPLE PREVIOUS TEXT";
    public static final String NEXT_ACTION = "NEXT_ACTION";
    public static final String PREVIOUS_ACTION = "PREVIOUS_ACTION";

    public SingleRemoteViewBuilder(Context context, int widgetId) {
        this.widgetId = widgetId;
        this.context = context;
        /*View layout
        * -is the initial layout for the app widget*/
        this.remoteViews = new RemoteViews(context.getPackageName(), R.layout.example_single_app_widget);
    }

    public RemoteViews getFirstView(String exampleText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteViews.setImageViewResource(R.id.next, R.drawable.chevron_right);
        } else {
            remoteViews.setImageViewBitmap(R.id.next, VectorUtil
                    .vectorToBitmap(context, R.drawable.chevron_right));
        }
        remoteViews.setOnClickPendingIntent(R.id.next, getPendingIntent(context, PREVIOUS_TEXT,
                NEXT_ACTION, widgetId));
        remoteViews.setViewVisibility(R.id.next, View.VISIBLE);
        remoteViews.setViewVisibility(R.id.previous, View.INVISIBLE);
        remoteViews.setTextViewText(R.id.appwidget_text, exampleText);
        return remoteViews;
    }

    public RemoteViews getSecondView(String exampleText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            remoteViews.setImageViewResource(R.id.previous, R.drawable.chevron_left);
        } else {
            remoteViews.setImageViewBitmap(R.id.previous, VectorUtil
                    .vectorToBitmap(context, R.drawable.chevron_left));
        }

        remoteViews.setOnClickPendingIntent(R.id.previous, getPendingIntent(context, NEXT_TEXT,
                PREVIOUS_ACTION, widgetId));
        remoteViews.setViewVisibility(R.id.next, View.INVISIBLE);
        remoteViews.setViewVisibility(R.id.previous, View.VISIBLE);
        remoteViews.setTextViewText(R.id.appwidget_text, exampleText);
        return remoteViews;
    }
//
//    public RemoteViews getThirdView(String exampleText){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//
//        }
//    }


    private static PendingIntent getPendingIntent(Context context, String text, String action,
                                                  int widgetId) {

        //start service directly and make update current widget id
        Bundle bundle = new Bundle();
        bundle.putString("action", action + String.valueOf(widgetId));
        bundle.putString("main_text", text);
        Intent updateServiceIntent = WidgetUpdateService.getIntentUpdateWidget(context,
                ExampleSingleAppWidget.class, bundle, widgetId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(context, widgetId, updateServiceIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        } else {
            return PendingIntent.getService(context, widgetId, updateServiceIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
    }
}