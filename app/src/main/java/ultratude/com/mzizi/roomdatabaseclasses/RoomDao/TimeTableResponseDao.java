package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;

@Dao
public interface TimeTableResponseDao {

    @Query("SELECT * FROM TimeTableResponse WHERE StudID = :StudID")
    List<TimeTableResponse> getTimeTableResponse(Integer StudID);

    @Insert
    long[] insertTimeTableResponse(List<TimeTableResponse> response);

    @Query("DELETE FROM TimeTableResponse")
    int deleteAllTimeTableResponse();

    @Query("DELETE FROM TimeTableResponse WHERE StudID = :StudID")
    int deleteTimeTableResponse(Integer StudID);

}
