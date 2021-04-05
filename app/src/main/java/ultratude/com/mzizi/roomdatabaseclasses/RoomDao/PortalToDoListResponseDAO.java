package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;

@Dao
public interface PortalToDoListResponseDAO {

    @Query("SELECT * FROM PortalToDoListResponse WHERE StudID = :StudID LIMIT 10")
    List<PortalToDoListResponse> getPortalToDoListResponse(Integer StudID);

    @Insert
    long[] insertPortalToDoListResponse(List<PortalToDoListResponse> response);

    @Query("DELETE FROM PortalToDoListResponse WHERE StudID = :StudID")
    int deletePortalToDoListResponse(Integer StudID);

    @Query("DELETE FROM PortalToDoListResponse")
    int deleteAllPortalToDoListResponse();
}
