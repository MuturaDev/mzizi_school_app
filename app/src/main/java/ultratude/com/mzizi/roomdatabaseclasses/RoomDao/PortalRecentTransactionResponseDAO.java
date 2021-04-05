package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;

@Dao
public interface PortalRecentTransactionResponseDAO {

    @Query("SELECT  * FROM PortalRecentTransactionResponse WHERE StudID = :StudID LIMIT 5")
    List<PortalRecentTransactionResponse> getPortalRecentTransactionResponse(Integer StudID);

    @Insert
    long[] insertPortalRecentTransactionResponse(List<PortalRecentTransactionResponse> response);

    @Query("DELETE FROM PortalRecentTransactionResponse WHERE StudID = :StudID")
    int deletePortalRecentTransactionResponse(Integer StudID);

    @Query("DELETE FROM PortalRecentTransactionResponse")
    int deleteAllPortalRecentTransactionResponse();
}
