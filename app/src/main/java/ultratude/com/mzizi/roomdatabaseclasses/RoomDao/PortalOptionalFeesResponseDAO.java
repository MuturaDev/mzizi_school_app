package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;

@Dao
public interface PortalOptionalFeesResponseDAO {

    @Query("SELECT * FROM PortalOptionalFeesResponse WHERE StudID = :StudID")
    List<PortalOptionalFeesResponse> getPortalOptionalFeesResponse(Integer StudID);

    @Insert
    long[] insertPortalOptionalFeesResponse(List<PortalOptionalFeesResponse> response);

    @Query("DELETE FROM PortalOptionalFeesResponse WHERE StudID = :StudID")
    int deletePortalOptionalFeesResponse(Integer StudID);

    @Query("DELETE FROM PortalOptionalFeesResponse")
    int deleteAllPortalOptionalFeesResponse();
}
