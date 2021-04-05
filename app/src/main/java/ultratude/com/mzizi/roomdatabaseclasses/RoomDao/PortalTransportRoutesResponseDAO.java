package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;

@Dao
public interface PortalTransportRoutesResponseDAO {

    @Query("SELECT * FROM PortalTransportRoutesResponse WHERE StudID = :StudID")
    List<PortalTransportRoutesResponse> getPortalTransportRoutesResponse(Integer StudID);

    @Insert
    long[] insertPortalTransportRoutesResponse(List<PortalTransportRoutesResponse> response);

    @Query("DELETE FROM PortalTransportRoutesResponse WHERE StudID = :StudID")
    int deletePortalTransportRoutesResponse(Integer StudID);

    @Query("DELETE FROM PortalTransportRoutesResponse")
    int deleteAllPortalTransportRoutesResponse();
}
