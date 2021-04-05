package ultratude.com.mzizi.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.kevalpatel.passcodeview.patternCells.PatternPoint;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;

import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.BuildConfig;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.SplashActivity;
import ultratude.com.mzizi.uploadfiles.FileUploadPayload;

public class UtilityFunctions {

   private Context mContext;
   boolean returnBoolean = false;

    //You can have several constructors
   public UtilityFunctions(Context mContext){
        this.mContext = mContext;
    }

    public boolean internetConnection(){
        final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }

        FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();


        return false;
    }

    public boolean advancedInternetConnection(){



        final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

            AsyncTask asyncTask = new AsyncTask() {

                @Override
                protected void onPostExecute(Object o) {

                   final Student student = (Student) o;

                    Test test = new Test();
                    test.setTest(true);

                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

                    Call<Boolean> userCall = apiInterface.testNetworkStability(test);
                    userCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                            if (response.code() == 200) {

                                returnBoolean = true;

                                if (response.body()) {
                                    //the code should come here

                                    APIInterface apiInterface2 = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo


                                    Call<SyncMyAccountResult> userCall = apiInterface2.getSyncMyAccount(student);
                                    userCall.enqueue(new Callback<SyncMyAccountResult>() {
                                        @Override
                                        public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                                            SyncMyAccountResult result = response.body();

                                            if(result == null){

                                            }else{
                                                // Toast.makeText(context, "SyncMyAccount: " + result.toString(), Toast.LENGTH_LONG).show();


                                                //first delete the previous
                                                new SyncMyAccountDAO(mContext).deleteForSyncMyAccountResult(student.getStudentID());
                                                result.setStudID(Integer.valueOf(student.getStudentID()));
                                                new SyncMyAccountDAO(mContext).insertSyncMyAccountResult(result);
                                            }



                                            //end

                                        }

                                        @Override
                                        public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                                            // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
                                            call.cancel();

                                        }
                                    });


                                    //End


                                        //THIS WAS REPLACED WITH EVERY RECYCLER HAVING A SwipRefreshLayout
//                                    FloatingActionShow.showFloat(false);
//                                    FloatingActionShow.showSchoolChat(false);
//                                    FragTransaction.dislayFragment(DiaryFragment.class, "",fragmentManager);
//                                    Student student = new Student(studentID, appcode);
//                                    new RefreshData(MainActivity.this, fragmentManager).SendRequest(student);

                                }

                            } else {
                                FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            //call.cancel();
                            FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();

                        }
                    });

                    super.onPostExecute(o);
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    return ((MainActivity) mContext).student;
                }
            };
            asyncTask.execute();

        }


        return returnBoolean;
    }

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static  void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public static Map<String, String> getMyAccountCredentials(Context context){//should be an activity context

       ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
        List<AuthenticateUserResponse> authenticateUserResponseList  = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

        Map<String, String> map = new HashMap<>();
        AuthenticateUserResponse authenticateUserResponse = null;
        if(authenticateUserResponseList.size() > 0){//if this is false, authenticateUserResponse will refer to null
            authenticateUserResponse = authenticateUserResponseList.get(0);


                map.put("UserName", db.getPortalSiblingsDao().getUsernameFromPortalSibling(authenticateUserResponse.getUserID()) );//0 = studentid
                map.put("Password",db.getPortalSiblingsDao().getPassowrdFromPortalSibling(authenticateUserResponse.getUserID()));//1 = appcode
                map.put("SchoolCode",authenticateUserResponse.getAppcode());//2=organizationID

        }

        Log.d(context.getPackageName().toUpperCase(), map.toString());

       return map;
    }

    //CHART UTILITY FUNCTIONS
    public List<String> getUniqueTermYearDates(List<PortalStudentVisualizationResponse> responseList){

        List<String> uniquePeriod = new ArrayList<>();


        for(int i = 0; i< responseList.size(); i++){
            String beforeUniquePeriod  = responseList.get(i).getPeriod();
            String afterUniquePeriod = "";

            if(uniquePeriod.size() == 0){
                uniquePeriod.add(beforeUniquePeriod);
            }else{
                for(int k=0; k<uniquePeriod.size(); k++){
                    if(beforeUniquePeriod.equalsIgnoreCase(uniquePeriod.get(k))){
                        break;
                    }else{
                        if(k==(uniquePeriod.size() -1)){
                            afterUniquePeriod = beforeUniquePeriod;
                            uniquePeriod.add(afterUniquePeriod);
                            break;
                        }else{
                            continue;
                        }
                    }
                }
            }
        }

        // Log.d(getActivity().getPackageName().toUpperCase(), uniquePeriod.toString());


        return uniquePeriod;
    }

    public List<PortalStudentVisualizationResponse> getListForThisSubject(String subject, List<PortalStudentVisualizationResponse> response){


       List<PortalStudentVisualizationResponse> list = new ArrayList<>();

       if(subject.isEmpty()){
           return response;
       }
       for(int i=0; i<response.size(); i++){
           if(subject.equalsIgnoreCase(response.get(i).getUnitName())){
               list.add(response.get(i));
           }
       }
            return list;
    }

    public List<List<PortalStudentVisualizationResponse>> getUniqueSubjects(List<PortalStudentVisualizationResponse> resultsList){

        List<List<PortalStudentVisualizationResponse>> responseListList1 = new ArrayList<>();

        List<String> uniqueSubject = new ArrayList<>();

        for(int i =0; i< resultsList.size(); i++){
            String beforeUniqueSubject = resultsList.get(i).getUnitName();
            String afterUniqueSubject = "";

            if(uniqueSubject.size() == 0){
                uniqueSubject.add(beforeUniqueSubject);
            }else{
                for(int k=0; k< uniqueSubject.size(); k++){
                    if(beforeUniqueSubject.equalsIgnoreCase(uniqueSubject.get(k))){
                        break;
                    }else{
                        if(k==(uniqueSubject.size() - 1)){
                            afterUniqueSubject = beforeUniqueSubject;
                            uniqueSubject.add(afterUniqueSubject);
                            break;
                        }else{
                            continue;
                        }
                    }
                }
            }
        }



        for(int i=0; i< uniqueSubject.size(); i++){
            String subject = uniqueSubject.get(i);
            List<PortalStudentVisualizationResponse> responseList1 = new ArrayList<>();
            for(int k=0; k < resultsList.size(); k++){
                if(subject.equalsIgnoreCase(resultsList.get(k).getUnitName())){
                    responseList1.add(resultsList.get(k));
                }
            }
            responseListList1.add(responseList1);
        }

        // Log.d(getActivity().getPackageName().toUpperCase(), responseListList1.toString());

        return  responseListList1;

    }

    public List<String> getXAxisLabelsLine(List<PortalStudentVisualizationResponse> resultsList){

        List<String> subjects = new ArrayList<>();



        for(int i = 0; i< resultsList.size(); i++){
            String beforeUniquePeriod  = resultsList.get(i).getUnitName();
            String afterUniquePeriod = "";

            if(subjects.size() == 0){
                subjects.add(beforeUniquePeriod);
            }else{
                for(int k=0; k<subjects.size(); k++){
                    if(beforeUniquePeriod.equalsIgnoreCase(subjects.get(k))){
                        break;
                    }else{
                        if(k==(subjects.size() -1)){
                            afterUniquePeriod = beforeUniquePeriod;
                            subjects.add(afterUniquePeriod);
                            break;
                        }else{
                            continue;
                        }
                    }
                }
            }
        }

        //Log.d(getActivity().getPackageName().toUpperCase(), subjects.toString());

        return subjects;
    }

    public  List<PortalStudentVisualizationResponse> portalStudentVisualizationResponsesFromJsonLocalFiles(){

       List<PortalStudentVisualizationResponse> returnList = new ArrayList<>();

       Log.d(mContext.getPackageName().toUpperCase(), "Asset has data: " +  loadJsonFromAsset().isEmpty());

       try{
           JSONArray jsonArray = new JSONArray(loadJsonFromAsset());


          for(int i = 0; i < jsonArray.length(); i++){
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              PortalStudentVisualizationResponse response = new PortalStudentVisualizationResponse();
              response.setAverageScore(jsonObject.getString("AverageScore"));
              response.setPeriod(jsonObject.getString("Period"));
              response.setUnitName(jsonObject.getString("UnitName"));

              returnList.add(response);
          }


       }catch(Exception ex){
            return returnList;
       }

        return returnList;

    }

    private String loadJsonFromAsset(){
       String json = null;

       try{

           InputStream inputStream = mContext.getAssets().open("PortalStudentVisualizationAverage.json");
           int size = inputStream.available();

           byte[] buffer = new byte[size];
           inputStream.read(buffer);
           buffer.clone();

           json = new String(buffer, "UTF-8");


       }catch(Exception ex){
           ex.printStackTrace();
           return null;
       }

       return json;
    }

    public List<PortalStudentVisualizationResponse> portalStudentVisualizationResponsesDummyData(){
        List<PortalStudentVisualizationResponse> resultList = new ArrayList<>();
        PortalStudentVisualizationResponse response = new PortalStudentVisualizationResponse(
                "2018 T1",
                "G0: SCI",
                "20"
        );
        resultList.add(response);

        PortalStudentVisualizationResponse response1 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G0: SCI",
                "30"
        );
        resultList.add(response1);
        PortalStudentVisualizationResponse response2 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G0: SCI",
                "40"
        );

        resultList.add(response2);
        PortalStudentVisualizationResponse response3 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G0: SCI",
                "50"
        );
        resultList.add(response3);
        PortalStudentVisualizationResponse response4 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G0: SCI",
                "60"
        );
        resultList.add(response4);
        PortalStudentVisualizationResponse response5 = new PortalStudentVisualizationResponse(
                "2018 T1",
                "G0: SSTRE",
                "20"
        );
        resultList.add(response5);
        PortalStudentVisualizationResponse response6 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G0: SSTRE",
                "30"
        );
        resultList.add(response6);
        PortalStudentVisualizationResponse response7 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G0: SSTRE",
                "40"
        );
        resultList.add(response7);

        PortalStudentVisualizationResponse response8 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G0: SSTRE",
                "50"
        );
        resultList.add(response8);
        PortalStudentVisualizationResponse response9 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G0: SSTRE",
                "60"
        );
        resultList.add(response9);


        PortalStudentVisualizationResponse response10 = new PortalStudentVisualizationResponse(
                "2018 T1",
                "G1: ENG",
                "20"
        );
        resultList.add(response10);

        PortalStudentVisualizationResponse response11 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G1: ENG",
                "30"
        );
        resultList.add(response11);
        PortalStudentVisualizationResponse response12 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G1: ENG",
                "40"
        );

        resultList.add(response12);
        PortalStudentVisualizationResponse response13 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G1: ENG",
                "50"
        );
        resultList.add(response13);
        PortalStudentVisualizationResponse response14 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G1: ENG",
                "60"
        );
        resultList.add(response14);
        PortalStudentVisualizationResponse response15 = new PortalStudentVisualizationResponse(
                "2018 T1",
                "G1: KISWA",
                "20"
        );
        resultList.add(response15);
        PortalStudentVisualizationResponse response16 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G1: KISWA",
                "30"
        );
        resultList.add(response16);
        PortalStudentVisualizationResponse response17 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G1: KISWA",
                "40"
        );
        resultList.add(response17);

        PortalStudentVisualizationResponse response18 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G1: KISWA",
                "50"
        );
        resultList.add(response18);
        PortalStudentVisualizationResponse response19 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G1: KISWA",
                "60"
        );
        resultList.add(response19);

        PortalStudentVisualizationResponse response20= new PortalStudentVisualizationResponse(
                "2018 T1",
                "G0: BIO",
                "20"
        );
        resultList.add(response20);
        PortalStudentVisualizationResponse response21 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G0: BIO",
                "30"
        );
        resultList.add(response21);
        PortalStudentVisualizationResponse response22 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G0: BIO",
                "40"
        );
        resultList.add(response22);

        PortalStudentVisualizationResponse response23 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G0: BIO",
                "50"
        );
        resultList.add(response23);
        PortalStudentVisualizationResponse response24 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G0: BIO",
                "60"
        );
        resultList.add(response24);

        PortalStudentVisualizationResponse response25= new PortalStudentVisualizationResponse(
                "2018 T1",
                "G0: MATH",
                "20"
        );
        resultList.add(response25);
        PortalStudentVisualizationResponse response26 = new PortalStudentVisualizationResponse(
                "2018 T2",
                "G0: MATH",
                "30"
        );
        resultList.add(response26);
        PortalStudentVisualizationResponse response27 = new PortalStudentVisualizationResponse(
                "2018 T3",
                "G0: MATH",
                "40"
        );
        resultList.add(response27);

        PortalStudentVisualizationResponse response28 = new PortalStudentVisualizationResponse(
                "2019 T1",
                "G0: MATH",
                "50"
        );
        resultList.add(response28);
        PortalStudentVisualizationResponse response29 = new PortalStudentVisualizationResponse(
                "2019 T2",
                "G0: MATH",
                "60"
        );
        resultList.add(response29);

        return resultList;
    }


    public static List<String> checkTermYearSchoolDates(){
        Calendar today = Calendar.getInstance();
        //today.set(2020,8, 31);


        Calendar startOfFirstTerm = Calendar.getInstance();
        startOfFirstTerm.set(today.get(Calendar.YEAR), 0, 1);
        System.out.println(startOfFirstTerm.getTime());

        Calendar endOfFirstTerm = Calendar.getInstance();
        endOfFirstTerm.set(today.get(Calendar.YEAR), 3, 30);
        System.out.println(endOfFirstTerm.getTime());

        Calendar startOfSecondTerm = Calendar.getInstance();
        startOfSecondTerm.set(today.get(Calendar.YEAR),4, 1);
        System.out.println(startOfSecondTerm.getTime());

        Calendar endOfSecondTerm =  Calendar.getInstance();
        endOfSecondTerm.set(today.get(Calendar.YEAR), 7, 31);
        System.out.println(endOfSecondTerm.getTime());

        Calendar startOfThirdTerm = Calendar.getInstance();
        startOfThirdTerm.set(today.get(Calendar.YEAR), 8, 1);
        System.out.println(startOfThirdTerm.getTime());

        Calendar endOfThirdTerm = Calendar.getInstance();
        endOfThirdTerm.set(today.get(Calendar.YEAR), 11, 31);
        System.out.println(endOfThirdTerm.getTime());

        String termName = "";
        String year = String.valueOf(today.get(Calendar.YEAR));
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");


        if (today.after(startOfFirstTerm) && today.before(endOfFirstTerm)) {
            //print("Now at first term");
            termName = "Term 1";
        }else if(sdf.format(today.getTime()).equals(sdf.format(startOfFirstTerm.getTime())) ||
                sdf.format(today.getTime()).equals(sdf.format(endOfFirstTerm.getTime()))){
            termName = "Term 1";
        }

        if (today.after(startOfSecondTerm) && today.before(endOfSecondTerm)) {
            //print("Now at second term");
            termName = "Term 2";
        }else if(sdf.format(today.getTime()).equals(sdf.format(startOfSecondTerm.getTime())) ||
                sdf.format(today.getTime()).equals(sdf.format(endOfSecondTerm.getTime()))){
            termName = "Term 2";
        }

        if (today.after(startOfThirdTerm) && today.before(endOfThirdTerm)) {
            //print("Now at Third term");
            termName = "Term 3";
        }else if(sdf.format(today.getTime()).equals(sdf.format(startOfThirdTerm.getTime())) ||
                sdf.format(today.getTime()).equals(sdf.format(endOfThirdTerm.getTime()))){
            termName = "Term 3";
        }

        List<String> returnList = new ArrayList<>();
        returnList.add(termName);//term
        returnList.add(year);//year

        return returnList;
    }


    //TODO: to implemented later, afte testing in netbeans ide
    public static List<Object> getDataForPreviousTermYear(String term, int year){


        //Trying to search for a term and year that has data
        String previousTerm = "";
        int previousYear = year;

        if(term.equalsIgnoreCase("Term 1")){
            previousTerm = "Term 3";
        }else if(term.equalsIgnoreCase("Term 2")){
            previousTerm = "Term 1";
        }else if(term.equalsIgnoreCase("Term 3")){
            previousTerm = "Term 2";
        }

        //get data with year supplied
        //TODO: if statement to check if there exist data for that specific term and year

        if(year == 2020){
            previousYear++;
        }

        //get data with another year
        //TODO: if statement to check if there exist data for that specific term and year

//        System.out.println(previousYear);
//        System.out.println(previousTerm);

        //return previousTerm;

        return null;

    }




    public int getNumberOfDaysForTerm(String term){
        Calendar today = Calendar.getInstance();
        //today.set(2020,8, 31);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");

        Date startDate = new Date();
        Date endDate = new Date();

        int midterm = 0;


        if(term.equalsIgnoreCase("Term 1")){
            Calendar startOfFirstTerm = Calendar.getInstance();
            startOfFirstTerm.set(today.get(Calendar.YEAR), 0, 3);
            startDate = startOfFirstTerm.getTime();


            Calendar endOfFirstTerm = Calendar.getInstance();
            endOfFirstTerm.set(today.get(Calendar.YEAR), 3, 5);
            endDate = endOfFirstTerm.getTime();

            midterm = 7;
        }

        if(term.equalsIgnoreCase("Term 2")){
            Calendar startOfSecondTerm = Calendar.getInstance();
            startOfSecondTerm.set(today.get(Calendar.YEAR),3,29);
            startDate =  startOfSecondTerm.getTime();

            Calendar endOfSecondTerm =  Calendar.getInstance();
            endOfSecondTerm.set(today.get(Calendar.YEAR), 7, 2);
            endDate = endOfSecondTerm.getTime();

            midterm = 7;
        }

        if(term.equalsIgnoreCase("Term 3")){
            Calendar startOfThirdTerm = Calendar.getInstance();
            startOfThirdTerm.set(today.get(Calendar.YEAR), 8, 2);
            startDate = startOfThirdTerm.getTime();

            Calendar endOfThirdTerm = Calendar.getInstance();
            endOfThirdTerm.set(today.get(Calendar.YEAR), 9, 25);
            endDate = endOfThirdTerm.getTime();
        }


            int dateBetween =    interval(startDate, endDate, new IncludeAllDateRule(), Calendar.DATE) - midterm;


            System.out.println("Days Between: " + dateBetween);



        return dateBetween;



    }

    private int interval(Date startDate, Date endDate, DateRule dateRule, int intervalType ){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        int count = 0;
        Date now = calendar.getTime();

        while(now.before(endDate)){
            calendar.add(intervalType, 1);
            now = calendar.getTime();

            if(dateRule.include(now)){
                ++count;
            }

        }

        return count;
    }

   private  interface DateRule{
        boolean include(Date date);
    }


    private class ExcludeWeekendDateRule implements DateRule{

        public boolean include(Date date){
            boolean include = true;

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if ((dayOfWeek == Calendar.SATURDAY || (dayOfWeek == Calendar.SUNDAY))){
                include = false;
            }

            return include;
        }
    }

    private class IncludeAllDateRule implements DateRule{
        public boolean include(Date date){
            return true;
        }
    }



    public static String convertDateToMziziDisplayDate(String stringDate){
        String dateToConvert = "";

        try{

            Date date = new Date(stringDate);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

            dateToConvert = sdf.format(date);


        }catch(Exception ex){
            dateToConvert = stringDate
                    .replace("12:00:00", "").replace("AM","").replace("PM", "")
                    .replace("11:59:59", "");
        }

        return dateToConvert;

    }


    public static List<Boolean> fingerprintHardwareDetected(Context context){
       boolean fingerprintHardwareDetected = false;
       boolean fingerprintNotRegister = false;


        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(context);

        if (!fingerprintManagerCompat.isHardwareDetected()) {
            // Device doesn't support fingerprint authentication
            fingerprintHardwareDetected = false;
            fingerprintNotRegister = false;
        } else if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
            // User hasn't enrolled any fingerprints to authenticate with
            //TODO: Notify to register fingerprint, for authentication
            fingerprintHardwareDetected = true;
            fingerprintNotRegister = true;
        } else {
            // Everything is ready for fingerprint authentication
            fingerprintHardwareDetected = true;
            fingerprintNotRegister = false;
        }

        List<Boolean> returnList = new ArrayList<>();
        returnList.add(fingerprintHardwareDetected);
        returnList.add(fingerprintNotRegister);

        return returnList;
    }


    public static final PatternPoint[] convertStringToCharThenNumberDigits(String content){

        List<Integer> holderPatternNumbers = new ArrayList<>();
        PatternPoint[] patternPointArray = new PatternPoint[content.length()];


        if(!content.isEmpty()){
            try{
                Integer.getInteger(content);//raise error if not number

                char[] numbersChar =  content.toCharArray();
                for(int i =0; i< numbersChar.length; i++){
                    holderPatternNumbers.add(Integer.parseInt(String.valueOf(numbersChar[i])));
                }

            }catch(Exception ex){
                return null;
                //return not a number
            }


            for(int i=0; i<holderPatternNumbers.size(); i++){
                patternPointArray[i] = (getPatternRowColumn(holderPatternNumbers.get(i)));
            }
        }

        return patternPointArray;
    }


    private static  PatternPoint getPatternRowColumn(int valueToFind){

        int[][] matrix = new int[][]{{0,1,2},{3,4,5},{6,7,8}};

        PatternPoint pp = null;

        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                int value =  matrix[i][j];
                if(value == valueToFind){
                    //System.out.println("i: " + i + " j: " + j);
                   pp = new PatternPoint(i, j);
                    break;
                }
            }
        }

        return pp;

    }

    public  static String getYoutubeID(String videoUrl){
       return videoUrl.replace("https://www.youtube.com/embed/","").
               replace("?enablejsapi=1&autoplay=1&fs=0?loop=0&modestbranding=1&rel=0","");
    }


    public static boolean checkFileSizeNotToExceed10MB(final File file){

       if(getFileSizeMegaBytes(file) < 11){
           return false;
       }
       return true;
    }

    private static double getFileSizeMegaBytes(File file){
       return  (double) file.length()/ (1024 * 1024);
    }

