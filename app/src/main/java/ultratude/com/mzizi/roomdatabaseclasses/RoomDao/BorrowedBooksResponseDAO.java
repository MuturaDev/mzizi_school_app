package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.modelclasses.response.BorrowedBooksResponse;

@Dao
public interface BorrowedBooksResponseDAO {

    @Query("SELECT * FROM BorrowedBooksResponse WHERE StudID = :StudID")
    List<BorrowedBooksResponse> getBorrowedBooksResponse(Integer StudID);

    @Insert
    long[] insertBorrowedBooksResponse(List<BorrowedBooksResponse> response);

    @Query("DELETE FROM BorrowedBooksResponse WHERE StudID = :StudID")
    int deleteBorrowedBooksResponse(Integer StudID);

    @Query("DELETE FROM BorrowedBooksResponse")
    int deleteAllBorrowedBooksResponse();
}
