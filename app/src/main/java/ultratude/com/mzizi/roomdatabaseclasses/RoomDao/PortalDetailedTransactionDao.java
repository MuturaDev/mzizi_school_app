package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;

/**
 * Created by James on 07/07/2018.
 */
@Dao
public interface PortalDetailedTransactionDao {

    @Query("SELECT * FROM PortalDetailedTransaction WHERE StudID = :StudID")
    List<PortalDetailedTransaction> getPortalDetailedTransaction(Integer StudID);

    @Insert
    long[] insertPortalDetailedTransaction(List<PortalDetailedTransaction> studentTransaction);

    @Query("DELETE FROM PortalDetailedTransaction WHERE StudID = :StudID")
    int deletePortalDetailedTransaction(Integer StudID);

    @Query("DELETE FROM PortalDetailedTransaction")
    int deleteAllPortalDetailedTransaction();

    //@Query("SELECT * FROM PortalDetailedTransaction")
    //LiveData<List<PortalDetailedTransaction>> getLivePortalTransactions();
}