//https://stackoverflow.com/questions/6609414/how-do-i-programmatically-restart-an-android-app
    public static void doRestart(Context c) {
       String TAG = c.getPackageName().toUpperCase();
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Was not able to restart application");
        }
    }



    public static int getFirstTimeRun(Context context){
        SharedPreferences pref = context.getSharedPreferences("MYAPP", 0);
        int results, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = pref.getInt("FIRSTTIMERUN", -1);
        if(lastVersionCode == -1) results = 0; else
            results = (lastVersionCode == currentVersionCode) ? 1 : 2;

        pref.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return results;
    }


    public static boolean isFirstInstall(Context context) {
        try {
            long firstInstallTime =   context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
            return firstInstallTime == lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }



    public static boolean isInstallFromUpdate(Context context) {
        try {
            long firstInstallTime =   context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
            long lastUpdateTime = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
            return firstInstallTime != lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void showKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Context context){
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }



    public static List<String> getBodyWithUrl(String two, String st) {
        List<String> returnList = new ArrayList<>();
        if (st != "") {
            String cc = two.replace(st, "&&");
            String[] ss = cc.split("&&");
            for (int i = 0; i < ss.length; i++) {
                if (i == 0) {
                    returnList.add(ss[i]); //0
                    returnList.add(st); //1
                } else {
                    returnList.add(ss[i]); //2
                }
            }
        } else {
            returnList.add(two); //0
        }

        return returnList;
    }

    public static String removeUrl(String sb) {
        String returnString = "";
        String[] arrs = sb.replace("|", "-").split("-");

        for (String arr : arrs) {
            if (arr.contains("http")) {
                returnString = arr;
                break;
            }
        }
        return returnString;
    }




   public static Map<String,String> getPassMeetID(String text){
        Map<String,String> map = new HashMap<>();
       if(text.contains("Join Zoom Meeting") || text.contains("Join Zoom Lesson")){
           if(text.contains("Join Zoom Meeting"))
               return getPassMeetID(text.split("Join Zoom Meeting")[1]);
           else if(text.contains("Join Zoom Lesson"))
               return getPassMeetID(text.split("Join Zoom Lesson")[1]);
            //recursion, testing
            //return  getPassMeetID("|Meeting ID: 850 9804 8526|Password: 078306|");

        }else{

            text= text.replace("|", "&&");
            String[] ldld = text.split("&&");

            int count = 0;
            for(String dd : ldld){
                if(!dd.isEmpty()){
                    if(count == 1)
                        map.put("Meeting ID",dd.replace("Meeting ID:", "").replace("Password:", ""));
                    else if(count == 2)
                        map.put("Password",dd.replace("Meeting ID:", "").replace("Password:", "").replace("Passcode:","").replaceAll("\\s+",""));

                }
                count++;
            }
        }

        return map;
    }


    public static List<TimeTableResponse> getDummyDataForTimeTable(){
        List<TimeTableResponse> responseList = new ArrayList<>();
//                TimeTableResponse response1 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","09:00","09:45","Friday","#ff7e00");
//                responseList.add(response1);
//                TimeTableResponse response2 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","10:00","10:45","Wednesday","#ff7e00");
//                responseList.add(response2);
//                TimeTableResponse response3 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","11:00","11:45","Thursday","#ff7e00");
//                responseList.add(response3);
//                TimeTableResponse response4 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","09:00","09:45","Tuesday","#ff7e00");
//                responseList.add(response4);
//                TimeTableResponse response5 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","11:00","11:45","Friday","#da1884");
//                responseList.add(response5);
//                TimeTableResponse response6 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","12:00","12:45","Thursday","#da1884");
//                responseList.add(response6);
//                TimeTableResponse response7 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","12:00","12:45","wednesday","#da1884");
//                responseList.add(response7);
//                TimeTableResponse response8 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","11:00","11:45","Tuesday","#da1884");
//                responseList.add(response8);

        //same dates different times
        TimeTableResponse response1 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","09:00","09:45","Monday","#ff7e00");
        responseList.add(response1);
        TimeTableResponse response2 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","09:00","09:45","Wednesday","#ff7e00");
        responseList.add(response2);
        TimeTableResponse response3 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","09:00","09:45","Friday","#ff7e00");
        responseList.add(response3);
//
        TimeTableResponse response5 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","14:00","10:45","Thursday","#da1884");
        responseList.add(response5);
//        TimeTableResponse response6 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","18:00","10:45","Wednesday","#da1884");
//        responseList.add(response6);
//        TimeTableResponse response7 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","18:00","10:45","Friday","#da1884");
//        responseList.add(response7);

        TimeTableResponse response8 = new TimeTableResponse("","YEAR 5","MATHEMATICS","Reshma Sharrif","10:00","12:45","Wednesday","#da1884");
        responseList.add(response8);
        TimeTableResponse response4 = new TimeTableResponse("","YEAR 5","ENGLISH","Rosemary Omondi","10:00","11:45","Thursday","#ff7e00");
        responseList.add(response4);
       return responseList;
    }


   public static List<List<TimeTableResponse>> sortRawTimeTableData(List<TimeTableResponse>  rawData){
       List<List<TimeTableResponse>> returnList = new ArrayList<>();

        for(String hour1 : getListOfTimes()){
            List<TimeTableResponse> list = new ArrayList<>();
            for(TimeTableResponse timetable : rawData){
                String hour2 = timetable.getStartTime().split(":")[0];
                if(hour1.equalsIgnoreCase(hour2)){
                    list.add(timetable);
                }
            }


            returnList.add(list);

        }

        return returnList;
    }


    private static List<String> getListOfTimes(){

       List<String> returnlist = new ArrayList<>();
       returnlist.add("07");
        returnlist.add("08");
        returnlist.add("09");
        returnlist.add("10");
        returnlist.add("11");
        returnlist.add("12");
        returnlist.add("13");
        returnlist.add("14");
        returnlist.add("15");
        returnlist.add("16");
        returnlist.add("17");
        returnlist.add("18");

       return returnlist;
    }



    public static String encrypt( String plainText){
        String[] numo = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9","-"};
        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y","z"};
        for(String chara : plainText.split("")){
            int count = 0;
            for(String nim : numo){
                if(chara.equalsIgnoreCase(nim)){
                    plainText = plainText.replace(chara, alpha[count+5]);//5 is the private key
                }
                count++;
            }
        }

       return  plainText;


    }


    //YOU WILL NEED TO RUN THIS AT THE BACKGROUND, INCASE ASYNC IS NOT USED.
    //this making the app crash while using mobile network, but works perfectly on wifi internet
    public static boolean isConnected(Context context) {
        try{
            String command = "ping -c 1 google.com";
            return Runtime.getRuntime().exec(command).waitFor() == 0;

        }catch (InterruptedException ex){
            return false;
        }catch (IOException ex){
            return false;
        }


    }



    public  static void checkIfStudentIfNotMakeWidgetInvisible(final View... views){
      new AsyncTask() {
          @Override
          protected void onPostExecute(Object o) {
              if(views.length > 0) {
                  for (View view : views) {
                      view.setVisibility(View.GONE);
                  }
              }
              super.onPostExecute(o);
          }

          @Override
          protected Object doInBackground(Object[] objects) {
              //check kwa db
              return null;
          }
      }.execute();
    }


    ////////////////////////////////////////////////PASSWORD ENCRYPTION AND DECRYPTION/////////////////////////////////////////////////////////////////

    public static Map<String,String> decryptPassword(String cypher){
       Map<String,String> cred = new HashMap<>();
        String convertyx = cypher.replace("yx","xy");
        String[] splitecypher = convertyx.split("xy");
        int count = 0;
        for(String item : splitecypher){
           cred.put( count == 0 ? "Username" : count == 1 ? "Password" : count == 2 ?  "SchoolCode" : "other"
                   ,decrypt(item));
           count++;
        }

        return cred;
    }


    public static String decrypt(String cypher){


        if(TextUtils.isEmpty(cypher)){
            return "";
        }

        String[] numo = {"0","1", "2", "3", "4", "5", "6", "7", "8", "9","-"};

        String[] alpha = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r",
                "s", "t", "u", "v", "w", "x", "y","z"};


        for(String chara : cypher.split("")){
            int count = 0;
            for(String nim : alpha){
                if(chara.equalsIgnoreCase(nim)){
                    cypher = cypher.replace(chara, numo[count-5]);//5 is the private key
                }
                count++;
            }
        }

        //System.out.println(cypher);
        return cypher;

    }





    ////////////////////////////////////////////////END OF PASSWORD ENCRYPTION AND DECRYPTION/////////////////////////////////////////////////////////////////












}
