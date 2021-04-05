package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;

@Dao
public interface PortalStudentVisualizationResponseDAO {

    @Query("SELECT * FROM PortalStudentVisualizationResponse WHERE StudID = :StudID")
    List<PortalStudentVisualizationResponse> getPortalStudentVisualizationResponse(Integer StudID);

    @Insert
    long[] insertPortalStudentVisualizationResponse(List<PortalStudentVisualizationResponse> response);

    @Query("DELETE FROM PortalStudentVisualizationResponse WHERE StudID = :StudID")
    int deletePortalStudentVisualizationResponse(Integer StudID);

    @Query("DELETE FROM PortalStudentVisualizationResponse")
    int deleteAllPortalStudentVisualizationResponse();
}
