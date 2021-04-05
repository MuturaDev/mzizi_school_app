package ultratude.com.mzizi.uiactivities;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ultratude.com.mzizi.BuildConfig;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.multiplechoicequestions.MultipleChoiceQuestionsFragment;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.OrderItemsFragment2;

/**
 * Created by James on 23/07/2018.
 */

public class FragTransaction {

   // private static FragmentManager fragmentManager;
    private MainActivity mainActivity;

    public static String notificationCount;

    public static FragmentManager fragManager;

    public static MainActivity transactionActivity;


    private FragTransaction(){

    }



    public static void fragmentTransactionInstance(){

    }


    public static void setNotificationCount(String count){
        //PreferenceStorage.storeThis("notificationCount", count);
        notificationCount = count;
    }



    //FeeBalanceFrag.class;UpcomingEventsFrag.class;ViewExamHistory.class;NotificationFrag.class;PortalChatsFragment.class;
     public static void dislayFragment(Class fragClass, String valueToSend,  FragmentManager fragmentManager){
          //fragment manager will be initialized in MainActivity

        //create a new fragment and specify the fragment to show based on nav item clicked

         fragManager = fragmentManager;


         //check internet connection
         if(transactionActivity != null){
             if(transactionActivity instanceof  MainActivity){
                 transactionActivity.checkInternetConnection();
             }
         }


        //create a new fragment and specify the fragment to show based on nav item clicked
        Class fragmentClass = null;


        if(fragClass.equals(FeeBalanceFrag.class)){
            fragmentClass =  fragClass;

                //why dont you place this in the oncreate of each fragment
            //activity.setTitle("Recent Balance");
        }else if(fragClass.equals(UpcomingEventsFrag.class)){
            fragmentClass =  fragClass;
           // activity.setTitle("Upcoming Events");

        }else if(fragClass.equals(MeanScoreFrag.class)){
            fragmentClass = fragClass;
            //activity.setTitle("Current Mean Score");

        }else if(fragClass.equals(NotificationFrag.class)){

            fragmentClass = fragClass;
           // activity.setTitle("Notifications");

        }else if(fragClass.equals(PortalChatsFragment.class)){

            fragmentClass = fragClass;
            // getActivity().setTitle("");

        }else if(fragClass.equals(HomeFrag.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(SchoolAttendaceFrag.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(SchoolContactsFrag.class)){
            fragmentClass = fragClass;
        }
        else if(fragClass.equals(NewCarricullumActivity.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(DiaryFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(TransportRoutesFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(OptionalFeesFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(SettingsFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(ProfileFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(AssignmentFileUploadFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(PortalChatsFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(OrderItemsFragment2.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(MultipleChoiceQuestionsFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(BorrowedBooksFragment.class)){
            fragmentClass = fragClass;
        }else if(fragClass.equals(SchoolTripFragment.class)){
            fragmentClass = fragClass;
        }



        try{

           Fragment fragment = (Fragment) fragmentClass.newInstance();
            if(fragment instanceof FeeBalanceFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof HomeFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }
            else if(fragment instanceof UpcomingEventsFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }
            else if(fragment instanceof MeanScoreFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            } else if(fragment instanceof NotificationFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
//            } else if(fragment instanceof PortalChatsFragment){
//                fragment.setArguments(null);
            }else if(fragment instanceof SchoolContactsFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof SchoolAttendaceFrag){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof NewCarricullumActivity){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof DiaryFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof OptionalFeesFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof TransportRoutesFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof SettingsFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof  ProfileFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof  AssignmentFileUploadFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof PortalChatsFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof  OrderItemsFragment2){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof MultipleChoiceQuestionsFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof  BorrowedBooksFragment){
                moveToFragment(valueToSend, fragment, fragmentManager);
            }else if(fragment instanceof  SchoolTripFragment){
             moveToFragment(valueToSend, fragment, fragmentManager);
         }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private static void moveToFragment(String valueToSend, Fragment fragment, FragmentManager fragmentManager){
        Bundle b = new Bundle();
        b.putString("message", valueToSend);
        fragment.setArguments(b);

        //insert the fragment by replacing any existing
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();
    }



    public static  boolean getFragmentIsVisible(String key, FragmentManager fragmentManager){
       return fragmentManager.findFragmentByTag(key).isVisible();
    }

    public static Fragment getFragment(String key, FragmentManager fragmentManager){
      return  fragmentManager.findFragmentByTag(key);
    }



    public static  void removeFragments(FragmentManager fragmentManager){

        for(Fragment removeFrag : fragmentManager.getFragments()) {
            if (removeFrag.isVisible()) {
                fragmentManager.beginTransaction().remove(removeFrag);
            }
        }

    }


}
