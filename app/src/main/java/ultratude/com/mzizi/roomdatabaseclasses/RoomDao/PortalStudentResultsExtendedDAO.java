package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;

@Dao
public interface PortalStudentResultsExtendedDAO {

    @Query("SELECT * FROM PortalStudentResultsExtended WHERE StudID = :StudID")
    List<PortalStudentResultsExtended> getPortalStudentResultsExtended(Integer StudID);

    @Insert
    long[] insertPortalStudentResultsExtended(List<PortalStudentResultsExtended> response);

    @Query("DELETE FROM PortalStudentResultsExtended WHERE StudID = :StudID")
    int deletePortalStudentResultsExtended(Integer StudID);

    @Query("DELETE FROM PortalStudentResultsExtended")
    int deleteAllPortalStudentResultsExtended();
}
