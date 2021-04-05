package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;

@Dao
public interface PortalStudentVisualizationAverageResponseDAO {
    @Query("SELECT * FROM PortalStudentVisualizationAverageResponse WHERE StudID = :StudID")
    List<PortalStudentVisualizationAverageResponse> getPortalStudentVisualizationAverageResponse(Integer StudID);

    @Insert
    long[] insertPortalStudentVisualizationAverageResponse(List<PortalStudentVisualizationAverageResponse> response);

    @Query("DELETE FROM PortalStudentVisualizationAverageResponse WHERE StudID = :StudID")
    int deletePortalStudentVisualizationAverageResponse(Integer StudID);

    @Query("DELETE FROM PortalStudentVisualizationAverageResponse")
    int deleteAllPortalStudentVisualizationAverageResponse();
}
