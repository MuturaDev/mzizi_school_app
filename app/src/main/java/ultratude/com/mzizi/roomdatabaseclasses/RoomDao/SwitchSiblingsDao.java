package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class SwitchSiblingsDao {

    @Query("UPDATE  AuthenticateUserResponse SET userID = :StudID")
   public abstract int setAuthenticateUserResponseMainStudent(String StudID);

    @Query("UPDATE PortalSiblings SET IsMain = 0 WHERE StudentID NOT IN" +
            "(SELECT DISTINCT userID FROM AuthenticateUserResponse)")
    public abstract  int setTheRestNotMainStudentInPortalSibling();


    @Query("UPDATE  PortalSiblings SET IsMain = 1 WHERE studentID IN " +
            "(SELECT DISTINCT userID FROM AuthenticateUserResponse)")
   public abstract int updatePortalSiblingWhoseMainSibling();




    //updates to Siblings table whose the main sibling
    //to switch siblings, just change the value userid in AuthenticateUserResponse
    //will be set after login
    @Transaction
   public void setTheMainStudentToThisStudentIDPassed(String StudID){
        setAuthenticateUserResponseMainStudent(StudID);
        setTheRestNotMainStudentInPortalSibling();
        updatePortalSiblingWhoseMainSibling();
    }
}
