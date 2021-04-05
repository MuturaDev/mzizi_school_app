package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;

/**
 * Created by James on 20/07/2018.
 */
@Dao
public interface PortalEventsDao {


    @Query("SELECT * FROM PortalEvents WHERE StudID = :StudID")
    List<PortalEvents> getPortalEvents(Integer StudID);

    @Insert
    long[] insertPortalEventsList(List<PortalEvents> portalEventseList);

    @Query("DELETE FROM PortalEvents WHERE StudID = :StudID")
    int deletePortalEvents(Integer StudID);

    @Query("DELETE FROM PortalEvents")
    int deleteAllPortalEvents();

   // @Query("SELECT * FROM PortalEvents")
    //LiveData<List<PortalEvents>> getLivePortalEvents();


}
