package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;


/**
 * Created by James on 01/08/2018.
 */



@Dao
public interface PortalContactsDao {

    @Query("SELECT * FROM PortalContacts WHERE StudID = :StudID")
    List<PortalContacts> getPortalContacts(Integer StudID);

    @Insert
    long[] insertPortalContacts(List<PortalContacts> contacts);

    @Query("DELETE FROM PortalContacts WHERE StudID = :StudID")
    int deletePortalContacts(Integer StudID);

    @Query("DELETE FROM PortalSiblings")
    int deleteAllPortalContacts();

//    @Query("SELECT * FROM PortalContacts")
//    LiveData<List<PortalContacts>> getLivePortalContacts();

}
