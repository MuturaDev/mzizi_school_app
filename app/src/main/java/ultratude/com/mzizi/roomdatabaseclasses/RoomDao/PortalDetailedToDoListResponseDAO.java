package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;

@Dao
public interface PortalDetailedToDoListResponseDAO {

    @Query("SELECT * FROM PortalDetailedToDoListResponse WHERE StudID = :StudID")
    List<PortalDetailedToDoListResponse> getPPortalDetailedToDoListResponse(Integer StudID);

    @Insert
    long[] insertPortalDetailedToDoListResponse(List<PortalDetailedToDoListResponse> response);

    @Query("DELETE FROM PortalDetailedToDoListResponse WHERE StudID = :StudID")
    int deletePortalDetailedToDoListResponse(Integer StudID);

    @Query("DELETE FROM PortalDetailedToDoListResponse")
    int deleteAllPortalDetailedToDoListResponse();

}
