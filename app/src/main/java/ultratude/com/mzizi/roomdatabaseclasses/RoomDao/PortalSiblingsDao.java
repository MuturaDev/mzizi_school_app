package ultratude.com.mzizi.roomdatabaseclasses.RoomDao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;

/**
 * Created by James on 20/07/2018.
 */
@Dao
public interface PortalSiblingsDao {

    @Query("SELECT * FROM PortalSiblings")
    List<PortalSiblings> getSiblings();

    @Query("SELECT SchoolID FROM PortalSiblings WHERE studentID = :StudentID")
    String getSchoolIDFromPortalSibling(String StudentID);

    @Query("SELECT DefaultPassword FROM PortalSiblings WHERE studentID = :StudentID")
    String getPassowrdFromPortalSibling(String StudentID);

    @Query("SELECT Username FROM PortalSiblings WHERE studentID = :StudentID")
    String getUsernameFromPortalSibling(String StudentID);

    @Query("SELECT CASE TRIM(StudentID) WHEN '' THEN  '0' " +
            "WHEN NULL THEN '0' ELSE StudentID END StudentID FROM PortalSiblings p " +
            "WHERE p.isMain = 1")
    String getMainStudentFromSibling();

    @Query("SELECT * FROM PortalSiblings p " +
            "WHERE p.isMain = 1")
    List<PortalSiblings> getMainStudentFromSiblingInformation();


    @Insert
    long[] insertMoreSiblings(List<PortalSiblings> siblings);


    @Insert
    long insertSibling(PortalSiblings sibling);

    @Query("DELETE FROM PortalSiblings")
    int deleteSiblings();

//    @Query("SELECT * FROM PortalSiblings")
//    //LiveData<List<PortalSiblings>> getLiveSiblings();
}
