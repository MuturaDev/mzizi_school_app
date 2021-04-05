package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import retrofit2.http.DELETE;
import ultratude.com.mzizi.modelclasses.response.BookDataResponse;

@Dao
public interface BookDataResponseDAO {

    @Query("SELECT * FROM BookDataResponse WHERE StudID = :StudID")
    List<BookDataResponse> getBookDataResponse(Integer StudID);

    @Insert
    long[] insertBookDataResponse(List<BookDataResponse> response);

    @Query("DELETE FROM BookDataResponse WHERE StudID = :StudID")
    int deleteBookDataResponse(Integer StudID);

    @Query("DELETE FROM BookDataResponse")
    int deleteAllBookDataResponse();

}
