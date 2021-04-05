package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

//import android.arch.lifecycle.LiveData;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;

/**
 * Created by James on 25/06/2018.
 */

@Dao
public interface PortalStudentInfoDao  {


    @Query("SELECT * FROM SyncMyAccountResult WHERE StudID = :StudID")
    List<PortalStudentInfo> getPortalStudentsInfo(Integer StudID);

    @Query("SELECT * FROM SyncMyAccountResult WHERE registrationNumber = :registrationNumber")
    List<PortalStudentInfo> checkIfStudentWithThisRegistrationNumberExist(String registrationNumber);

    //insert object in database
    @Insert
    long insertStudent(PortalStudentInfo student);

    //delete object in database
    @Query("DELETE FROM SyncMyAccountResult WHERE StudID = :StudID")
    int deleteStudent(Integer StudID);

    @Query("DELETE FROM SyncMyAccountResult")
    int deleteAllStudents();


//    @Query("SELECT * FROM SyncMyAccountResult")
//    //LiveData<List<SyncMyAccountResult>> getLivePortalStudentsInfo();

}
