package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;


/**
 * Created by James on 25/07/2018.
 */

@Dao
public interface AuthenticateUserResponseDao {

    @Query("SELECT * FROM AuthenticateUserResponse")
    List<AuthenticateUserResponse> getAuthenticateUserResponse();

    @Query("SELECT username FROM AuthenticateUserResponse WHERE username LIKE '%ST%' OR '%st%'")
    String getIsStudent();

    @Insert
    long insertAuthenticateUserResponse(AuthenticateUserResponse authenticateUserResponse);

    @Query("DELETE FROM AuthenticateUserResponse")
    int deleteAuthenticateUserResponse();

   // @Query("SELECT * FROM AuthenticateUserResponse")
    //LiveData<List<AuthenticateUserResponse>> getLiveAuthenticateUserResponse();

}
