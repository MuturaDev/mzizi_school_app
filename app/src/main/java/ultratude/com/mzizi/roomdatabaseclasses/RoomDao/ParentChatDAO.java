package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;


/**
 * Created by James on 05/06/2019.
 */

@Dao
public interface ParentChatDAO {

    @Query("SELECT * FROM ParentChat WHERE StudID = :StudID")
    List<ParentChat> getParentChat(Integer StudID);

    @Insert
    long[] insertParentChat(List<ParentChat> parentChatList);

    @Query("DELETE FROM ParentChat WHERE StudID = :StudID")
    int deleteParentChat(Integer StudID);

    @Query("DELETE FROM ParentChat")
    int deleteAllParentChat();
}
