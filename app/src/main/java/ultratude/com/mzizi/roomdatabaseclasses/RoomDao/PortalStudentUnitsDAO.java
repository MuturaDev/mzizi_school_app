package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;

@Dao
public interface PortalStudentUnitsDAO {

    @Query("SELECT * FROM PortalStudentUnits WHERE StudID = :StudID")
    List<PortalStudentUnits> getPortalStudentUnits(Integer StudID);


    @Insert
    long[] insertPortalStudentUnits(List<PortalStudentUnits> response);



    @Query("DELETE FROM PortalStudentUnits WHERE StudID = :StudID")
    int deletePortalStudentUnits(Integer StudID);

    @Query("DELETE FROM PortalStudentUnits")
    int deleteAllPortalStudentUnits();

}
