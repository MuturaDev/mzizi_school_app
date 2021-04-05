package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;

@Dao
public interface YoutubeVideoGalleryResponseDAO {

    @Query("SELECT * FROM YoutubeVideoGalleryResponse WHERE StudID = :StudID")
    List<YoutubeVideoGalleryResponse> getYoutubeVideoGalleryResponse(Integer StudID);

    @Insert
    long[] insertYoutubeVideoGalleryResponse(List<YoutubeVideoGalleryResponse> response);

    @Query("DELETE FROM YoutubeVideoGalleryResponse WHERE StudID = :StudID")
    int deleteYoutubeVideoGalleryResponse(Integer StudID);
    
    @Query("DELETE FROM YoutubeVideoGalleryResponse")
    int deleteAllYoutubeVideoGalleryResponse();

}
