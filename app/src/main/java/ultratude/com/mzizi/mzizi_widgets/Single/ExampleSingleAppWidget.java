package ultratude.com.mzizi.mzizi_widgets.Single;

import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import heyalex.widgethelper.RemoteViewsUpdater;
import heyalex.widgethelper.WidgetUpdateService;

/*Create an app widget
 * 1)declare the app widget within the manifest
 *      declare the class that extends the AppWidgetProvider.
 *      in manifest start with the tag, receiver android:name="xxxAppWidgetProvider", has an action and a metadata
 *      The action within intent-filter, specifies that the AppWidgetProvider accepts the ACTION_APPWIDGET_UPDATE broadcast.
 *          -The AppWidgetManager will automatically send all other AppWidget broadcast to the AppWidgetProvider as necessary.\
 *      The metadata specifies the AppWidgetProviderInfo resource.
 *
 * */

@RemoteViewsUpdater(SingleUpdater.class)

/*App Widget Provider
* -has basic methods that allow you to programmatically interface with app widget, based on broadcast events.
* -through this class implementation you will receive broadcast when the app widget is updated, enabled, disabled and deleted.*/


/*3) Using the AppWidgetProvider Class
*   -This class extends BroadcastReceiver class as a convenience to handle the app widget broadcasts.
*   -It receives only the event broadcasts that are relevant to the app widget, such as when the app widget is updated, deleted, enabled and disabled.
*   -Call back methods of App Widget Provider class
*       -onUpdate
*           -call at intervals defined by the updatePeriodMillis attribute in the AppWidgetProviderInfo.
*           -The method is also called when users adds the App Widget, so it should perform the essential setup, such as
*               -define event handlers for Views and start temporary service, if necessary.
*           -if you have decleared the configuration activity, this method is not called when the user adds the app widget, but it is called for the
*               subsequent updates.
*              -It is the responsibility of the configuration activity to perform the first update when configuration is done.
*       -onAppWidgetOptionsChanged
*           -called when the app widget is placed and any time the widget is resized.
*           -You can use this callback to show or hide content based on the widgets's size ranges.
*           -you can get the size ranges by calling getAppWidgetOptions, which returns a bundle that includes,
*               -OPTION_APPWIDGET_MIN_WIDTH, ...HEIGHT, in dp units.
/Users/ultratudedev/mzizischoolsystem/lib/mzizi_parent_student_portal/screens/portal_notifications_screen.dart*               -OPTION_APPWIDGET_MAX_WIDTH, ...HEIGHT, in dp units.
*        -onDeleted(Context, int[])
*           -called every time an app widget is deleted from App Widget host.
*        -enabled(context)
*           -called the the instance of an app widget is created for the first time.
*           -if the user adds two instances of your app widget, it si only called the first time.
*           -if you need to open a new database or perform other setup that only needs to occur once for all app widget instances, then this is a grood place to do it.
*        -onDisabled(Context)
*           -This is called when your last instance of the app widget is deleted from the app widget host
*        -onReceive(Context, Intent)
*           -This is called for every broadcast and before each of the above callback methods.
*           -you dont need to implement this method.
* 4)Receiving app widget broadcast intents
*   -....
* 5)Pinning App Widget
*   -...
* 6)Creating an App Widget Configuration Activity
*   -...
* 7)Setting a preview image
* 8)Using app widget with collections*/
public class ExampleSingleAppWidget extends AppWidgetProvider {


        /*This is the most important AppWidgetProvider callback.
        * -it is called when each app widget is added to a host, onless you use the configuration activity.
        * -If your app accepts any user interaction events, then you need to register the events handlers in this callback.
        * -if your app widget doesnt create temporary files or databases, or perform other work that requires clean-up, then onupdate may be the only
        *   callback method you need to define.
        * -To perform a button click you need.
        *   -//CODE
        *       final int N = appWidgetIds.length;
        *
        *       //peform this loop procedure for each app widget that belongs to this provider
        *       for(int i =0; i< N; i++){
        *           int appWidgetId = appWidgetIds[i];
        *
        *            //Create an intent to launch ExampleActivity;
        *           Intent intent = new Intent(context, ExampleActivity.class);
        *
        *           PendingIntent pIntent = new PendingIntent.getActivity(context, 0, intent, 0);
        *
        *           //Get the layout for the app widget and attach an on-click listener
        *               //to the button.
        *           RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider_layout);
        *           views.setOnClickPendingIntent(R.id.button, pIntent);
        *
        *           //Tell the appwidgetManager to perform an update on the current app widget.
        *           appWidgetManager.updateAppWidget(appWidgetId, views);
        *       }
        *
        *
        *   //CODE
        *
        *   -appwidgetprovider defines onUpdate for the purpose of defining a PendingIntent, that launches an Activity
        *       and attaching it to the app widget's button with setOnClickPendingIntent(int, PendingIntent).
        *   -It includes a loop.
        *       -the loop interates through each entry in appWidgetIds, (an array of IDs that identify each app widget created by this provider.
        *       -If you the user creates more than one instnace of the app widget, then they are all updated simultaneosly.
        *        however only one updatePeriodMillis schedule will be managed for all instances of the app widget.
        *
        *
        * */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            Bundle bundle = new Bundle();
            bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION + (widgetId));
            bundle.putString("main_text", SingleRemoteViewBuilder.NEXT_TEXT);
            WidgetUpdateService.updateWidgets(context, this, bundle, widgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, ExampleSingleAppWidget.class));

        for (int widgetId : widgetIds) {
            Bundle bundle = new Bundle();
            bundle.putString("action", SingleRemoteViewBuilder.PREVIOUS_ACTION + (widgetId));
            bundle.putString("main_text", SingleRemoteViewBuilder.PREVIOUS_TEXT);

            WidgetUpdateService.updateWidgets(context, this, bundle, widgetId);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}