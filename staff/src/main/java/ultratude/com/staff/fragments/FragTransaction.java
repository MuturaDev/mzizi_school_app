package ultratude.com.staff.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;

import ultratude.com.staff.activities.StudentEnquiry;
import ultratude.com.staff.constantclasses.DisplayContants;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;

/**
 * Created by James on 23/07/2018.
 */

public class FragTransaction {

   // private static FragmentManager fragmentManager;
    private StudentEnquiry mainActivity;

    public static String notificationCount;

    public static FragmentManager fragManager;

    private static Map<String, Object> dataForFragment;




    private FragTransaction(){

    }


    public static void putDataHereToSend(Map<String, Object> data){
        dataForFragment = data;
    }




    //FeeBalanceFrag.class;UpcomingEventsFrag.class;ViewExamHistory.class;NotificationFrag.class;MessageListFrag.class;
     public static void dislayFragment(Class fragClass, FragmentManager fragmentManager, StudentRequest studentRequest){
          //fragment manager will be initialized in MainActivity

        //create a new fragment and specify the fragment to show based on nav item clicked

         fragManager = fragmentManager;


        //create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass = null;


      if(fragClass.equals(ViewExamHistory.class)){
            fragmentClass = fragClass;
            //activity.setTitle("Current Mean Score");

      }
      else if(fragClass.equals(ViewClassAttendance.class)){

            fragmentClass = fragClass;
           // activity.setTitle("Notifications");

      }else if(fragClass.equals(ViewDisciplinaryCases.class)){
          fragmentClass = fragClass;
      }



        try{




           Fragment fragment = (Fragment) fragmentClass.newInstance();

            if(fragment instanceof ViewExamHistory){

                Bundle b = new Bundle();
                b.putSerializable(DisplayContants.STUDENT_REQUEST, studentRequest);
                fragment.setArguments(b);
                //insert the fragment by replacing any existing
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();
            }
            else if(fragment instanceof ViewClassAttendance){

                Bundle b = new Bundle();
                b.putSerializable(DisplayContants.STUDENT_REQUEST, studentRequest);
                fragment.setArguments(b);
                //insert the fragment by replacing any existing
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();


           }else if(fragment instanceof  ViewDisciplinaryCases){
                Bundle b = new Bundle();
                b.putSerializable(DisplayContants.STUDENT_REQUEST, studentRequest);
                fragment.setArguments(b);
                //insert the fragment by replacing any existing
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public static  boolean getFragmentIsVisible(String key, FragmentManager fragmentManager){
       return fragmentManager.findFragmentByTag(key).isVisible();
    }

    public static Fragment getFragment(String key, FragmentManager fragmentManager){
      return  fragmentManager.findFragmentByTag(key);
    }



    //this method might not be working
    public static  void removeFragments(FragmentManager fragmentManager){

        for(Fragment removeFrag : fragmentManager.getFragments()) {
            if (removeFrag.isVisible()) {
                fragmentManager.beginTransaction().remove(removeFrag).commit();
            }
        }




    }


}
