package ultratude.com.mzizi.backgroundtasks;

import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.HomeItem;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.uiactivities.MainActivity;

/**
 * Created by James on 26/06/2018.
 */


    //this retrieves data and displays it
public class NavigationDrawerRetrieveAndDisplayRoomDataTask extends AsyncTask<Void,Void,PortalStudentInfo> {

    private WeakReference<MainActivity> mainActivityWeakReference;


    public NavigationDrawerRetrieveAndDisplayRoomDataTask(MainActivity mainActivity){
        mainActivityWeakReference = new WeakReference<>(mainActivity);

    }

    @Override
    protected PortalStudentInfo doInBackground(Void... voids) {
       String student = mainActivityWeakReference.get().db.getPortalSiblingsDao().getMainStudentFromSibling();
       if(student == null){
           student = "0";
       }
        if(mainActivityWeakReference.get() != null){
            List<PortalStudentInfo> studentList = mainActivityWeakReference.get().db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(student));
            if(studentList.size() <1){
                return new PortalStudentInfo();
            }

            return studentList.get(0);
        }else{
            return new PortalStudentInfo();
        }
    }

    @Override
    protected void onPostExecute(final PortalStudentInfo student) {
            //MainActivity views

        NumberFormat myFormatOne = NumberFormat.getInstance();
        myFormatOne.setGroupingUsed(true);

        mainActivityWeakReference.get().studentName.setText(student.getStudentFullName());
        mainActivityWeakReference.get().schoolMoto.setText(student.getSchoolMotto());
        mainActivityWeakReference.get().registrationNumber.setText("Adm No. " + student.getRegistrationNumber());
        String billinBalance = (student.getBillingBalance() == "") ? "" : myFormatOne.format(Double.valueOf(( student).getBillingBalance()));
        //Should not be displayed
       // mainActivityWeakReference.get().portalBalance.setText("Portal Balance: " + billinBalance);


//        Glide.with(mainActivityWeakReference.get())
//                .load("https://demo.mzizi.co.ke/photos/23226.jpg")
//                .centerCrop()
//                .error()
//                .into(mainActivityWeakReference.get().studentProfilePhoto);

      if(student.getPhotoUrl().equals("gradfemale_avatar.png")){
             mainActivityWeakReference.get().studentProfilePhoto.setImageDrawable(
                     mainActivityWeakReference.get().getResources().getDrawable(R.drawable.gradfemale_avatar));

        }else if(student.getPhotoUrl().equals("gradmale_avatar.png")){
            mainActivityWeakReference.get().studentProfilePhoto.setImageDrawable(
                    mainActivityWeakReference.get().getResources().getDrawable(R.drawable.gradmale_avatar));

        }else{

          AsyncTask asyncTask = new AsyncTask() {
              @Override
              protected void onPostExecute(Object o) {
                  Map<String, Object> map = (Map<String,Object>)o;

                  if(map.get(Constants.PORTAL_URL_ENABLED) != null){
                     String baseulr = ((GlobalSettings)map.get(Constants.PORTAL_URL_ENABLED)).getGlobalSettingsValue();

                      Glide.with(mainActivityWeakReference.get())
                              .load(baseulr + student.getPhotoUrl())//https://demo.mzizi.co.ke/
                              .centerCrop()
                              .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                              .into(mainActivityWeakReference.get().studentProfilePhoto);

                  }

                  super.onPostExecute(o);
              }

              @Override
              protected Object doInBackground(Object[] objects) {
                  ParentMziziDatabase db = ParentMziziDatabase.getInstance(mainActivityWeakReference.get());
                  Map<String, Object> map = new HashMap<>();

                  String student = mainActivityWeakReference.get().db.getPortalSiblingsDao().getMainStudentFromSibling();
                  if(student == null){
                      student = "0";
                  }

                  if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED,Integer.valueOf(student)).size() > 0){
                      map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED, Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling())).get(0));
                  }

                  return map;
              }
          };
          asyncTask.execute();

        }
        //commented as discussed
        //mainActivityWeakReference.get().schoolLogo.setImageDrawable(mainActivityWeakReference.get().getResources().getDrawable(R.drawable.logo_mzizi_2));
        mainActivityWeakReference.get().schoolName.setText(student.getSchoolName());

        super.onPostExecute(student);


    }
}
