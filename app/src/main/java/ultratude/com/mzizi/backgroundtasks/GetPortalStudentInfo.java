package ultratude.com.mzizi.backgroundtasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.uiactivities.MainActivity;

/**
 * Created by James on 04/07/2018.
 */


    //just for providing an instance of that class
public class GetPortalStudentInfo extends AsyncTask<Void,Void,PortalStudentInfo> {


    private WeakReference<MainActivity> mainActivityWeakReference;


    public GetPortalStudentInfo(MainActivity mainActivity){
        mainActivityWeakReference = new WeakReference<>(mainActivity);

    }

    @Override
    protected void onPostExecute(PortalStudentInfo portalStudentInfo) {
        mainActivityWeakReference.get().portalStudentInfoInstance = portalStudentInfo;

        super.onPostExecute(portalStudentInfo);
    }

    @Override
    protected PortalStudentInfo doInBackground(Void... voids) {

        if(mainActivityWeakReference.get() != null){
            String studentid = mainActivityWeakReference.get().db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }
            List<PortalStudentInfo> studentList = mainActivityWeakReference.get().db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
            if(studentList.size() <1){
                return new PortalStudentInfo();
            }

            return studentList.get(0);
        }else{

            return null;
        }

    }
}
