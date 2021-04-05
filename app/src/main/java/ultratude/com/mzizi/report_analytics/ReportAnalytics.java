package ultratude.com.mzizi.report_analytics;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;

public class ReportAnalytics {

    public static void reportAnalyticEvent(final Context context, final String eventKey, final String eventValue){
        new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
                List<PortalSiblings> portalSiblingsList = (List<PortalSiblings>) o;
                if(portalSiblingsList != null)
                    if(portalSiblingsList.size() > 0){
                        PortalSiblings portalSiblings = portalSiblingsList.get(0);
                        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                        //student
                        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
                        Bundle b = new Bundle();
                        b.putString(eventKey, eventValue );
                        mFirebaseAnalytics.logEvent("Mzizi_Event",b);
                        //mFirebaseAnalytics.logEvent();
                    }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                return ParentMziziDatabase.getInstance(context.getApplicationContext()).getPortalSiblingsDao().getMainStudentFromSiblingInformation();
            }
        }.execute();
    }

    public static void reportScreenChangeAnalytic(final Context context,  final String screenName){
        new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
                List<PortalSiblings> portalSiblingsList = (List<PortalSiblings>) o;
                if(portalSiblingsList != null)
                    if(portalSiblingsList.size() > 0){
                        PortalSiblings portalSiblings = portalSiblingsList.get(0);
                        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
                        //student
                        mFirebaseAnalytics.setUserId(portalSiblings.getStudentID());
                        mFirebaseAnalytics.setUserProperty("Username", portalSiblings.getUsername());
                        mFirebaseAnalytics.setUserProperty("Password", portalSiblings.getDefaultPassword());
                        mFirebaseAnalytics.setUserProperty("SchoolID", portalSiblings.getSchoolID());
                        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
                        Bundle b = new Bundle();
                        b.putString("Mzizi_Screen", screenName );
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW,b);
                        //mFirebaseAnalytics.logEvent();
                    }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                return ParentMziziDatabase.getInstance(context.getApplicationContext()).getPortalSiblingsDao().getMainStudentFromSiblingInformation();
            }
        }.execute();
    }

}
