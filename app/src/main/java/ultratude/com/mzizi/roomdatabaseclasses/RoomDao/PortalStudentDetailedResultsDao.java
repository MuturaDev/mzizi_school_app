package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;

/**
 * Created by James on 07/07/2018.
 */
@Dao
public interface PortalStudentDetailedResultsDao {

    @Query("SELECT * FROM PortalStudentDetailedResults WHERE StudID = :StudID")
    List<PortalStudentDetailedResults> getPortalStudentDetailedResults(Integer StudID);

    @Insert
    long[] insertPortalStudentDetialedResults(List<PortalStudentDetailedResults> studentResults);

    @Query("DELETE FROM PortalStudentDetailedResults WHERE StudID = :StudID")
    int deletePortalStudentDetialedResults(Integer StudID);

    @Query("DELETE FROM PortalStudentDetailedResults")
    int deleteAllPortalStudentDetiledResults();



}
