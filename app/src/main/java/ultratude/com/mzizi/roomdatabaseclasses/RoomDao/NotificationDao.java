package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.Notification;

/**
 * Created by James on 05/07/2018.
 */
@Dao
public interface NotificationDao {

    @Query("SELECT * FROM Notification")
    List<Notification>  getNotifications();

    @Insert
    long insertNotifications(Notification message);

    @Query("DELETE FROM Notification")
    int deleteNotifications();

    //Query("SELECT * FROM Notification")
    //LiveData<List<Notification>> getLiveNotifications();

}
